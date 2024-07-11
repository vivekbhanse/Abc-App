package com.example.myassignment.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassignment.presentation.activity.ui.compose.FilteredList
import com.example.myassignment.presentation.activity.ui.compose.SearchViewCustom
import com.example.myassignment.presentation.activity.ui.theme.MyAssignmentTheme
import com.example.myassignment.presentation.viewmodels.MainViewModelCompose
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAssignmentTheme {
                GreetingPreview()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeApp(

    viewModel: MainViewModelCompose = hiltViewModel(),
) {
    val stringResult by viewModel.finalData.collectAsState()
    val bannerList by viewModel.bannerValue.collectAsState()
    var query by remember { mutableStateOf("") }
    val pagerState = rememberPagerState(
        pageCount = { bannerList.bannersList?.size ?: 0 },
    )
    val stateCollaped = rememberCollapsingToolbarScaffoldState()
    var index = rememberSaveable { mutableStateOf(0) }
    val sublistBannerContentList by viewModel.mBannerPostBannerSublist.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    LaunchedEffect(pagerState.currentPage) {
        viewModel.getSublistByIndex(pagerState.currentPage)
        viewModel.operationsOnCharacter(pagerState.currentPage)
    }

    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(onClick = {
            showBottomSheet = true
        }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "adds")
        }
    }) { innerPadding ->

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                }, sheetState = sheetState
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text("$stringResult", color = Color.Black)
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    }) {
                        Text("Dismiss", color = Color.Black)
                    }
                }
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CollapsingToolbarScaffold(modifier = Modifier,
                state = stateCollaped,
                scrollStrategy = ScrollStrategy.EnterAlways,
                toolbarModifier = Modifier.fillMaxWidth(),

                toolbar = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        HorizontalPager(
                            state = pagerState, modifier = Modifier
                                .wrapContentSize()
                                .padding(26.dp)
                        ) { currentPage ->
                            index.value = currentPage
                            Card(
                                modifier = Modifier.wrapContentSize(),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = bannerList.bannersList?.get(
                                            currentPage
                                        )?.bannerImage!!
                                    ),
                                    contentDescription = "",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                )
                            }
                        }
                        Row(
                            Modifier
                                .height(30.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,

                            ) {
                            bannerList.bannersList?.let {
                                repeat(it.size) { iteration ->
                                    val color =
                                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                                    Box(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .background(
                                                color, CircleShape
                                            )
                                            .size(10.dp)
                                    )
                                }
                            }
                        }
                    }
                }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SearchViewCustom(query = query, onQueryChanged = { query = it })
                    FilteredList(items = sublistBannerContentList, query = query)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HomeApp()
}

