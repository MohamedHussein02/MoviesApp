package com.mudio.movies.DataRetrievers

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar
import com.mudio.movies.R
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
                        Log.e("Picasso", "failed to load image")
                    }
                })
    }

    fun loadImage(imageView: ImageView, URL: String, progCircle: CircleProgressBar,
                  placeHolderOrientation: Int, function1:() -> Unit , function2: () -> Unit, function3: () -> Unit){

        Picasso.with(imageView.context)
                .load(URL)
                .placeholder(placeHolderOrientation)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        progCircle.visibility = View.GONE
                        function1
                        function2

                    }

                    override fun onError() {
                        Log.e("Picasso", "failed to load image")
                        function3
                    }
                })
    }
}
