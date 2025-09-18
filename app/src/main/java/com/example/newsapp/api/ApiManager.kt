package com.example.newsapp.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager {
    companion object {
        private var retrofit: Retrofit? = null

        private fun initRetrofit(): Retrofit {
            if (retrofit == null) {
                val logging = HttpLoggingInterceptor(logger = { message ->
                    Log.d("API", message)
                })

                val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

                retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://newsapi.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }
            return retrofit!!
        }

        fun webServices(): WebServices {
            return initRetrofit().create(WebServices::class.java)
        }
    }
}