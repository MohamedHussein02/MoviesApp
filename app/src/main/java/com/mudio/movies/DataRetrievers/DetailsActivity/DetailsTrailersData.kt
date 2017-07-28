package com.mudio.movies.DataRetrievers.DetailsActivity

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mudio.movies.DataClasses.TMDBVideoData.VideosList
import com.mudio.movies.DataClasses.TMDBVideoData.VideoResult
import com.mudio.movies.DataClasses.YoutubeVideoData.YoutubeVideo
import com.mudio.movies.DataRetrievers.OkHttpDataRetriever
import com.mudio.movies.DataRetrievers.UrlCreator

class DetailsTrailersData(private val movieId: String){

    private val urlCreator = UrlCreator()
    private val filteredVideoKeys = arrayListOf<String>()
    private var matchingKeys = arrayListOf<String>()
    private var videoKeys = mutableListOf<VideoResult>()

    private fun getVideoData() {
        val VIDEO_URL = urlCreator.getVideosJson(movieId)
        val VIDEO_JSON = OkHttpDataRetriever().getResult(VIDEO_URL)
        videoKeys = jacksonObjectMapper().readValue<VideosList>(VIDEO_JSON).results!!
    }

    private fun filterVideoKeys(){
        getVideoData()

        (0 .. videoKeys.size - 1)
                .filter { !videoKeys[it].name!!.toUpperCase() .contains("TEASER")
                        && videoKeys[it].type!!.toUpperCase() == "TRAILER"
                        && videoKeys[it].site!!.toUpperCase() == "YOUTUBE" }

                .forEach { filteredVideoKeys.add(videoKeys[it].key!!) }
    }

    private fun getMatchingYoutubeTrailersKeys(){
        val trailersList = arrayListOf<YoutubeVideo>()

        filterVideoKeys()

        matchingKeys = filteredVideoKeys

        if (filteredVideoKeys.size > 1) {
            matchingKeys = arrayListOf()
            (0..filteredVideoKeys.size - 1).forEach {
                val videoDataUrl = urlCreator.getYoutubeVideoData(filteredVideoKeys[it])
                val videoDataJson = OkHttpDataRetriever().getResult(videoDataUrl)
                trailersList.add(jacksonObjectMapper().readValue<YoutubeVideo>(videoDataJson))
            }

            val topOccurredAuthor = getTopOccurredAuthor(trailersList)

            (0..filteredVideoKeys.size - 1)
                    .filter { trailersList[it].authorName == topOccurredAuthor }
                    .forEach { matchingKeys.add(filteredVideoKeys[it]) }
        }
    }

    private fun getTopOccurredAuthor(videos: ArrayList<YoutubeVideo>): String{

        val listOfAuthors = arrayListOf("")
        val numberOfOccurrences = arrayListOf(0)

        for(i in 0 .. videos.size - 1) {
            var foundInArray = false
            for (j in 0..listOfAuthors.size - 1) {
                if (videos[i].authorName == listOfAuthors[j]) {
                    numberOfOccurrences[j] = ++numberOfOccurrences[j]
                    foundInArray = true
                    break
                }
            }
            if(!foundInArray){
                listOfAuthors.add(videos[i].authorName!!)
                numberOfOccurrences.add(1)
            }
        }

        var topOccurredIndex = 0
        (0 .. numberOfOccurrences.size - 1)
                .filter { numberOfOccurrences[it] > numberOfOccurrences[topOccurredIndex] }
                .forEach{ topOccurredIndex = it }

        return listOfAuthors[topOccurredIndex]
    }

    fun getYoutubeThumbnailUrls(): ArrayList<String>{
        val youtubeThumbnailArray = arrayListOf<String>()

        getMatchingYoutubeTrailersKeys()

        ( 0 .. matchingKeys.size - 1 )
                .forEach{ youtubeThumbnailArray.add( urlCreator.getYoutubeThumbnailUrl(matchingKeys[it]) ) }

        return youtubeThumbnailArray
    }

    fun getYoutubeVideoUrls(): ArrayList<String>{
        val youtubeVideoArray = arrayListOf<String>()

        (0 .. matchingKeys.size - 1)
                .forEach { youtubeVideoArray.add( urlCreator.getYoutubeWatchUrl(matchingKeys[it]) ) }

        return youtubeVideoArray
    }
}