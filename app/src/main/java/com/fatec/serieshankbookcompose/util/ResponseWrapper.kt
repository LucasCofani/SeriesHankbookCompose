package com.fatec.serieshankbookcompose.util

// classe generica para pegar o resultado dos gets da api
sealed class ResponseWrapper<T>(
    val loading: Boolean = false,
    val data: T? = null,
    val error: String? = null
) {
    class Success<T>(data: T) : ResponseWrapper<T>(data = data, loading = false)
    class Error<T>(error: String, data: T? = null) :
        ResponseWrapper<T>(data = data, error = error, loading = false)

    class Loading<T>(data: T? = null) : ResponseWrapper<T>(data = data, loading = true)
}
