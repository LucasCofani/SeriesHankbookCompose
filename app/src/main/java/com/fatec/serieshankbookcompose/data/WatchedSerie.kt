package com.fatec.serieshankbookcompose.data


data class WatchedSerie (
    val id: Int,
    val date: Long,
    val season: Int
){
    override fun equals(other: Any?): Boolean {
        return other is WatchedSerie && id == other.id && season == other.season
    }

    override fun hashCode(): Int {
        return "$id$season".toInt()
    }
}