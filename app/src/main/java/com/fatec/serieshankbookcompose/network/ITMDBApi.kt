package com.fatec.serieshankbookcompose.network

import com.fatec.serieshankbookcompose.data.remote.MovieDetailWrapper
import com.fatec.serieshankbookcompose.data.remote.SearchWrapper
import com.fatec.serieshankbookcompose.data.remote.TVDetailWrapper
import com.fatec.serieshankbookcompose.data.remote.DiscoveryWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ITMDBApi {
    // configura os end-points para trazer as informações que precisamos de acordo
    // com https://developers.themoviedb.org/3/getting-started/introduction

    @GET("tv/{tv_id}")
    suspend fun getTVDetail(
        @Path("tv_id") id: Int,
        @Query("language") language : String ="pt-BR",
    ) : TVDetailWrapper

    @GET("tv/top_rated")
    suspend fun getTVTop(@Query("language") language : String ="pt-BR",) : DiscoveryWrapper

    @GET("tv/popular")
    suspend fun getTVPop(@Query("language") language : String ="pt-BR",) : DiscoveryWrapper

    @GET("tv/{tv_id}/similar")
    suspend fun getTVSimilar(@Path("tv_id") id: Int,@Query("language") language : String ="pt-BR",) : DiscoveryWrapper

    @GET("search/multi")
    suspend fun getSearch(
        @Query("query") query : String,
        @Query("language") language : String ="pt-BR",
        @Query("page") page : Int = 1
    ) : SearchWrapper

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("language") language : String ="pt-BR",
    ) : MovieDetailWrapper

    @GET("movie/top_rated")
    suspend fun getMovieTop(@Query("language") language : String ="pt-BR",) : DiscoveryWrapper

    @GET("movie/popular")
    suspend fun getMoviePop(@Query("language") language : String ="pt-BR",) : DiscoveryWrapper

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieSimilar(@Path("movie_id") id: Int,@Query("language") language : String ="pt-BR",) : DiscoveryWrapper
}