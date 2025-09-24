package com.example.newsapp.ui.screens.News

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.SourcesItem
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.model.CategoryItemDM
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewsScreen(category: CategoryItemDM, modifier: Modifier = Modifier) {
    Column(modifier) {
        NewsSourcesTopRow(category)
        NewsLazyColumn()
    }
}

@Composable
fun NewsLazyColumn() {
    TODO("Not yet implemented")
}

@Composable
fun NewsSourcesTopRow(category: CategoryItemDM) {
    val newsSources = remember {
        mutableStateListOf<SourcesItem>()
    }

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    LazyRow() {
        itemsIndexed(newsSources) { index, item ->
            SourceItem(item = item, index = index, selectedIndex = selectedIndex) { item, index ->
                selectedIndex = index
                getSources(category) {
                    newsSources.clear()
                    newsSources.addAll(it)
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
    onClick: (SourcesItem, Int) -> Unit
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
        modifier = modifier.clickable {
            onClick(item, index)
        },
        textDecoration = if (selectedIndex == index) TextDecoration.Underline else TextDecoration.None
    )
}

fun getSources(category: CategoryItemDM, onSuccess: (List<SourcesItem>) -> Unit) {
    val TAG = "Sources API"

    ApiManager.webServices().getNewsSources(categoryApiId = category.apiID)
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