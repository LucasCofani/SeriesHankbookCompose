package com.fatec.serieshankbookcompose.ui.screen.moviedetailscreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.serieshankbookcompose.data.remote.MovieDetailWrapper
import com.fatec.serieshankbookcompose.data.remote.ResultX
import com.fatec.serieshankbookcompose.data.remote.TVDetailWrapper
import com.fatec.serieshankbookcompose.repository.FirebaseRepository
import com.fatec.serieshankbookcompose.repository.TMDBApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val apiRepo: TMDBApiRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    val detail = mutableStateOf<MovieDetailWrapper?>(null)
    val favorite = mutableStateOf<Boolean>(false)
    val similar = mutableStateOf<MutableList<ResultX>?>(null)

    fun getDetail(id: Int) {
        viewModelScope.launch {
            val res = apiRepo.getMovieDetail(id= id.toInt())
            if (res.error.isNullOrEmpty()) {
                detail.value = res.data
                val list = firebaseRepository.getAllFavMov()
                list.forEach { idS ->
                    if (idS.toInt() == id){
                        favorite.value = true
                    }
                }

            }
            getSimilar(id)
        }
    }

    fun setFavorite(id: Int) {
        viewModelScope.launch {
            firebaseRepository.setFavMov(id)
            favorite.value = !favorite.value
        }
    }

    fun setLater(id: Int) {
        viewModelScope.launch {
            firebaseRepository.setLaterMov(id)
        }
    }

    fun getSimilar(id: Int){
        viewModelScope.launch {

            if (similar.value == null){
                val resSimilar = apiRepo.getMovieSimilar(id)
                similar.value = resSimilar.data?.results!!
            }
        }
    }

    fun setWatched(id: Int, date: Date) {
        viewModelScope.launch {
            firebaseRepository.setMoviesWatched(id,date)
        }
    }
}