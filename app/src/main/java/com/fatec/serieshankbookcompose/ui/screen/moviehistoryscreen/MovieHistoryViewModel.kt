package com.fatec.serieshankbookcompose.ui.screen.moviehistoryscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.serieshankbookcompose.data.remote.MovieDetailWrapper
import com.fatec.serieshankbookcompose.data.remote.TVDetailWrapper
import com.fatec.serieshankbookcompose.repository.FirebaseRepository
import com.fatec.serieshankbookcompose.repository.TMDBApiRepository
import com.fatec.serieshankbookcompose.util.ConvertDateUtil.Factory.formatTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.Timestamp
import javax.inject.Inject

@HiltViewModel
class MovieHistoryViewModel @Inject constructor(
    private val apiRepo : TMDBApiRepository,
    private val firebaseRepo : FirebaseRepository
) : ViewModel() {
    val allHistory = mutableStateOf<MutableList<MovieDetailWrapper>?>(null)
    fun getHistory() {
        viewModelScope.launch {
            val list: MutableList<MovieDetailWrapper> = mutableListOf()
            val listWatched = firebaseRepo.getAllMoviesWatched()
            var uniqueId: MutableList<Int> = mutableListOf()
            listWatched.forEach {
                uniqueId.add(it.id)
            }
            uniqueId = uniqueId.distinct().toMutableList()
            uniqueId.forEach {
                val item = apiRepo.getMovieDetail(it).data!!
                listWatched.forEach { it2 ->
                    if (it == it2.id) {
                        item.release_date = Timestamp(it2.date).formatTo("dd/MM/yyyy")
                    }
                }
                list.add(item)
            }
            allHistory.value = list
        }
    }
}