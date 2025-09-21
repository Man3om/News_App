package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat.enableEdgeToEdge
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.ui.theme.NewsAppTheme
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                LaunchedEffect(Unit) {
                    getSources()
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {

                    },
                    topBar = {

                    }) { innerPadding ->
                }
            }
        }
    }

    fun getSources() {
        ApiManager.webServices().getNewsSources().enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(
                call: Call<SourcesResponse?>,
                response: Response<SourcesResponse?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.i(TAG, "onResponse: $responseBody")
                } else {
                    val errorBody = response.errorBody()
                    Log.e(TAG, "onResponse: $errorBody")
                }
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}