package com.mudio.movies.DataRetrievers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mudio.movies.DataClasses.MovieData.MoviesDataList
import com.mudio.movies.DataClasses.MovieData.MovieResult
import com.fasterxml.jackson.module.kotlin.readValue

class FragmentMovieData private constructor() {

    fun getMoviesData(index:Int):ArrayList<MovieResult>{

        val MOST_POPULAR_JSON = TmdbJsons.instance.mostPopularJson
        val TOP_RATED_JSON = TmdbJsons.instance.topRatedJson

        when(index){
            0->{ return parseData(MOST_POPULAR_JSON) }

            1->{ return parseData(TOP_RATED_JSON) }

            else->{ return parseData(MOST_POPULAR_JSON) }
        }
    }

    fun parseData(movieDataListJsonString: String): ArrayList<MovieResult> {

        if(movieDataListJsonString != OkHttpDataRetriever.FAILED && movieDataListJsonString != ""){
            val movieDataList = jacksonObjectMapper().readValue<MoviesDataList>(movieDataListJsonString).movieResults!!

            return movieDataList
        }
        return arrayListOf()
    }

    companion object{
        val instance:FragmentMovieData by lazy { FragmentMovieData() }
    }
}