package com.example.newsapp.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager {
    companion object {
        private var retrofit: Retrofit? = null

        private fun initRetrofit(): Retrofit {
            if (retrofit == null) {
                val logging = HttpLoggingInterceptor{
                    Log.e("API", it)
                }.apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }

                class APiInterceptor : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val requestUrl = chain.request().url.newBuilder()
                        val request = chain.request().newBuilder()
                        requestUrl.addQueryParameter("apiKey","06d492c0e0074c2ba430339bf1839078")
                        request.url(requestUrl.build())
                        return chain.proceed(request.build())
                    }
                }

                val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(APiInterceptor())
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