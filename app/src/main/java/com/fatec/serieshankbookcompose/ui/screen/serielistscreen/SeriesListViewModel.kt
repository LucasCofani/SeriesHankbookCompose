package com.fatec.serieshankbookcompose.ui.screen.serielistscreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.serieshankbookcompose.data.remote.ResultX
import com.fatec.serieshankbookcompose.data.remote.TVDetailWrapper
import com.fatec.serieshankbookcompose.repository.FirebaseRepository
import com.fatec.serieshankbookcompose.repository.TMDBApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesListViewModel @Inject constructor(
    private val apiRepo: TMDBApiRepository,
    private val firebaseRepo: FirebaseRepository,
) : ViewModel() {

    val favorites = mutableStateOf<MutableList<TVDetailWrapper>?>(null)
    val later = mutableStateOf<MutableList<TVDetailWrapper>?>(null)

    fun getAllFavSeries(){
        viewModelScope.launch {
            val list = firebaseRepo.getAllFavSerie()
            val tmpList = mutableListOf<TVDetailWrapper>()
            list.forEach { id ->
                val tmpRes = apiRepo.getTvDetail( id.toInt())
                if (tmpRes.error.isNullOrEmpty()) {
                    tmpList.add(tmpRes.data!!)
                }
            }
            favorites.value = tmpList
        }
    }

    fun getAllLaterSeries(){
        viewModelScope.launch {
            val list = firebaseRepo.getAllLaterSerie()
            val tmpList = mutableListOf<TVDetailWrapper>()
            list.forEach { id ->
                val tmpRes = apiRepo.getTvDetail( id.toInt())
                if (tmpRes.error.isNullOrEmpty()) {
                    tmpList.add(tmpRes.data!!)
                }
            }
            later.value = tmpList
        }
    }
}