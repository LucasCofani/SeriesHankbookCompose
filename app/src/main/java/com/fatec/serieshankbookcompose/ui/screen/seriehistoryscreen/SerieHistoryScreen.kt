package com.fatec.serieshankbookcompose.ui.screen.seriehistoryscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.ui.SharedViewModel
import com.fatec.serieshankbookcompose.ui.component.ListCustom
import com.fatec.serieshankbookcompose.ui.screen.Screen

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun SerieHistoryScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    viewModel: SerieHistoryViewModel = hiltViewModel()
) {
    val isLoading by remember {
        sharedViewModel.isLoading
    }
    val history by remember {
        viewModel.allHistory
    }
    // para nao resetar o scroll quando tiver paginação
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        if (history != null ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp), state = listState
            ) {
                itemsIndexed(
                    items = history!!
                ) { _, fav ->

                    val id = fav.id
                    val media = "tv"
                    val nome = "" + fav.name
                    val nomeOriginal = "" + fav.original_name
                    val imgSRC = "https://image.tmdb.org/t/p/w185/" + fav.poster_path
                    val lancamento = "" + fav.first_air_date
                    val nota = "" + fav.vote_average

                    ListCustom(
                        navController = navController,
                        id = id,
                        imgSRC = imgSRC,
                        nome = nome,
                        nomeOriginal = nomeOriginal,
                        media = media,
                        lancamento = "Assistido em: " + lancamento,
                        nota = nota
                    )
                }
            }
        } else {
            viewModel.getHistory()
        }
    }
}
