package com.example.newsapp.ui.screens.categories

import com.example.newsapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

@Serializable
data class CategoryItemDM(
    val title: Int? = null,
    val image: Int? = null,
    val apiID: String? = null,
    val index: Int? = null
) {
    companion object {
        fun getCategories(): MutableStateFlow<List<CategoryItemDM>> {
            return MutableStateFlow(
                listOf(
                    CategoryItemDM(
                        title = R.string.general,
                        image = R.drawable.general,
                        apiID = "general",
                        index = 0
                    ),
                    CategoryItemDM(
                        title = R.string.business,
                        image = R.drawable.bussnies,
                        apiID = "business",
                        index = 1
                    ),
                    CategoryItemDM(
                        title = R.string.entertainment,
                        image = R.drawable.entertainment,
                        apiID = "entertainment",
                        index = 2
                    ),
                    CategoryItemDM(
                        title = R.string.health,
                        image = R.drawable.health,
                        apiID = "health",
                        index = 3
                    ),
                    CategoryItemDM(
                        title = R.string.science,
                        image = R.drawable.science,
                        apiID = "science",
                        index = 4
                    ),
                    CategoryItemDM(
                        title = R.string.sports,
                        image = R.drawable.sports,
                        apiID = "sports",
                        index = 5
                    )
                )
            )
        }
    }
}