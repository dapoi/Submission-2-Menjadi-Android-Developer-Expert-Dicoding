package com.dapoidev.catmov.core.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("poster_path")
    val coverMovie: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("release_date")
    val date: String,

    @SerializedName("vote_average")
    val vote_average: Double,

    @SerializedName("overview")
    val desc: String
)
