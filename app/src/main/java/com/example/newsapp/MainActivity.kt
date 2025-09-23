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
import androidx.compose.foundation.layout.padding
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
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.model.CategoryItemDM
import com.example.newsapp.ui.components.NewsToolbar
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

                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {

                }, topBar = {
                    NewsToolbar(
                        title = title.value,
                        onMenuButtonClicked = {},
                        onSearchButtonClicked = {})
                }) { innerPadding ->
                    CategoriesScreen(modifier = Modifier.padding(top = innerPadding.calculateTopPadding()))
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

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            "Good Morning\n Here is Some News For You",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.W400
        )

        CategoryList()
    }
}

@Composable
fun CategoryList(modifier: Modifier = Modifier) {
    val categories = CategoryItemDM.getCategories()
    LazyColumn {
        itemsIndexed(categories) { index, item ->
            CategoryItem(category = item, index = index)
        }
    }

}

@Composable
fun CategoryItem(category: CategoryItemDM, index: Int, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
            contentColor = MaterialTheme.colorScheme.background,
        ), modifier = modifier.padding(
            start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp
        )
    ) {
        if (index % 2 == 0) {
            EvenCategoryItem(category)
        } else {
            OddCategoryItem(category)
        }
    }
}

@Composable
fun ColumnScope.EvenCategoryItem(item: CategoryItemDM, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.image!!),
            contentDescription = null,
            modifier = Modifier.width(150.dp)
        )

        // Category Name and View All
        Column(
            modifier = Modifier.padding(start = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(item.title!!),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W600,
                fontSize = 30.sp
            )

            // View All Component
            Row(
                modifier = Modifier
                    .padding(15.dp)
                    .background(
                        shape = CircleShape,
                        color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                    ), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "View All",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W600,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Image(
                    contentDescription = "",
                    painter = painterResource(id = R.drawable.view_all_arrow),
                    modifier = Modifier
                        .width(50.dp)
                        .background(
                            shape = CircleShape, color = MaterialTheme.colorScheme.background
                        ),
                    contentScale = ContentScale.FillWidth,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
                )
            }
        }
    }
}

@Composable
fun ColumnScope.OddCategoryItem(item: CategoryItemDM, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(start = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(item.title!!),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W600,
                fontSize = 30.sp
            )

            // View All Component
            Row(
                modifier = Modifier
                    .padding(15.dp)
                    .background(
                        shape = CircleShape,
                        color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                    ), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    contentDescription = "",
                    painter = painterResource(id = R.drawable.view_all_arrow),
                    modifier = Modifier
                        .width(50.dp)
                        .background(
                            shape = CircleShape, color = MaterialTheme.colorScheme.background
                        )
                        .graphicsLayer(rotationY = 180f),
                    contentScale = ContentScale.FillWidth,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
                )

                Text(
                    "View All",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W600,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }

        Image(
            painter = painterResource(id = item.image!!),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 20.dp)
                .width(200.dp)
        )
    }
}

@Preview
@Composable
private fun CategoryItemPreview() {
    CategoryItem(
        category = CategoryItemDM.getCategories()[0], index = 0
    )
}

@Preview
@Composable
private fun CategoryItemPreviewodd() {
    CategoryItem(
        category = CategoryItemDM.getCategories()[1], index = 1
    )
}
