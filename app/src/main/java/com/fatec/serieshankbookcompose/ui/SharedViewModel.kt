package com.fatec.serieshankbookcompose.ui

import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.serieshankbookcompose.data.remote.ResultX
import com.fatec.serieshankbookcompose.repository.FirebaseRepository
import com.fatec.serieshankbookcompose.repository.TMDBApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val apiRepo : TMDBApiRepository
) : ViewModel()
{
    private val pag = mutableStateOf(1)
    private val EOF = mutableStateOf(false)

    private var searchCache = mutableListOf<ResultX>()
    val searchList = mutableStateOf<MutableList<ResultX>?>(null)
    val isLoading = mutableStateOf(false)
    val errorMsg = mutableStateOf("")
    val qString = mutableStateOf("")

    var loggedUser = mutableStateOf("")

    fun logout() {
        viewModelScope.launch {
            firebaseRepository.logoutFirebase()
        }
    }

    fun createLoginIntent() :Intent {
        val saida = firebaseRepository.createLoginIntent()
        getUserEmail()
        return  saida
    }

    fun getUserEmail() {
        loggedUser.value = firebaseRepository.getUserEmail()
    }

    fun loginAttempt(){
        loggedUser.value = "desistiu"
    }

    fun getSearch(queryString : String){
        viewModelScope.launch {
            isLoading.value = true
            val res = apiRepo.getSearch(queryString)
            if (res.error.isNullOrEmpty()) {
                EOF.value = (res.data?.page == res.data?.total_pages)
                searchCache = res.data?.results!!
                searchList.value = searchCache
                errorMsg.value = ""
            }else{
                errorMsg.value = res.error
                searchList.value = null
            }
            pag.value = 1
            qString.value = queryString
            isLoading.value = false
        }
    }

    fun getNextPage(){
        viewModelScope.launch {
            if (!EOF.value) {
                isLoading.value = true
                pag.value = pag.value + 1
                val res = apiRepo.getSearch(qString.value, pag.value)
                if (res.error.isNullOrEmpty()) {
                    EOF.value = (res.data?.page == res.data?.total_pages)
                    searchList.value?.addAll(res.data?.results!!)
                    searchCache = searchList.value!!
                    errorMsg.value = ""
                } else {
                    errorMsg.value = res.error
                    searchList.value = null
                }
                isLoading.value = false
            }
        }
    }
}