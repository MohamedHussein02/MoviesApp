package com.mudio.movies.Activities

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mudio.movies.AnimationsAndTransitions.TransitionsDetailsActivity
import com.mudio.movies.DataRetrievers.DetailsActivity.DetailsActivityData
import com.mudio.movies.R
import kotlinx.android.synthetic.main.details_activity.*
import android.widget.ImageView
import com.mudio.movies.DataRetrievers.DetailsActivity.DetailsTrailersData
import com.mudio.movies.DataClasses.DetailedMovieData.SingleMovieDataResult
import com.mudio.movies.DataRetrievers.DetailedMovieDataRetriever
import com.mudio.movies.DataRetrievers.PicassoImageRetriever
import com.mudio.movies.DataRetrievers.TmdbJsons
import com.mudio.movies.parseJson
import com.mudio.movies.startYoutubeIntent

class DetailsActivity : AppCompatActivity() {

    private val transitionsTrailersInstance = TransitionsDetailsActivity()
    private val transitionsOverviewInstance = TransitionsDetailsActivity()
    private var movieId = 0
    private val SIDE_WAYS = R.drawable.placeholder_sideways
    lateinit private var detailsTrailersInstance : DetailsTrailersData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(Bundle())
        setContentView(R.layout.details_activity)
        setSupportActionBar(detailsToolbar)

        initComponents( getDataFromIntent() )
        YoutubeThumbnailsLoader(movieId).execute()
        initEvents()
    }

    private fun initComponents(data: SingleMovieDataResult) {
        val detailsActivityDataRetriever = DetailsActivityData(data)

        detailsActivityDataRetriever.initToolbarTitle(supportActionBar!!)
        detailsActivityDataRetriever.initTextViews(releaseDateTVDetails, ratingTVDetails, originalLangTV)
        detailsActivityDataRetriever.initOverviewText(overviewTV)
        detailsActivityDataRetriever.initPosterImage(posterIVDetails, progressCircleDetails)
    }

    private fun getDataFromIntent(): SingleMovieDataResult {
        movieId = intent.getIntExtra(DetailedMovieDataRetriever.ID_EXTRA_NAME, 0)

        return parseJson(TmdbJsons.instance.detailedMovieJson)
    }

    private fun initEvents() {
        trailersBtn.setOnClickListener{
            transitionsTrailersInstance.ExpandCollapseTransitioning(detailsLayout, trailersLayout)
        }

        overviewBtn.setOnClickListener{
            transitionsOverviewInstance.ExpandCollapseTransitioning(detailsLayout, overviewTV)
        }

    }

    private fun initTrailersOnClick(){
        val youtubeVideoURLs = detailsTrailersInstance.getYoutubeVideoUrls()

        if(trailer1IV.visibility == View.VISIBLE){
            trailer1IV.setOnClickListener{ startYoutubeIntent(youtubeVideoURLs[0]) }
        }

        if(trailer2IV.visibility == View.VISIBLE){
            trailer2IV.setOnClickListener{
                startYoutubeIntent(youtubeVideoURLs[1])
            }
        }

        if(trailerSingleIV.visibility == View.VISIBLE){
            trailerSingleIV.setOnClickListener{ startYoutubeIntent(youtubeVideoURLs[0]) }
        }
    }

    inner class YoutubeThumbnailsLoader(private val movieId: Int): AsyncTask<Unit,Unit,Unit>(){

        var thumbnailUrls = arrayListOf<String>()

        override fun doInBackground(vararg p0: Unit?) {
            thumbnailUrls = getDetailsTrailersThumbnailUrl(movieId)
        }

        override fun onPostExecute(result: Unit?) {
            loadThumbnailsInImageViews(thumbnailUrls)
            initTrailersOnClick()
        }

        private fun getDetailsTrailersThumbnailUrl(movieId :Int):ArrayList<String>{
            detailsTrailersInstance = DetailsTrailersData("$movieId")

            return detailsTrailersInstance.getYoutubeThumbnailUrls()
        }

        private fun trailerPicasso(url: String, imageView: ImageView){
            imageView.visibility = View.VISIBLE

            PicassoImageRetriever()
                    .loadImage(imageView, url, trailersProgressCircle, SIDE_WAYS)
        }

        private fun loadThumbnailsInImageViews( URLs: ArrayList<String> ){
            //checks number of valid urls before loading into ImageViews
            when( URLs.size ){
                0->{
                    trailersProgressCircle.visibility = View.GONE
                    noTrailersFoundTV.visibility = View.VISIBLE
                }
                1->{
                    trailerPicasso(URLs[0], trailerSingleIV)
                    trailerSingleTV.visibility = View.VISIBLE
                }
                else->{
                    trailerPicasso(URLs[0], trailer1IV)
                    trailerPicasso(URLs[1], trailer2IV)
                    trailer1TV.visibility = View.VISIBLE
                    trailer2TV.visibility = View.VISIBLE
                }
            }
        }
    }

}