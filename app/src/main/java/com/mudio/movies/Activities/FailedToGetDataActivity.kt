package com.mudio.movies.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mudio.movies.DataRetrievers.OkHttpDataRetriever
import com.mudio.movies.R
import kotlinx.android.synthetic.main.failed_to_get_data_activity.*
import android.net.ConnectivityManager
import android.os.Handler
import android.provider.Settings
import android.support.design.widget.Snackbar
import com.arasthel.asyncjob.AsyncJob
import com.mudio.movies.DataRetrievers.TmdbJsons
import com.mudio.movies.DataRetrievers.UrlCreator
import com.mudio.movies.startActivity

class FailedToGetDataActivity : AppCompatActivity() {

    lateinit private var snackBar: Snackbar
    private val SPLASH_DURATION = 500
    private val okHttpRetriever = OkHttpDataRetriever()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.failed_to_get_data_activity)
        initSnackBar()
        initEvents()
    }

    private fun initEvents() {

        retryConnectionBt.setOnClickListener{
            toggleButtonAndProgCircle()

            if(snackBar.isShown) {snackBar.dismiss()}

            Handler().postDelayed( {launchActivityIfConnected()}, SPLASH_DURATION.toLong() )
        }
    }

    private fun toggleButtonAndProgCircle(){
        if(retryConnectionBt.visibility == View.VISIBLE){ retryConnectionBt.visibility = View.GONE }
        else{ retryConnectionBt.visibility = View.VISIBLE }

        if(progressCircleConnecting.visibility == View.GONE){ progressCircleConnecting.visibility = View.VISIBLE }
        else{ progressCircleConnecting.visibility = View.GONE }
    }

    private fun launchActivityIfConnected(){
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo

        val CONNECTED_TO_WIFI_OR_DATA = (networkInfo != null) && (networkInfo.isConnected)

        if(CONNECTED_TO_WIFI_OR_DATA){
            AsyncJob.doInBackground {
                val response = OkHttpDataRetriever().getResult(UrlCreator().getTmdbTestUrl())

                if (response != "failed") {
                    getTmdbData()
                    startActivity<MainActivity>()
                    finish()

                } else { toggleButtonAndProgCircle() }
            }

        }else{
            toggleButtonAndProgCircle()
            snackBar.show()
        }
    }

    private fun getTmdbData(){
        TmdbJsons.instance.mostPopularJson = okHttpRetriever.getResult(UrlCreator().getTmdbMostPopularUrl())
        TmdbJsons.instance.topRatedJson = okHttpRetriever.getResult(UrlCreator().getTmdbTopRatedUrl())
    }

    private fun initSnackBar(){
        snackBar = Snackbar
                .make(failedActivityLayout, "Waiting for network", Snackbar.LENGTH_INDEFINITE)
                .setAction("Open Settings", { startSettingsIntent() } )

        snackBar.view.setBackgroundColor(Color.parseColor("#006064"))
        snackBar.setActionTextColor(Color.parseColor("#00BCD4"))

        if(snackBar.isShownOrQueued){ snackBar.dismiss() }
    }
}

fun Activity.startSettingsIntent(){startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))}