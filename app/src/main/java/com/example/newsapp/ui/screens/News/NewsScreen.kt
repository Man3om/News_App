package com.example.newsapp.ui.screens.News

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesItemDM
import com.example.newsapp.ui.destinations.ArticleDestinations
import com.example.newsapp.ui.screens.Resources

@Composable
fun NewsScreen(
    category: String,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(),
    navController: NavHostController
) {
    Column(modifier.fillMaxSize()) {
        NewsSourcesTopRow(category, viewModel = viewModel)
        NewsLazyColumn(viewModel, navController = navController)
    }
}

@Composable
fun NewsLazyColumn(
    newsViewModel: NewsViewModel, modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val articleState = newsViewModel.articlesResource.collectAsState().value

    when (articleState) {
        is Resources.Error -> {
            if (articleState.message.isNotEmpty()) {
                Toast.makeText(
                    LocalContext.current,
                    articleState.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        is Resources.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
            }
        }

        is Resources.Success -> {
            LazyColumn(modifier = modifier) {
                itemsIndexed(articleState.response) { index, item ->
                    NewsCard(article = item){
                        url, description ->
                        Log.d("NewsScreen", "NewsCardUrl: $url")
                        Log.d("NewsScreen", "NewsCardDescription: $description")
                        navController.navigate(ArticleDestinations(description, url))
                    }
                }
            }
        }

        is Resources.Initial -> {
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    article: ArticlesItem?,
    onNewsCardClick: (String, String) -> Unit
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .clickable {
                  onNewsCardClick(article?.urlToImage ?: "", article?.description ?: "")
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
    ) {
        // Image
        GlideImage(
            model = article?.urlToImage,
            contentDescription = "Translated description of what the image contains",
            modifier = Modifier
                .padding(10.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )
        //Text
        Text(
            text = article?.description ?: "",
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
                text = "By: ${article?.author ?: ""}",
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
                text = article?.publishedAt ?: "",
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
    categoryApiId: String, modifier: Modifier = Modifier, viewModel: NewsViewModel
) {
    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }

    LaunchedEffect(Unit) {
        viewModel.getSources(categoryApiId)
    }

    LaunchedEffect(viewModel.sourcesResource.collectAsState().value) {
        if (viewModel.sourcesResource.value is Resources.Success) {
            val reposeSuccess =
                (viewModel.sourcesResource.value as Resources.Success<List<SourcesItemDM>>).response
            viewModel.selectedSourceId.value = reposeSuccess[0].id ?: ""
            selectedIndex = 0
        }
    }

    val state = viewModel.sourcesResource.collectAsState().value

    when (state) {
        is Resources.Error -> {
            if (state.message.isNotEmpty()) {
                Toast.makeText(
                    LocalContext.current,
                    state.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        is Resources.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
            }
        }

        is Resources.Success -> {
            LazyRow(modifier) {
                itemsIndexed(state.response) { index, item ->
                    SourceItem(
                        item = item, index = index, selectedIndex = selectedIndex
                    ) { item ->
                        selectedIndex = index
                        viewModel.selectedSourceId.value = item.id ?: ""
                    }
                }
            }

        }

        is Resources.Initial -> {

        }
    }

    val selectedId = viewModel.selectedSourceId.collectAsState().value
    LaunchedEffect(selectedId) {
        if (selectedId.isNotEmpty()) {
            viewModel.getNewsBySourceId(selectedId)
        }
    }
}

@Composable
fun SourceItem(
    item: SourcesItemDM,
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    index: Int,
    onClick: (SourcesItemDM) -> Unit
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