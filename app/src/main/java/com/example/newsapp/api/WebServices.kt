package com.example.newsapp.api

import com.example.newsapp.api.model.everythingResponseApiModel.EverythingResponse
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
   suspend fun getNewsSources(
        @Query("category") categoryApiId: String? = null
    ): Response<SourcesResponse>

    @GET("v2/everything")
   suspend fun getNewsBySource(
        @Query("sources") sources: String? = null,
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = 15
    ) : Response<EverythingResponse>

   @GET("v2/everything")
    suspend fun getNewsSearch(
        @Query("q") search: String? = null,
    ) : Response<EverythingResponse>
}