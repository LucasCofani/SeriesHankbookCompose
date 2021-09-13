package com.fatec.serieshankbookcompose.data.remote

// media_type
// movie
// person
// tv
data class SearchWrapper(
    val page: Int,
    val results: MutableList<ResultX>,
    val total_pages: Int,
    val total_results: Int
)