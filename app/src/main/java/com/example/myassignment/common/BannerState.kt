package com.example.myassignment.common

import com.example.myassignment.data.models.BannerDetails

data class BannerState(
    val isLoading: Boolean = false,
    val bannersList: List<BannerDetails>? = emptyList(),
    val error: String = "",
)
