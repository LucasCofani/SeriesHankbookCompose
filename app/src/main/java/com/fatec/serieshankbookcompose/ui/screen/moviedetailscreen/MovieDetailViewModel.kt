package com.fatec.serieshankbookcompose.ui.screen.moviedetailscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.serieshankbookcompose.data.remote.MovieDetailWrapper
import com.fatec.serieshankbookcompose.data.remote.TVDetailWrapper
import com.fatec.serieshankbookcompose.repository.TMDBApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val apiRepo: TMDBApiRepository
) : ViewModel() {
    val detail = mutableStateOf<MovieDetailWrapper?>(null)

    fun getDetail(id: Int) {
        viewModelScope.launch {
            val res = apiRepo.getMovieDetail(id= id.toInt())
            if (res.error.isNullOrEmpty()) {
                detail.value = res.data
            }
        }
    }
}