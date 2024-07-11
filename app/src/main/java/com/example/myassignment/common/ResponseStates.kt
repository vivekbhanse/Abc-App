package com.example.myassignment.common

import com.example.myassignment.data.models.BannerDetails

sealed class ResponseStates {
    data object Loading : ResponseStates()
    class Failure(val msg: Throwable) : ResponseStates()
    class Success(val data: List<BannerDetails>) : ResponseStates()
    data object Empty : ResponseStates()
}