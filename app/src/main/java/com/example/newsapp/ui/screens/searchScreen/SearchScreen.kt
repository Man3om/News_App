package com.example.newsapp.ui.screens.searchScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ui.screens.Resources
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.screens.News.NewsCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    textFieldState: TextFieldState = TextFieldState(),
) {
    // Controls expansion state of the search bar
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier
            .semantics { isTraversalGroup = true }) {

        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                    onSearch = {
                        viewModel.searchArticles(textFieldState.text.toString())
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search") }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            )
        ) {}
    }
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier, viewModel: SearchViewModel = viewModel()) {
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        SearchBarComponent(modifier = modifier, viewModel = viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        HandleArticlesList(viewModel, modifier)
    }
}

@Composable
private fun HandleArticlesList(
    viewModel: SearchViewModel,
    modifier: Modifier
) {
    val articlesState = viewModel.searchResults.collectAsState().value
    Log.d("SearchScreen", "State: $articlesState")

    when (articlesState) {
        is Resources.Error -> {
            if (articlesState.message.isNotEmpty()) {
                Toast.makeText(
                    LocalContext.current,
                    articlesState.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        is Resources.Initial -> {}
        is Resources.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Log.d("SearchScreen", "Loading")
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
            }
        }

        is Resources.Success -> {
            Log.d("SearchScreen", "Success: ${articlesState.response}")
            LazyColumn(modifier = modifier) {
                itemsIndexed(articlesState.response) { index, item ->
                    NewsCard(article = item)
                }
            }
        }
    }
}
