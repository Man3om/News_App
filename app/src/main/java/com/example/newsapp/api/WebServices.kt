package com.example.newsapp.api

import com.example.newsapp.api.model.everythingResponseApiModel.EverythingResponse
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
    fun getNewsSources(
        @Query("apiKey") apiKey: String = "06d492c0e0074c2ba430339bf1839078",
        @Query("category") categoryApiId: String? = null
    ): Call<SourcesResponse>

    @GET("v2/everything")
    fun getNewsBySource(
        @Query("apiKey") apiKey: String = "06d492c0e0074c2ba430339bf1839078",
        @Query("sources") sources: String? = null
    ) : Call<EverythingResponse>
}