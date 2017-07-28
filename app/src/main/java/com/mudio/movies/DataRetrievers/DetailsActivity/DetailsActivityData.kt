package com.mudio.movies.DataRetrievers.DetailsActivity

import android.support.v7.app.ActionBar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar
import com.mudio.movies.DataClasses.DetailedMovieData.SingleMovieDataResult
import com.mudio.movies.DataRetrievers.UrlCreator
import com.mudio.movies.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class DetailsActivityData(private val data: SingleMovieDataResult){

    private val SIZE_FORMAT = "w600"

    fun initToolbarTitle(actionBar: ActionBar){
        actionBar.title=data.title
    }

    fun initPosterImage(poster:ImageView, progressCircleProgressBar: CircleProgressBar) {
        val url = UrlCreator().getPosterPathUrl(data.posterPath!!, SIZE_FORMAT)

        Picasso.with(poster.context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .into(poster, object : Callback {
                    override fun onSuccess() {
                        progressCircleProgressBar.visibility = View.GONE
                    }

                    override fun onError() {
                    }
                })
    }

    fun initTextViews(releaseDate:TextView, rating:TextView, originalLanguage:TextView){
        releaseDate.text=data.releaseDate
        rating.text=data.voteAverage.toString()
        originalLanguage.text=data.originalLanguage
    }

    fun initOverviewText(textView: TextView){
        textView.text = data.overview
    }
}
