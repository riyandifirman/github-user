package com.riyandifirman.githubuser.response

import com.google.gson.annotations.SerializedName
import com.riyandifirman.githubuser.User

data class SearchResponse(
    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<User>
)
