package com.mudio.movies.DataClasses.MovieData

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("page",
        "total_results",
        "total_pages",
        "results")

data class MoviesDataList (

        @JsonProperty("page")
        @get:JsonProperty("page")
        @set:JsonProperty("page")
        var page: Int? = null,

        @JsonProperty("total_results")
        @get:JsonProperty("total_results")
        @set:JsonProperty("total_results")
        var totalResults: Int? = null,

        @JsonProperty("total_pages")
        @get:JsonProperty("total_pages")
        @set:JsonProperty("total_pages")
        var totalPages: Int? = null,

        @JsonProperty("results")
        @get:JsonProperty("results")
        @set:JsonProperty("results")
        var movieResults: ArrayList<MovieResult>? = null

)