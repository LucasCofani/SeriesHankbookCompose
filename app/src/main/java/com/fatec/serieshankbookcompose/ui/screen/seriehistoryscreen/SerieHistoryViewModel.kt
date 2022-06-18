package com.fatec.serieshankbookcompose.ui.screen.seriehistoryscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.serieshankbookcompose.data.remote.ResultX
import com.fatec.serieshankbookcompose.data.remote.TVDetailWrapper
import com.fatec.serieshankbookcompose.repository.FirebaseRepository
import com.fatec.serieshankbookcompose.repository.TMDBApiRepository
import com.fatec.serieshankbookcompose.util.ConvertDateUtil.Factory.formatTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.Timestamp
import javax.inject.Inject

@HiltViewModel
class SerieHistoryViewModel @Inject constructor(
    private val apiRepo : TMDBApiRepository,
    private val firebaseRepo : FirebaseRepository
) : ViewModel() {
    val allHistory = mutableStateOf<MutableList<TVDetailWrapper>?>(null)
    fun getHistory(){
        viewModelScope.launch {
            val list : MutableList<TVDetailWrapper> = mutableListOf()
            val listWatched = firebaseRepo.getAllSeriesWatched()
            var uniqueId : MutableList<Int> = mutableListOf()
            listWatched.forEach {
                uniqueId.add(it.id)
            }
            uniqueId = uniqueId.distinct().toMutableList()
            uniqueId.forEach {
                val item = apiRepo.getTvDetail(it).data!!
                listWatched.forEach { it2 ->
                    if (it == it2.id){
                        item.first_air_date = Timestamp(it2.date).formatTo("dd/MM/yyyy")
                    }
                }
                list.add(item)
            }
            allHistory.value = list
        }
    }
}