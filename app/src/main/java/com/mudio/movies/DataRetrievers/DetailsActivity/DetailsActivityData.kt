package com.mudio.movies.DataRetrievers.DetailsActivity

import android.support.v7.app.ActionBar
import android.widget.ImageView
import android.widget.TextView
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar
import com.mudio.movies.DataClasses.DetailedMovieData.SingleMovieDataResult
import com.mudio.movies.DataRetrievers.PicassoImageRetriever
import com.mudio.movies.DataRetrievers.UrlCreator
import com.mudio.movies.R

class DetailsActivityData(private val data: SingleMovieDataResult){

    private val SIZE_FORMAT = "w600"
    private val PERPENDICULAR = R.drawable.placeholder

    fun initToolbarTitle(actionBar: ActionBar){ actionBar.title = data.title }

    fun initPosterImage(posterIV:ImageView, progressCircleProgressBar: CircleProgressBar) {

        val URL = UrlCreator().getPosterPathUrl(data.posterPath!!, SIZE_FORMAT)

        PicassoImageRetriever().loadImage(posterIV, URL, progressCircleProgressBar, PERPENDICULAR)
    }

    fun initTextViews(releaseDate:TextView, rating:TextView, originalLanguage:TextView){
        releaseDate.text = data.releaseDate
        rating.text = data.voteAverage.toString()
        originalLanguage.text = data.originalLanguage
    }

    fun initOverviewText(textView: TextView){ textView.text = data.overview }

}
