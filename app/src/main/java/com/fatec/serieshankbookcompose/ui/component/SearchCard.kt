package com.fatec.serieshankbookcompose.ui.component

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.data.remote.ResultX


@ExperimentalMaterialApi
@Composable
fun SearchCard(
    search: ResultX,
    navController: NavController
) {
    val id = search.id
    val media = search.media_type
    val nome = "" + if (media == "movie") search.title else search.name
    val nomeOriginal = "" + if (media == "movie") search.original_title else search.original_name
    val imgSRC = "https://image.tmdb.org/t/p/w185/" + search.poster_path
    val lancamento =
        "" + if (media == "movie") search.release_date else (if (media == "tv") search.first_air_date else "")
    val nota = "" + search.vote_average
    if (media != "person") {
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