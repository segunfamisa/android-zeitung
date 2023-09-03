package com.segunfamisa.zeitung.data.remote.service

import com.segunfamisa.zeitung.data.remote.common.ApiResponse
import com.segunfamisa.zeitung.data.remote.entities.ArticlesResponse
import com.segunfamisa.zeitung.data.remote.entities.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {

    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String? = null,
        @Query("category") category: String? = null,
        @Query("sources") sources: String? = null,
        @Query("query") query: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): ApiResponse<ArticlesResponse>

    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String? = null,
        @Query("sources") sources: String? = null,
        @Query("domains") domains: String? = null,
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null,
        @Query("language") language: String? = null,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("page") page: Int? = null
    ): ApiResponse<ArticlesResponse>

    @GET("sources")
    suspend fun getSources(
        @Query("category") category: String? = null,
        @Query("language") language: String? = null,
        @Query("country") country: String? = null
    ): ApiResponse<SourcesResponse>
}
