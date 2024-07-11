package com.example.myassignment.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.myassignment.data.models.BannerDetails
import com.example.myassignment.databinding.ItemImgSliderBinding
import java.util.Objects

class ViewPagerAdapter(
    private val context: Context,
    private val arr_img: List<BannerDetails>,
) : PagerAdapter() {
    override fun getCount(): Int {
        return arr_img.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ItemImgSliderBinding.inflate(LayoutInflater.from(context), container, false)
        arr_img.get(position).bannerImage?.let { binding.imageViewMain.setImageResource(it) }
        Objects.requireNonNull(container).addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}