package com.mudio.movies.DataRetrievers

import android.view.View
import android.widget.ImageView
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PicassoImageRetriever{

    fun loadImage(imageView: ImageView, URL: String, progCircle: CircleProgressBar,
                  placeHolderOrientation: Int){

        Picasso.with(imageView.context)
                .load(URL)
                .placeholder(placeHolderOrientation)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        progCircle.visibility = View.GONE
                    }

                    override fun onError() {
                        progCircle.visibility = View.GONE
                    }
                })
    }
}
