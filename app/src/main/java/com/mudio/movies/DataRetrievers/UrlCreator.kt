package com.mudio.movies.DataRetrievers

class UrlCreator{
    private val TMDB_API_KEY = "5c0db14b896ed284efd834f986166bee"

    //TMDB URLs
    fun getTmdbMostPopularUrl(): String{
        val MOST_POPULAR="https://api.themoviedb.org/3/movie/popular?api_key=$TMDB_API_KEY"
        return MOST_POPULAR
    }

    fun getTmdbTopRatedUrl(): String {
        val TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=$TMDB_API_KEY"
        return TOP_RATED
    }

    fun getDetailedMovieJsonUrlById(id: String): String{
        val movieJsonUrl="http://api.themoviedb.org/3/movie/$id?api_key=$TMDB_API_KEY"
        return movieJsonUrl
    }

    fun getVideosJson(id: String): String{
        val movieJsonUrl="http://api.themoviedb.org/3/movie/$id/videos?api_key=$TMDB_API_KEY"
        return movieJsonUrl
    }

    fun getTmdbTestUrl(): String{
        return "http://api.themoviedb.org/3/?api_key=$TMDB_API_KEY"
    }
    //-------------------------------------------------------------

    //Youtube URLs

    fun getYoutubeWatchUrl(videoKey: String): String{
        return "https://www.youtube.com/watch?v=$videoKey"
    }

    fun getYoutubeVideoData(videoKey: String): String{
        return "https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=$videoKey"
    }

    //-------------------------------------------------------------

    //Images URLs

    //TMDB Poster URL
    fun getPosterPathUrl(posterPath: String, sizeFormat: String): String{
        return "http://image.tmdb.org/t/p/$sizeFormat$posterPath"
    }

    //Youtube Thumbnail URL
    fun getYoutubeThumbnailUrl(videoKey: String): String{
        return "https://img.youtube.com/vi/$videoKey/hqdefault.jpg"
    }
}