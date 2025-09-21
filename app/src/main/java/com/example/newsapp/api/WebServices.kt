package com.example.newsapp.api

import com.example.newsapp.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
    fun getNewsSources(
        @Query("apiKey") apiKey: String = "06d492c0e0074c2ba430339bf1839078"
    ): Call<SourcesResponse>
}