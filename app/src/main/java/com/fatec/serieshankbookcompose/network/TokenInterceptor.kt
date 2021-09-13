package com.fatec.serieshankbookcompose.network

import android.util.Log
import com.fatec.serieshankbookcompose.repository.FirebaseRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()

        val token = runBlocking { firebaseRepository.getAPIKeyAsync() }

        val url = original.url.newBuilder().addQueryParameter("api_key", token).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }

}