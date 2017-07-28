package com.mudio.movies.DataRetrievers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mudio.movies.DataClasses.DetailedMovieData.SingleMovieDataResult

class DetailedMovieDataRetriever{

    companion object {
        val ID_EXTRA_NAME = "movieId"
        val MOVIE_INTENT_NAME = "detailedMovieData"

    }
    fun getDetailedMovieJson(movieId: String): String{
        val url = UrlCreator().getDetailedMovieJsonUrlById(movieId)
        return OkHttpDataRetriever().getResult(url)
    }

    fun parseDetailedMovieFromString(jsonAsString: String): SingleMovieDataResult{
        val data = jacksonObjectMapper().readValue<SingleMovieDataResult>(jsonAsString)
        return data
    }
}