package com.example.newsapp.model

import com.example.newsapp.R

data class CategoryItemDM(
    val title: Int? = null,
    val image: Int? = null,
    val apiID: String? = null,
    val index: Int? = null
) {
    companion object {
        fun getCategories(): List<CategoryItemDM> {
            return listOf(
                CategoryItemDM(
                    title = R.string.general, image = R.drawable.general, apiID = "general"
                ),
                CategoryItemDM(
                    title = R.string.business, image = R.drawable.bussnies, apiID = "business"
                ),
                CategoryItemDM(
                    title = R.string.entertainment,
                    image = R.drawable.entertainment,
                    apiID = "entertainment"
                ),
                CategoryItemDM(
                    title = R.string.health, image = R.drawable.health, apiID = "health"
                ),
                CategoryItemDM(
                    title = R.string.science, image = R.drawable.science, apiID = "science"
                ),
                CategoryItemDM(
                    title = R.string.technology, image = R.drawable.tech, apiID = "technology"
                ),
                CategoryItemDM(title = R.string.sports, image = R.drawable.sports, apiID = "sports")
            )
        }
    }
}
