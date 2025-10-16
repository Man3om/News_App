package com.example.newsapp.api.model.sourceResponseApiModel

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Parcelize
@Entity(tableName = "sources")
data class SourcesItemDM(

    @field:SerializedName("country")
	val country: String? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("description")
	val description: String? = null,

    @field:SerializedName("language")
	val language: String? = null,

	@PrimaryKey(autoGenerate = false)
    @field:SerializedName("id")
	var id: String? = null,

    @field:SerializedName("category")
	val category: String? = null,

    @field:SerializedName("url")
	val url: String? = null
) : Parcelable