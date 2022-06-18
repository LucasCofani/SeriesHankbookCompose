package com.fatec.serieshankbookcompose.data


data class Watched (
    val id: Int,
    val date: Long
){
    override fun equals(other: Any?): Boolean {
        return other is WatchedSerie && id == other.id
    }

    override fun hashCode(): Int {
        return "$id".toInt()
    }
}