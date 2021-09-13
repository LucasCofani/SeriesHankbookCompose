package com.fatec.serieshankbookcompose.ui.screen.seriediscoveryscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.serieshankbookcompose.data.remote.ResultX
import com.fatec.serieshankbookcompose.repository.TMDBApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SerieDiscoveryViewModel @Inject constructor(
    private val apiRepo : TMDBApiRepository
) : ViewModel() {

    val discoveryPop = mutableStateOf<MutableList<ResultX>?>(null)
    val discoveryTop = mutableStateOf<MutableList<ResultX>?>(null)


    fun getDiscovery(){
        viewModelScope.launch {
            if (
                discoveryPop.value == null ||
                discoveryTop.value == null
            ){
                val resTop = apiRepo.getTvDiscoveryTop()
                val resPop = apiRepo.getTvDiscoveryPop()
                if (resTop.error.isNullOrEmpty()) {
                    discoveryTop.value = resTop.data?.results!!
                }
                if (resPop.error.isNullOrEmpty()){
                    discoveryPop.value = resPop.data?.results!!
                }
            }
        }
    }
}