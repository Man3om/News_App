package com.example.newsapp.ui.mainActivity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp.R
import com.example.newsapp.ui.components.DrawerContent
import com.example.newsapp.ui.components.NewsToolbar
import com.example.newsapp.ui.destinations.ArticleDestinations
import com.example.newsapp.ui.destinations.CategoriesDestinations
import com.example.newsapp.ui.destinations.NewsDestinations
import com.example.newsapp.ui.destinations.SearchDestination
import com.example.newsapp.ui.dialogs.articleDialog.ArticleBottomSheet
import com.example.newsapp.ui.screens.News.NewsCard
import com.example.newsapp.ui.screens.News.NewsScreen
import com.example.domain.utils.base.Resources
import com.example.newsapp.ui.screens.categories.CategoriesScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val homeText = stringResource(R.string.home)
                val title = remember {
                    mutableStateOf(homeText)
                }
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val isSearching = remember {
                    mutableStateOf(false)
                }
                val query = remember {
                    mutableStateOf("")
                }
                val navController = rememberNavController()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent() {
                            scope.launch { drawerState.close() }
                        }
                    },
                    gesturesEnabled = true
                ) {
                    Scaffold(
                        modifier = Modifier.Companion
                            .fillMaxSize()
                            .navigationBarsPadding(), bottomBar = {

                        }, topBar = {
                            NewsToolbar(
                                title = title.value,
                                onMenuButtonClicked = {
                                    scope.launch { drawerState.open() }
                                },
                                onSearchClose = {
                                    isSearching.value = false
                                },
                                onSearchButtonClicked = {
                                    isSearching.value = true
                                },
                                isSearching = isSearching.value,
                                onSearchIconClickedForSearching = {
                                    Log.d("MainActivity", "onSearchIconClickedForSearching: $it")
                                    query.value = it
                                }
                            )
                        }
                    ) { innerPadding ->
                        when(isSearching.value){
                            true -> SearchScreen(innerPadding = innerPadding , navController = navController , query = query.value)
                            false -> HomeScreen(innerPadding, navController, title, homeText)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier,query: String, innerPadding: PaddingValues, viewModel: MainActivityViewModel = viewModel(), navController: NavHostController) {
    Box(modifier = Modifier.padding(innerPadding)){
        val articlesState = viewModel.searchResults.collectAsState().value
        Log.d("SearchScreen", "State: $articlesState")

        LaunchedEffect(key1 = query) {
            if (query.isNotEmpty())
                viewModel.searchArticles(query)
        }

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
                        NewsCard(article = item){imageUrl, description ,articleUrl->
                            // Navigate to ArticleScreen
                            navController.navigate(ArticleDestinations(description, imageUrl,articleUrl))
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun HomeScreen(
    innerPadding: PaddingValues,
    navController: NavHostController,
    title: MutableState<String>,
    homeText: String
) {
    NavHost(
        modifier = Modifier.Companion.padding(top = innerPadding.calculateTopPadding()),
        navController = navController,
        startDestination = CategoriesDestinations
    ) {
        composable<CategoriesDestinations> {
            title.value = homeText
            CategoriesScreen(navHostController = navController)
        }

        composable<NewsDestinations> {
            val destination = it.toRoute<NewsDestinations>()
            title.value = destination.categoryApiId
            NewsScreen(
                category = destination.categoryApiId,
                navController = navController
            )
        }

//        composable<SearchDestination> {
//            title.value = "Search"
//            SearchScreen(navController = navController)
//        }

        dialog<ArticleDestinations> {
            val destination = it.toRoute<ArticleDestinations>()
            ArticleBottomSheet(
                articleUrl = destination.articleUrl,
                imageUrl = destination.imageUrl,
                description = destination.description,
                onDismiss = {
                    navController.popBackStack()
                }
            )
        }
    }
}