package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.model.CategoryItemDM
import com.example.newsapp.ui.components.NewsToolbar
import com.example.newsapp.ui.destinations.CategoriesDestinations
import com.example.newsapp.ui.destinations.NewsDestinations
import com.example.newsapp.ui.screens.News.NewsScreen
import com.example.newsapp.ui.screens.categories.CategoriesScreen
import com.example.newsapp.ui.theme.NewsAppTheme
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
                val homeText = stringResource(R.string.home)
                val title = remember {
                    mutableStateOf(homeText)
                }

                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(), bottomBar = {

                    }, topBar = {
                        NewsToolbar(
                            title = title.value,
                            onMenuButtonClicked = {},
                            onSearchButtonClicked = {})
                    }) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                        navController = navController,
                        startDestination = CategoriesDestinations
                    ) {
                        composable<CategoriesDestinations> {
                            title.value = homeText
                            CategoriesScreen(navHostController = navController)
                        }

                        composable<NewsDestinations> {
                            val category = it.toRoute<CategoryItemDM>()

                        }
                    }
                }
            }
        }

        fun getSources() {
            ApiManager.webServices().getNewsSources().enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse?>, response: Response<SourcesResponse?>
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
}

