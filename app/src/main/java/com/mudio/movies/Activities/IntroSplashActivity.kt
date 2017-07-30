package com.mudio.movies.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.arasthel.asyncjob.AsyncJob
import com.mudio.movies.ActivityCreated
import com.mudio.movies.DataRetrievers.OkHttpDataRetriever
import com.mudio.movies.DataRetrievers.TmdbJsons
import com.mudio.movies.DataRetrievers.UrlCreator

import com.mudio.movies.R
import com.mudio.movies.startActivity

class IntroSplashActivity : AppCompatActivity() {

    private val SPLASH_DURATION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_splash_activity)
        setTheme(R.style.AppTheme)

        Handler().postDelayed({
            AsyncJob.doInBackground {

                if (!(ActivityCreated.instance.introSplashActivityCreated)) {

                    ActivityCreated.instance.introSplashActivityCreated = true
                    TmdbJsons.instance.mostPopularJson = OkHttpDataRetriever().getResult(UrlCreator().getTmdbMostPopularUrl())
                    TmdbJsons.instance.topRatedJson = OkHttpDataRetriever().getResult(UrlCreator().getTmdbTopRatedUrl())

                    startActivity<MainActivity>()
                    finish()
                } else {
                    startActivity<MainActivity>()
                    finish()
                }
            }
        }, SPLASH_DURATION.toLong() )
    }
}