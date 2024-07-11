package com.example.myassignment.usecases

//import androidx.compose.runtime.mutableStateOf
import com.example.myassignment.R
import com.example.myassignment.data.models.BannerContentList
import com.example.myassignment.data.models.BannerDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Collections
import javax.inject.Inject

class DataListUsesCase @Inject constructor(

) {

    fun getDataSet(): Flow<List<BannerDetails>> = flow {
        emit(
            arrayListOf(
                BannerDetails(
                    1, R.drawable.ic_banner1, "Cars", listOf(
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
                ),


                BannerDetails(
                    2, R.drawable.ic_banner1, "Animal", listOf(
                        BannerContentList("Tigor", "Wild", R.drawable.subbanner2),
                        BannerContentList("Lion", "Wild", R.drawable.subbanner2),
                        BannerContentList("Dog", "Pet", R.drawable.subbanner2),
                        BannerContentList("Dog1", "Pet", R.drawable.subbanner2),
                        BannerContentList("Dog2", "Pet", R.drawable.subbanner2),
                    )
                ),

                BannerDetails(
                    3, R.drawable.ic_banner2, "Other", listOf(
                        BannerContentList("Dolphine", "subtitle 1", R.drawable.subbanner3),
                        BannerContentList("Whale", "subtitle 1", R.drawable.subbanner3),
                        BannerContentList("Shark", "subtitle 1", R.drawable.subbanner3),
                        BannerContentList("Shark1", "subtitle 1", R.drawable.subbanner3),
                        BannerContentList("Shark2", "subtitle 1", R.drawable.subbanner3),
                    )
                ),
                BannerDetails(
                    4, R.drawable.ic_banner1, "Bird", listOf(
                        BannerContentList("Swan", "subtitle 1", R.drawable.subbanner4),
                        BannerContentList("Parrot", "subtitle 1", R.drawable.subbanner4),
                        BannerContentList("Peacock", "subtitle 1", R.drawable.subbanner4),
                        BannerContentList("Peacock1", "subtitle 1", R.drawable.subbanner4),
                        BannerContentList("Peacock2", "subtitle 1", R.drawable.subbanner4),
                    )
                ),
            )
        )
    }.flowOn(Dispatchers.IO)


    fun calculateStatics(position: Int, listBanner: List<BannerDetails>): String {

        val bannerLists = listBanner.get(position).bannerSubList ?: emptyList()
        val arrBanner = bannerLists.map { it.title.toString() }

        val listOfCount = mutableMapOf<String, Int>()
        val ultimateList = mutableMapOf<Int, MutableList<String>>()

        arrBanner.forEach { item ->
            item.forEach { char ->
                listOfCount[char.toString()] = listOfCount.getOrDefault(char.toString(), 0) + 1
            }
        }
        val tempo_Arr: ArrayList<Int> = arrayListOf()


        listOfCount.map {
            if (!tempo_Arr.contains(it.value)) tempo_Arr.add(it.value)
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
        val countList = arrayListOf<Int>()
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