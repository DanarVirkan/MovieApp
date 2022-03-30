package com.example.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class Results(
    @field:SerializedName("results")
    val results: List<ItemResponse>
)