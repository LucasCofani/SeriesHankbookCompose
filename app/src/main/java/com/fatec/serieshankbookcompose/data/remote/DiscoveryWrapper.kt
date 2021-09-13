package com.fatec.serieshankbookcompose.data.remote

data class DiscoveryWrapper(
    val page: Int,
    val results: MutableList<ResultX>,
    val total_pages: Int,
    val total_results: Int
)