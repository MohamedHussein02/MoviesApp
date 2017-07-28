package com.mudio.movies.DataClasses.DetailedMovieData

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonProperty

@JsonPropertyOrder("adult",
        "backdrop_path",
        "belongs_to_collection",
        "budget",
        "genres",
        "homepage",
        "id",
        "imdb_id",
        "original_language",
        "original_title",
        "overview",
        "popularity",
        "poster_path",
        "production_companies",
        "production_countries",
        "release_date",
        "revenue",
        "runtime",
        "spoken_language",
        "status",
        "tagline",
        "title",
        "video",
        "vote_average",
        "vote_count"
        )

data class SingleMovieDataResult(
        @JsonProperty("adult")
        var adult: Boolean? = null,

        @JsonProperty("backdrop_path")
        var backdropPath: String? = null,

        @JsonProperty("belongs_to_collection")
        var belongsToCollection: BelongsToCollection? = null,

        @JsonProperty("budget")
        var budget: Int? = null,

        @JsonProperty("genres")
        var genres: List<Genre>? = null,

        @JsonProperty("homepage")
        var homepage: String? = null,

        @JsonProperty("id")
        var id: Int? = null,

        @JsonProperty("imdb_id")
        var imdbId: String? = null,

        @JsonProperty("original_language")
        var originalLanguage: String? = null,

        @JsonProperty("original_title")
        var originalTitle: String? = null,

        @JsonProperty("overview")
        var overview: String? = null,

        @JsonProperty("popularity")
        var popularity: Double? = null,

        @JsonProperty("poster_path")
        var posterPath: String?,

        @JsonProperty("production_companies")
        var productionCompanies: List<ProductionCompany>?,

        @JsonProperty("production_countries")
        var productionCountries: List<ProductionCountry>?,

        @JsonProperty("release_date")
        var releaseDate: String?,

        @JsonProperty("revenue")
        var revenue: Int?,

        @JsonProperty("runtime")
        var runtime: Int?,

        @JsonProperty("spoken_languages")
        var spokenLanguages: List<SpokenLanguage>? = null,

        @JsonProperty("status")
        var status: String? = null,

        @JsonProperty("tagline")
        var tagline: String? = null,

        @JsonProperty("title")
        var title: String? = null,

        @JsonProperty("video")
        var video: Boolean? = null,

        @JsonProperty("vote_average")
        var voteAverage: Double? = null,

        @JsonProperty("vote_count")
        var voteCount: Int? = null )