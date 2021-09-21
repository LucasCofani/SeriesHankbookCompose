package com.fatec.serieshankbookcompose.ui.screen.movielistscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.serieshankbookcompose.data.remote.MovieDetailWrapper
import com.fatec.serieshankbookcompose.data.remote.TVDetailWrapper
import com.fatec.serieshankbookcompose.repository.FirebaseRepository
import com.fatec.serieshankbookcompose.repository.TMDBApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val apiRepo: TMDBApiRepository,
    private val firebaseRepo: FirebaseRepository,
) : ViewModel() {

    val favorites = mutableStateOf<MutableList<MovieDetailWrapper>?>(null)
    val later = mutableStateOf<MutableList<MovieDetailWrapper>?>(null)

    fun getAllFavMovie(){
        viewModelScope.launch {
            val list = firebaseRepo.getAllFavMov()
            val tmpList = mutableListOf<MovieDetailWrapper>()
            list.forEach { id ->
                val tmpRes = apiRepo.getMovieDetail( id.toInt())
                if (tmpRes.error.isNullOrEmpty()) {
                    tmpList.add(tmpRes.data!!)
                }
            }
            favorites.value = tmpList
        }
    }

    fun getAllLaterMovie(){
        viewModelScope.launch {
            val list = firebaseRepo.getAllLaterMov()
            val tmpList = mutableListOf<MovieDetailWrapper>()
            list.forEach { id ->
                val tmpRes = apiRepo.getMovieDetail( id.toInt())
                if (tmpRes.error.isNullOrEmpty()) {
                    tmpList.add(tmpRes.data!!)
                }
            }
            later.value = tmpList
        }
    }
}