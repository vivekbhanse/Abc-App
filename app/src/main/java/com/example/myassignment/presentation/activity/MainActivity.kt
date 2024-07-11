package com.example.myassignment.presentation.activity

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.myassignment.common.ResponseStates
import com.example.myassignment.data.models.BannerDetails
import com.example.myassignment.databinding.ActivityMainBinding
import com.example.myassignment.presentation.adapter.TitleAdpater
import com.example.myassignment.presentation.adapter.ViewPagerAdapter
import com.example.myassignment.presentation.fragments.BottomSheetDialog
import com.example.myassignment.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mViewModel: MainViewModel
    lateinit var adapter: TitleAdpater
    private var positionIndex = 0
    private var result = ""
    private var dataset = arrayListOf<BannerDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.recList.layoutManager = LinearLayoutManager(this@MainActivity)
        myObserver()
        lifecycleScope.launch {
            mViewModel.finalDataOfCharaters.collectLatest {
                result = it
            }
        }
    }

    private fun initView() {
        binding.run {
            moreFab.setOnClickListener {
                val modal = BottomSheetDialog(result)
                supportFragmentManager.let { modal.show(it, BottomSheetDialog.TAG) }
            }
            binding.tabIndicator.setupWithViewPager(viewPagerMain)
            viewPagerMain.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {

                }

                override fun onPageSelected(position: Int) {
                    positionIndex = position
                    initRecylerView(dataset, position)
                    mViewModel.calculateNumberOfCharacters(position)
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        adapter.filter.filter(newText)
                    }
                    return false
                }

            })

        }


    }

    private fun initRecylerView(bannerDetails: List<BannerDetails>, postion: Int = 0) {
        binding.run {
            adapter = bannerDetails.get(postion).bannerSubList?.let { TitleAdpater(it.toList()) }!!
            recList.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun myObserver() {
        lifecycleScope.launch {
            mViewModel.getData.collect {
                when (it) {
                    is ResponseStates.Loading -> {
                        Toast.makeText(this@MainActivity, "Loading..", Toast.LENGTH_SHORT).show()
                    }

                    is ResponseStates.Success -> {
                        dataset.addAll(it.data)
                        binding.viewPagerMain.adapter = ViewPagerAdapter(this@MainActivity, dataset)
                        initView()
                        initRecylerView(dataset)
                    }

                    is ResponseStates.Failure -> {
                        Toast.makeText(this@MainActivity, it.msg.toString(), Toast.LENGTH_LONG)
                            .show()
                    }

                    else -> {}
                }
            }
        }
    }
}