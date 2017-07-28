package com.mudio.movies.DataClasses.MovieData

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("vote_count",
        "id",
        "video",
        "vote_average",
        "title", "popularity",
        "poster_path",
        "original_language",
        "original_title",
        "genre_ids",
        "backdrop_path",
        "adult",
        "overview",
        "release_date")

data class MovieResult (

        @JsonProperty("vote_count")
        @get:JsonProperty("vote_count")
        @set:JsonProperty("vote_count")
        var voteCount: Int? = null,

        @JsonProperty("id")
        @get:JsonProperty("id")
        @set:JsonProperty("id")
        var id: Int? = null,

        @JsonProperty("video")
        @get:JsonProperty("video")
        @set:JsonProperty("video")
        var video: Boolean? = null,

        @JsonProperty("vote_average")
        @get:JsonProperty("vote_average")
        @set:JsonProperty("vote_average")
        var voteAverage: Double? = null,

        @JsonProperty("title")
        @get:JsonProperty("title")
        @set:JsonProperty("title")
        var title: String? = null,

        @JsonProperty("popularity")
        @get:JsonProperty("popularity")
        @set:JsonProperty("popularity")
        var popularity: Double? = null,

        @JsonProperty("poster_path")
        @get:JsonProperty("poster_path")
        @set:JsonProperty("poster_path")
        var posterPath: String? = null,

        @JsonProperty("original_language")
        @get:JsonProperty("original_language")
        @set:JsonProperty("original_language")
        var originalLanguage: String? = null,

        @JsonProperty("original_title")
        @get:JsonProperty("original_title")
        @set:JsonProperty("original_title")
        var originalTitle: String? = null,

        @JsonProperty("genre_ids")
        @get:JsonProperty("genre_ids")
        @set:JsonProperty("genre_ids")
        var genreIds: List<Int>? = null,

        @JsonProperty("backdrop_path")
        @get:JsonProperty("backdrop_path")
        @set:JsonProperty("backdrop_path")
        var backdropPath: String? = null,

        @JsonProperty("adult")
        @get:JsonProperty("adult")
        @set:JsonProperty("adult")
        var adult: Boolean? = null,

        @JsonProperty("overview")
        @get:JsonProperty("overview")
        @set:JsonProperty("overview")
        var overview: String? = null,

        @JsonProperty("release_date")
        @get:JsonProperty("release_date")
        @set:JsonProperty("release_date")
        var releaseDate: String? = null

)