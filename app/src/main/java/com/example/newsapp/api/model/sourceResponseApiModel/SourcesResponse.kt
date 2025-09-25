package com.example.newsapp.api.model.sourceResponseApiModel

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class SourcesResponse(

	@field:SerializedName("sources")
	val sources: List<SourcesItem>? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable