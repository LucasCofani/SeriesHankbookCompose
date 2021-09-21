package com.fatec.serieshankbookcompose.ui.screen.movielistscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.ui.component.ListCustom

@ExperimentalMaterialApi
@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val favList by remember {
        viewModel.favorites
    }
    val laterList by remember {
        viewModel.later
    }
    val listState = rememberLazyListState()

    var selectedTab = remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        TabRow(
            selectedTabIndex = selectedTab.value,
        ) {
            Tab(
                selected = selectedTab.value == 0,
                onClick = {
                    if (selectedTab.value != 0) {
                        selectedTab.value = 0
                    }
                },
                text = { Text(text = "Favoritos") },
                modifier = Modifier.background(MaterialTheme.colors.secondary)
            )
            Tab(
                selected = selectedTab.value == 1,
                onClick = {
                    if (selectedTab.value != 1) {
                        selectedTab.value = 1
                    }
                },
                text = { Text(text = "Assistir depois") },
                modifier = Modifier.background(MaterialTheme.colors.secondary)
            )

        }
        when (selectedTab.value) {
            0 ->
                if (favList != null) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp), state = listState
                    ) {
                        itemsIndexed(
                            items = favList!!
                        ) { _, fav ->

                            val id = fav.id
                            val media = "movie"
                            val nome = "" + fav.title
                            val nomeOriginal = "" + fav.original_title
                            val imgSRC = "https://image.tmdb.org/t/p/w185/" + fav.poster_path
                            val lancamento = "" + fav.release_date
                            val nota = "" + fav.vote_average

                            ListCustom(
                                navController = navController,
                                id = id,
                                imgSRC = imgSRC,
                                nome = nome,
                                nomeOriginal = nomeOriginal,
                                media = media,
                                lancamento = lancamento,
                                nota = nota
                            )
                        }
                    }
                } else {
                    viewModel.getAllFavMovie()
                }
            1 ->
                if (laterList != null) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp), state = listState
                    ) {
                        itemsIndexed(
                            items = laterList!!
                        ) { _, fav ->

                            val id = fav.id
                            val media = "movie"
                            val nome = "" + fav.title
                            val nomeOriginal = "" + fav.original_title
                            val imgSRC = "https://image.tmdb.org/t/p/w185/" + fav.poster_path
                            val lancamento = "" + fav.release_date
                            val nota = "" + fav.vote_average

                            ListCustom(
                                navController = navController,
                                id = id,
                                imgSRC = imgSRC,
                                nome = nome,
                                nomeOriginal = nomeOriginal,
                                media = media,
                                lancamento = lancamento,
                                nota = nota
                            )
                        }
                    }
                } else {
                    viewModel.getAllLaterMovie()
                }
        }
    }
}

