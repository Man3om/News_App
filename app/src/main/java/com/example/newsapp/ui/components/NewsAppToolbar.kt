package com.example.newsapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopSearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsToolbar(
    modifier: Modifier = Modifier,
    title: String,
    onMenuButtonClicked: () -> Unit,
    onSearchButtonClicked: () -> Unit,
    onSearchClose: () -> Unit,
    isSearching: Boolean
) {
    when (isSearching) {
        true -> {
            TopSearchBar(
                state = rememberSearchBarState(),
                modifier = Modifier.fillMaxWidth(),
                colors = SearchBarDefaults.colors(Color.Transparent),
                inputField = {
                    OutlinedTextField(
                        state = rememberTextFieldState(),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        placeholder = { Text("Search") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.clickable {

                                }
                            )
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.clickable {
                                    onSearchClose()
                                }
                            )
                        }
                    )
                }
            )
        }

        false -> {
            CenterAlignedTopAppBar(modifier = modifier.padding(start = 8.dp, end = 8.dp), title = {
                Text(title)
            }, actions = {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(R.string.search_icon),
                    modifier = Modifier
                        .clickable {
                            onSearchButtonClicked()
                        }
                        .width(30.dp),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground))

            }, navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = stringResource(R.string.menu_icon),
                    modifier = Modifier
                        .clickable {
                            onMenuButtonClicked()
                        }
                        .width(30.dp),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            })
        }
    }

}
