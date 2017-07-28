package com.mudio.movies.DataClasses.DetailedMovieData

import com.fasterxml.jackson.annotation.JsonProperty


data class ProductionCompany(
        @JsonProperty("name")
        var name: String? = null,

        @JsonProperty("id")
        var id: Int? = null)
