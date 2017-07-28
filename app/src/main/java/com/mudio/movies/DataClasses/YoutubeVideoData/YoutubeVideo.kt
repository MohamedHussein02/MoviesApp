package com.mudio.movies.DataClasses.YoutubeVideoData

import com.fasterxml.jackson.annotation.JsonProperty

data class YoutubeVideo(

    @JsonProperty("type")
    var type: String? = null,

    @JsonProperty("height")
    var height: Int? = null,

    @JsonProperty("thumbnail_width")
    var thumbnailWidth: Int? = null,

    @JsonProperty("thumbnail_url")
    var thumbnailUrl: String? = null,

    @JsonProperty("provider_name")
    var providerName: String? = null,

    @JsonProperty("title")
    var title: String? = null,

    @JsonProperty("author_name")
    var authorName: String? = null,

    @JsonProperty("version")
    var version: String? = null,

    @JsonProperty("author_url")
    var authorUrl: String? = null,

    @JsonProperty("thumbnail_height")
    var thumbnailHeight: Int? = null,

    @JsonProperty("width")
    var width: Int? = null,

    @JsonProperty("provider_url")
    var providerUrl: String? = null,

    @JsonProperty("html")
    var html: String? = null
)
