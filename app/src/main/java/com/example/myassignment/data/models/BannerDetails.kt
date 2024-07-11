package com.example.myassignment.data.models

data class BannerDetails(
    val num: Int? = null,
    val bannerImage: Int? = null,
    val bannerType: String? = null,
    val bannerSubList: List<BannerContentList>? = emptyList(),
)

data class BannerContentList(
    val title:String?=null,
    val subTitle:String?=null,
    val subBannerImage: Int? = null,
)

