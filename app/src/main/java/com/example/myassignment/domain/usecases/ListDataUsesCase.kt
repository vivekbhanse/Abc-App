package com.example.myassignment.domain.usecases

import com.example.myassignment.R
import com.example.myassignment.common.ResponseStates
import com.example.myassignment.data.models.BannerContentList
import com.example.myassignment.data.models.BannerDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Collections
import javax.inject.Inject

class ListDataUsesCase @Inject constructor(
) {
    var bannerList: MutableList<BannerDetails>? = mutableListOf()

    fun intitializedBannerListCompose(): Flow<ResponseStates<List<BannerDetails>>> = flow {
        bannerList = mutableListOf()
        bannerList?.add(
            BannerDetails(
                1, R.drawable.banner1, "Cars", listOf(
                    BannerContentList("Audi", "(1909–present)", R.drawable.subbanner1),
                    BannerContentList("Maruti", "(1950–present)", R.drawable.subbanner1),
                    BannerContentList("Mercedes-Benz", "(1865–present)", R.drawable.subbanner1),
                    BannerContentList("BMW", "(1909–present)", R.drawable.subbanner1),
                    BannerContentList("Nissan", "(1909–present)", R.drawable.subbanner1),
                    BannerContentList("Mono", "(1909–present)", R.drawable.subbanner1),
                    BannerContentList("Ferrari", "(1909–present)", R.drawable.subbanner1),
                    BannerContentList("Mustang", "(1909–present)", R.drawable.subbanner1),
                    BannerContentList("Suziki", "(1909–present)", R.drawable.subbanner1),
                    BannerContentList("Suziki", "(1909–present)", R.drawable.subbanner1),
                    BannerContentList("Suziki", "(1909–present)", R.drawable.subbanner1),
                    BannerContentList("Suziki", "(1909–present)", R.drawable.subbanner1),
                )
            )
        )
        bannerList?.add(
            BannerDetails(
                2, R.drawable.banner1, "Animal", listOf(
                    BannerContentList("Tigor", "Wild", R.drawable.subbanner2),
                    BannerContentList("Lion", "Wild", R.drawable.subbanner2),
                    BannerContentList("Dog", "Pet", R.drawable.subbanner2),
                    BannerContentList("Dog1", "Pet", R.drawable.subbanner2),
                    BannerContentList("Dog2", "Pet", R.drawable.subbanner2),
                )
            )
        )
        bannerList?.add(
            BannerDetails(
                3, R.drawable.banner2, "Other", listOf(
                    BannerContentList("Dolphine", "subtitle 1", R.drawable.subbanner3),
                    BannerContentList("Whale", "subtitle 1", R.drawable.subbanner3),
                    BannerContentList("Shark", "subtitle 1", R.drawable.subbanner3),
                    BannerContentList("Shark1", "subtitle 1", R.drawable.subbanner3),
                    BannerContentList("Shark2", "subtitle 1", R.drawable.subbanner3),
                )
            )
        )
        bannerList?.add(
            BannerDetails(
                4, R.drawable.banner1, "Bird", listOf(
                    BannerContentList("Swan", "subtitle 1", R.drawable.subbanner4),
                    BannerContentList("Parrot", "subtitle 1", R.drawable.subbanner4),
                    BannerContentList("Peacock", "subtitle 1", R.drawable.subbanner4),
                    BannerContentList("Peacock1", "subtitle 1", R.drawable.subbanner4),
                    BannerContentList("Peacock2", "subtitle 1", R.drawable.subbanner4),
                )
            )
        )
        emit(ResponseStates.Success<List<BannerDetails>>(bannerList!!))

    }


    fun getBannerSublist(position: Int): List<BannerContentList> {
        return bannerList!!.get(position).bannerSubList!!
    }


    fun getNumberOfCharacters(position: Int): String {
        val bannerLists = bannerList?.get(position)?.bannerSubList ?: emptyList()
        val arrBanner = bannerLists.map { it.title.toString() }
        val listOfCount = mutableMapOf<String, Int>()
        val ultimateList = mutableMapOf<Int, MutableList<String>>()
        arrBanner.forEach { item ->
            item.forEach { char ->
                listOfCount[char.toString()] = listOfCount.getOrDefault(char.toString(), 0) + 1
            }
        }
        val tempo_Arr: java.util.ArrayList<Int> = arrayListOf()
        listOfCount.map {
            if(!tempo_Arr.contains(it.value))
                tempo_Arr.add(it.value)
        }
        Collections.sort(tempo_Arr, Collections.reverseOrder())
        val tempArrayList2 = tempo_Arr.filterIndexed { index, i -> index < 3 }
        listOfCount.map {
            if (tempArrayList2.contains(it.value)) {
                if (ultimateList.containsKey(it.value)) {
                    var tempArray = ultimateList.getValue(it.value)
                    tempArray.add(it.key)
                    ultimateList.set(it.value, tempArray)
                } else {
                    ultimateList.put(it.value, arrayListOf(it.key))
                }
            }
        }
        val countList= arrayListOf<Int>()
        ultimateList.map {
            countList.add(it.key)
        }
        val countData = buildString {
            append("List ${position + 1}: ${arrBanner.size} items\n\n")
            append(arrBanner.joinToString(separator = ", ", prefix = " ", postfix = "\n\n"))
            ultimateList.toSortedMap(compareByDescending { it }).forEach { (count, chars) ->
                append("\n\n$chars = $count")
            }
        }
        return countData
    }


}