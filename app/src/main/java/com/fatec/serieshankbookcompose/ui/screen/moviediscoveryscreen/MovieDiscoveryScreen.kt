package com.fatec.serieshankbookcompose.ui.screen.moviediscoveryscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.ui.SharedViewModel
import com.fatec.serieshankbookcompose.ui.component.ImageCard
import com.fatec.serieshankbookcompose.ui.component.SearchField
import com.fatec.serieshankbookcompose.ui.component.SearchResult
import com.fatec.serieshankbookcompose.ui.screen.Screen

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun MovieDiscoveryScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    viewModel: MovieDiscoveryViewModel = hiltViewModel()
) {
    val searchList by remember {
        sharedViewModel.searchList
    }
    val isLoading by remember {
        sharedViewModel.isLoading
    }
    val errorMsg by remember {
        sharedViewModel.errorMsg
    }
    val querySearch by remember {
        sharedViewModel.qString
    }
    val discoveryTop by remember {
        viewModel.discoveryTop
    }
    val discoveryPop by remember {
        viewModel.discoveryPop
    }
    // para nao resetar o scroll quando tiver paginação
    val listState = rememberLazyListState()
    viewModel.getDiscovery()

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        SearchField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            hint = "Procurando",
            onSearch = sharedViewModel::getSearch
        )
        // Para pesquisar
        if (searchList != null) {
            if (!isLoading && errorMsg.isNullOrBlank()) {
                SearchResult(
                    listState = listState,
                    searchList = searchList,
                    navController = navController,
                    isLoading = isLoading,
                    getNextPage = sharedViewModel::getNextPage,
                    query = querySearch
                )
            }
        }
        // Para descobrir
        else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(text = "Mais populares")
                if (discoveryPop != null) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        itemsIndexed(
                            items = discoveryPop!!
                        ) { index, res ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .padding(8.dp)
                            ) {
                                ImageCard(
                                    item = res,
                                    onClick = GoToDetail(navController),
                                    mediaInfo = "movie"
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Text(text = "Mais votados")
                    if (discoveryTop != null) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            itemsIndexed(
                                items = discoveryTop!!
                            ) { _, res ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.4f)
                                        .padding(8.dp)
                                ) {
                                    ImageCard(
                                        item = res,
                                        onClick = GoToDetail(navController),
                                        mediaInfo = "movie"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GoToDetail(navController: NavController): (Int) -> Unit =
    {
        navController.navigate(Screen.MovieDetailScreen.route + "/$it")
    }

