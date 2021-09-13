package com.fatec.serieshankbookcompose.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.data.remote.ResultX


@ExperimentalMaterialApi
@Composable
fun SearchResult(
    listState: LazyListState,
    searchList: MutableList<ResultX>?,
    navController: NavController,
    isLoading: Boolean,
    getNextPage: () -> Unit,
    query : String
) {
    Text(
        text = "Procurando por: $query",
        modifier = Modifier
            .fillMaxWidth()
        ,textAlign = TextAlign.Center
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), state = listState
    ) {
        itemsIndexed(
            items = searchList!!
        ) { index, search ->
            SearchCard(
                navController = navController,
                search = search
            )
            if (index == (searchList!!.count() - 1) && !isLoading!!) {
                getNextPage()
            }
        }
    }
}