package com.example.newsapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
    onSearchButtonClicked: () -> Unit
) {
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
