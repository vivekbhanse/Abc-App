package com.example.myassignment.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassignment.common.BannerState
import com.example.myassignment.common.ResponseStates
import com.example.myassignment.data.models.BannerContentList
import com.example.myassignment.domain.usecases.ListDataUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModelCompose @Inject constructor(
    val mainUsesCase: ListDataUsesCase,
) : ViewModel() {
    var bannerValue = MutableStateFlow(BannerState())
    val mBannerPostBannerSublist = MutableStateFlow<List<BannerContentList>>(emptyList())
    var finalData = MutableStateFlow("")

    init {
        fetchDataFromLists()
        operationsOnCharacter()
    }

    fun operationsOnCharacter(position: Int = 0) {
        viewModelScope.launch(Dispatchers.Default) {
            val final = mainUsesCase.getNumberOfCharacters(position)
            finalData.update { final }
        }
    }

    fun getSublistByIndex(position: Int) = viewModelScope.launch(Dispatchers.Default) {
        val dataList = mainUsesCase.getBannerSublist(position)
        mBannerPostBannerSublist.update { dataList }
    }

    fun fetchDataFromLists() = viewModelScope.launch {
        mainUsesCase.intitializedBannerListCompose().collect {
            when (it) {
                is ResponseStates.Error -> {
                    bannerValue.value = BannerState(error = it.msg.toString())
                }

                is ResponseStates.Loading -> {
                    bannerValue.value = BannerState(isLoading = true)
                }

                is ResponseStates.Success -> {
                    bannerValue.value = BannerState(bannersList = it.data)
                }
            }
        }
    }
}