package com.example.myassignment.presentation.activity.ui.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.myassignment.data.models.BannerContentList

@Composable
fun FilteredList(items: List<BannerContentList>, query: String) {
    val filteredItems = items.filter {
        it.title!!.contains(query, ignoreCase = true)
    }

    LazyColumn {
        items(filteredItems) {
            DataLists(dataItemUiState = listOf(it))
        }
    }
}