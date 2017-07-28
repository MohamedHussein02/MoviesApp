package com.mudio.movies.DataRetrievers

class TmdbJsons private constructor(){
    var mostPopularJson = ""
    var topRatedJson = ""
    var detailedMovieJson = ""

    companion object {
        val instance: TmdbJsons by lazy { TmdbJsons() }
    }
}
