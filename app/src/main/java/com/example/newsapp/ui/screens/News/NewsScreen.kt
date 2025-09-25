package com.example.newsapp.ui.screens.News

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.toolbox.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.api.model.everythingResponseApiModel.EverythingResponse
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesItem
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewsScreen(category: String, modifier: Modifier = Modifier) {
    val news = remember {
        mutableStateListOf<ArticlesItem>()
    }

    Column(modifier.fillMaxSize()) {
        NewsSourcesTopRow(category) {
            news.clear()
            news.addAll(it)
        }

        NewsLazyColumn(news)
    }
}

@Composable
fun NewsLazyColumn(news: SnapshotStateList<ArticlesItem>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(news) { index, item ->
            NewsCard(article = item)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(modifier: Modifier = Modifier, article: ArticlesItem) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground)
    ) {
        // Image
        GlideImage(
            model = article.urlToImage,
            contentDescription = "Translated description of what the image contains",
            modifier = Modifier
                .padding(10.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )
        //Text
        Text(
            text = article.description ?: "",
            modifier = Modifier.padding(8.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
            color = MaterialTheme.colorScheme.onBackground
        )

        //Author and Time
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Author
            Text(
                text = "By: ${article.author ?: ""}",
                modifier = Modifier
                    .padding(8.dp)
                    .weight(2f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.weight(0.5f))

            // Time
            Text(
                text = article.publishedAt ?: "",
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1.5f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun NewsSourcesTopRow(
    categoryApiId: String,
    modifier: Modifier = Modifier,
    onUpdateNewsList: (List<ArticlesItem>) -> Unit
) {
    val newsSources = remember {
        mutableStateListOf<SourcesItem>()
    }

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(Unit) {
        getSources(categoryApiId) {
            newsSources.clear()
            newsSources.addAll(it)
        }
    }

    LazyRow(modifier) {
        itemsIndexed(newsSources) { index, item ->
            SourceItem(
                item = item, index = index, selectedIndex = selectedIndex
            ) { item ->
                selectedIndex = index
                getNewsBySourceId(item.id.toString()) {
                    Log.d("News API", "Selected Source: ${item.name}")
                    onUpdateNewsList(it)
                }
            }
        }
    }
}

@Composable
fun SourceItem(
    item: SourcesItem,
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    index: Int,
    onClick: (SourcesItem) -> Unit
) {
    val selectedTextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W700,
        color = MaterialTheme.colorScheme.onBackground
    )
    val unselectedTextStyle = TextStyle(
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.W500
    )
    Text(
        text = item.name.toString(),
        style = if (selectedIndex == index) selectedTextStyle else unselectedTextStyle,
        modifier = modifier
            .clickable {
                onClick(item)
            }
            .padding(horizontal = 8.dp, vertical = 5.dp),
        textDecoration = if (selectedIndex == index) TextDecoration.Underline else TextDecoration.None)
}

fun getSources(categoryApiId: String, onSuccess: (List<SourcesItem>) -> Unit) {
    val TAG = "Sources API"

    ApiManager.webServices().getNewsSources(categoryApiId = categoryApiId)
        .enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(
                call: Call<SourcesResponse?>, response: Response<SourcesResponse?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.i(TAG, "onResponse: ${responseBody?.status}")
                    Log.i(TAG, "onResponse: ${responseBody?.sources}")

                    onSuccess(responseBody?.sources ?: listOf())
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

fun getNewsBySourceId(sourceId: String, onSuccess: (List<ArticlesItem>) -> Unit) {
    ApiManager.webServices().getNewsBySource(sources = sourceId)
        .enqueue(object : Callback<EverythingResponse> {
            override fun onResponse(
                call: Call<EverythingResponse?>, response: Response<EverythingResponse?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.i("News API", "onResponse: ${responseBody?.status}")
                    Log.i("News API", "onResponse: ${responseBody?.articles}")
                    onSuccess(responseBody?.articles ?: listOf())
                } else {
                    val errorBody = response.errorBody()
                    Log.e("News API", "onResponse: $errorBody")
                }
            }

            override fun onFailure(
                call: Call<EverythingResponse?>, t: Throwable
            ) {
                Log.e("News API", "onFailure: ${t.message}")
            }

        })

}