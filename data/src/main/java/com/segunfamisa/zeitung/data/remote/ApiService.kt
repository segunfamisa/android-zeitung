package com.segunfamisa.zeitung.data.remote

import com.segunfamisa.zeitung.data.remote.entities.ArticlesResponse
import com.segunfamisa.zeitung.data.remote.entities.SourcesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {

    @GET("top-headlines")
    fun getHeadlines(
        @Query("country") country: String? = null,
        @Query("category") category: String? = null,
        @Query("sources") sources: String? = null,
        @Query("query") query: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): Deferred<Response<ArticlesResponse>>

    @GET("sources")
    fun getSources(
        @Query("category") category: String? = null,
        @Query("language") language: String? = null,
        @Query("country") country: String? = null
    ): Deferred<Response<SourcesResponse>>
}
