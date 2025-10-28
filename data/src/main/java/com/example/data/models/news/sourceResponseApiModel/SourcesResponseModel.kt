package com.example.data.models.news.sourceResponseApiModel

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class SourcesResponseModel(

	@field:SerializedName("sources")
	val sources: List<SourcesItemModel>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable