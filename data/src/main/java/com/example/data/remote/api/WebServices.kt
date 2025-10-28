package com.example.data.remote.api

import com.example.data.models.news.everythingResponseApiModel.EverythingResponseModel
import com.example.data.models.news.sourceResponseApiModel.SourcesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
   suspend fun getNewsSources(
        @Query("category") categoryApiId: String? = null
    ): Response<SourcesResponseModel>

    @GET("v2/everything")
   suspend fun getNewsBySource(
        @Query("sources") sources: String? = null,
    ) : Response<EverythingResponseModel>

   @GET("v2/everything")
    suspend fun getNewsSearch(
        @Query("q") search: String? = null,
    ) : Response<EverythingResponseModel>
}