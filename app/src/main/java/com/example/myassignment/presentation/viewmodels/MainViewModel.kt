package com.example.myassignment.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassignment.common.ResponseStates
import com.example.myassignment.data.models.BannerDetails
import com.example.myassignment.usecases.DataListUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val mainUsesCase: DataListUsesCase,
) : ViewModel() {
    var finalDataOfCharaters = MutableStateFlow("initial value")
    private val _dataList: MutableStateFlow<ResponseStates> = MutableStateFlow(ResponseStates.Empty)
    val getData get() = _dataList
    var listBanner = listOf(BannerDetails())

    init {
        fetchDataFromLists()
    }

    fun calculateNumberOfCharacters(position: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            finalDataOfCharaters.update {
                mainUsesCase.calculateStatics(position, listBanner)
            }
        }
    }

    private fun fetchDataFromLists() {
        _dataList.value = ResponseStates.Loading
        viewModelScope.launch {
            mainUsesCase.getDataSet().catch { e ->
                _dataList.value = ResponseStates.Failure(e)
            }.collect { data ->
                listBanner = data
                calculateNumberOfCharacters()
                _dataList.value = ResponseStates.Success(data)
            }
        }
    }

}
