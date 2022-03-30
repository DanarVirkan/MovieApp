package com.example.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GenreResponse (
    @field:SerializedName("name")
    val name : String
)