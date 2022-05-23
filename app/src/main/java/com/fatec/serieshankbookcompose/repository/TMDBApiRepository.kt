package com.fatec.serieshankbookcompose.repository

import com.fatec.serieshankbookcompose.data.remote.SearchWrapper
import com.fatec.serieshankbookcompose.data.remote.TVDetailWrapper
import com.fatec.serieshankbookcompose.data.remote.DiscoveryWrapper
import com.fatec.serieshankbookcompose.data.remote.MovieDetailWrapper
import com.fatec.serieshankbookcompose.network.ITMDBApi
import com.fatec.serieshankbookcompose.util.ResponseWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TMDBApiRepository @Inject constructor(
    private val api: ITMDBApi
) {
    // faz as requisições de acordo com os end-points do network e transforma nas classses
    // correspondentes
    suspend fun getSearch(searchString: String): ResponseWrapper<SearchWrapper> {
        return try {
            ResponseWrapper.Success(api.getSearch(query = searchString))
        }catch (e : Exception){
            ResponseWrapper.Error(error=e.message!!)
        }
    }

    suspend fun getSearch(searchString: String, page: Int): ResponseWrapper<SearchWrapper> {
        return try {
            ResponseWrapper.Success(api.getSearch(query = searchString,page = page))
        }catch (e : Exception){
            ResponseWrapper.Error(error=e.message!!)
        }
    }

    suspend fun getTvDiscoveryPop(): ResponseWrapper<DiscoveryWrapper> {
        return try {
            ResponseWrapper.Success(api.getTVPop())
        }catch (e : Exception){
            ResponseWrapper.Error(error=e.message!!)
        }
    }

    suspend fun getTvDiscoveryTop(): ResponseWrapper<DiscoveryWrapper> {
        return try {
            ResponseWrapper.Success(api.getTVTop())
        }catch (e : Exception){
            ResponseWrapper.Error(error=e.message!!)
        }
    }
    suspend fun getTvDetail(id : Int): ResponseWrapper<TVDetailWrapper> {
        return try {
            ResponseWrapper.Success(api.getTVDetail(id))
        }catch (e : Exception){
            ResponseWrapper.Error(error=e.message!!)
        }
    }

    suspend fun getMovieDiscoveryPop(): ResponseWrapper<DiscoveryWrapper> {
        return try {
            ResponseWrapper.Success(api.getMoviePop())
        }catch (e : Exception){
            ResponseWrapper.Error(error=e.message!!)
        }
    }

    suspend fun getMovieDiscoveryTop(): ResponseWrapper<DiscoveryWrapper> {
        return try {
            ResponseWrapper.Success(api.getMovieTop())
        }catch (e : Exception){
            ResponseWrapper.Error(error=e.message!!)
        }
    }
    suspend fun getMovieDetail(id : Int): ResponseWrapper<MovieDetailWrapper> {
        return try {
            ResponseWrapper.Success(api.getMovieDetail(id))
        }catch (e : Exception){
            ResponseWrapper.Error(error=e.message!!)
        }
    }

}