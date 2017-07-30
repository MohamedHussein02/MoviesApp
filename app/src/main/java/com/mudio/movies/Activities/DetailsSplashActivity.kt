package com.mudio.movies.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mudio.movies.DataClasses.DetailedMovieData.SingleMovieDataResult
import com.mudio.movies.DataRetrievers.DetailedMovieDataRetriever
import com.mudio.movies.DataRetrievers.TmdbJsons
import com.mudio.movies.DataRetrievers.UrlCreator
import com.mudio.movies.R
import com.mudio.movies.createIntent
import com.mudio.movies.parseJson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.details_splash_activity.*

class DetailsSplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_TIME = 3000
    lateinit private var detailedMovieData : SingleMovieDataResult
    private val POSTER_SIZE_FORMAT="w600"
    private var movieId = 0
    private val movieJson = TmdbJsons.instance.detailedMovieJson
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_splash_activity)
        setTheme(R.style.AppTheme)

        initData()
        loadBackgroundImage()
        launchDetailActivity()
    }


    private fun initData() {
        movieId = intent.getIntExtra(DetailedMovieDataRetriever.ID_EXTRA_NAME, 0)

        detailedMovieData = parseJson<SingleMovieDataResult>(movieJson)

        initTagLine()
    }

    private fun initTagLine() {
        val tagLineDecorated = capitalize(detailedMovieData.tagline!!)
        tagLineTV.text = tagLineDecorated
        tagLineShadow1TV.text = tagLineDecorated
        tagLineShadow2TV.text = tagLineDecorated
        tagLineShadow3TV.text = tagLineDecorated
        tagLineShadow4TV.text = tagLineDecorated
    }

    private fun loadBackgroundImage() {
        val URL = UrlCreator().getPosterPathUrl(detailedMovieData.posterPath!!, POSTER_SIZE_FORMAT)
        Picasso.with(applicationContext).load(URL).into(detailsSplashBackgroundIV)
    }

    private fun launchDetailActivity() {
        handler.postDelayed({

            val bundle = Bundle()
            bundle.putInt(DetailedMovieDataRetriever.ID_EXTRA_NAME, movieId)
            bundle.putString(DetailedMovieDataRetriever.MOVIE_INTENT_NAME, movieJson)

            startActivity( createIntent<DetailsActivity>().putExtras(bundle) )
            finish()

        }, SPLASH_DISPLAY_TIME.toLong())
    }

    private fun capitalize(givenString: String): String {
        val arr = givenString.split(("" +
                " ").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val sb = StringBuffer()

        for (i in arr.indices) {
            sb.append(Character.toUpperCase(arr[i][0]))
                    .append(arr[i].substring(1)).append(" ")
        }


        return checkFullStop(sb.toString().trim { it <= ' ' })
    }

    private fun checkFullStop(sentence: String): String{
        var decorated = sentence
        if(sentence[sentence.length-1] != ".".toCharArray()[0]){
            decorated = "$sentence."
        }
        return decorated
    }

    override fun onBackPressed() {
        handler.removeCallbacksAndMessages(null)
        super.onBackPressed()
    }
}