package com.mudio.movies.DataClasses.DetailedMovieData

import com.fasterxml.jackson.annotation.JsonProperty


data class BelongsToCollection (
    @JsonProperty("id")
    var id: Int?,

    @JsonProperty("name")
    var name: String?,

    @JsonProperty("poster_path")
    var posterPath: String?,

    @JsonProperty("backdrop_path")
    var backdropPath: String? = null
)


