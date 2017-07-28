package com.mudio.movies.DataClasses.TMDBVideoData

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("id", "results")

data class VideosList(
    @JsonProperty("id")
    var id: Int?,

    @JsonProperty("results")
    var results: MutableList<VideoResult>?
)