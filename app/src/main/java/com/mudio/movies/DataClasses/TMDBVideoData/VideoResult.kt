package com.mudio.movies.DataClasses.TMDBVideoData

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("id",
        "iso_639_1",
        "iso_3166_1",
        "key",
        "name",
        "site",
        "size",
        "type")

data class VideoResult (

    @JsonProperty("id")
    var id: String?,

    @JsonProperty("iso_639_1")
    var iso6391: String?,

    @JsonProperty("iso_3166_1")
    var iso31661: String?,

    @JsonProperty("key")
    var key: String?,

    @JsonProperty("name")
    var name: String?,

    @JsonProperty("site")
    var site: String?,

    @JsonProperty("size")
    var size: Int?,

    @JsonProperty("type")
    var type: String?
)