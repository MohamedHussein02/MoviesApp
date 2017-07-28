package com.mudio.movies.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mudio.movies.Activities.DetailsActivity
import com.mudio.movies.Activities.DetailsSplashActivity
import com.mudio.movies.Activities.FailedToGetDataActivity
import com.mudio.movies.DataRetrievers.FragmentMovieData
import com.mudio.movies.Adapters.RecyclerAdapter
import com.mudio.movies.DataClasses.DetailedMovieData.SingleMovieDataResult
import com.mudio.movies.DataClasses.MovieData.MovieResult
import com.mudio.movies.DataRetrievers.DetailedMovieDataRetriever
import com.mudio.movies.DataRetrievers.TmdbJsons
import com.mudio.movies.DataRetrievers.UrlCreator
import com.mudio.movies.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_fragment.view.*


class RecyclerFragment(private val position:Int) : Fragment(), RecyclerAdapter.ListItemClickListener {

    lateinit private var detailedMovieData: SingleMovieDataResult
    private var movieResults = arrayListOf<MovieResult>()
    private var SIZE_FORMAT = "w600"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val (rootView, mRecyclerView) = initRecycler(inflater, container)

        if(movieResults == arrayListOf<MovieResult>()){
            startActivity(Intent(context, FailedToGetDataActivity::class.java))
        }


        mRecyclerView.adapter = RecyclerAdapter(movieResults, context, this)

        initToolbar(rootView)

        return rootView
    }

    private fun initRecycler(inflater: LayoutInflater?, container: ViewGroup?): Pair<View, RecyclerView>{
        val rootView = inflater!!.inflate(R.layout.main_fragment, container, false)
        val mRecyclerView = rootView.findViewById<View>(R.id.RecyclerViewXML) as RecyclerView

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(context, 2)

        movieResults = FragmentMovieData.instance.getMoviesData(position)

        return Pair(rootView, mRecyclerView)
    }

    private fun initToolbar(view: View){
        when(position){
            0-> view.fragmentToolbar.title = "Most Popular"
            1-> view.fragmentToolbar.title = "Top Rated"
        }
    }

    override fun onListItemClick(movieId:Int, holder: RecyclerAdapter.ViewHolder, position: Int) {
        if(holder.reloadIV.visibility == View.VISIBLE){
            reloadPosterImage(holder, position)
            return
        }
        clickedMovieId = movieId
        launchDetailsActivity(movieId)
    }

    private fun reloadPosterImage(holder: RecyclerAdapter.ViewHolder, pos: Int){

        holder.reloadIV.visibility = View.GONE
        holder.progCircle.visibility = View.VISIBLE
        val url = UrlCreator().getPosterPathUrl(movieResults[pos].posterPath!!, SIZE_FORMAT)

        Picasso
                .with(holder.posterIV.context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .into(holder.posterIV, object: Callback{
                    override fun onSuccess() {
                        holder.progCircle.visibility = View.GONE
                        RecyclerAdapter.briefDescriptionColors(holder)
                        RecyclerAdapter.setTvTypeface(holder, context)
                    }

                    override fun onError() {
                        Picasso.with(holder.posterIV.context).cancelRequest(holder.posterIV)

                        holder.progCircle.visibility = View.GONE
                        holder.reloadIV.visibility = View.VISIBLE
                    }

                })
    }

    private fun launchDetailsActivity(movieId: Int) {

        val detailsActivityIntent = Intent(context, DetailsActivity::class.java)
        detailsActivityIntent.putExtra(DetailedMovieDataRetriever.ID_EXTRA_NAME, movieId)

        val detailsSplashActivityIntent = Intent(context, DetailsSplashActivity::class.java)
        detailsSplashActivityIntent.putExtra(DetailedMovieDataRetriever.ID_EXTRA_NAME, movieId)

        TmdbJsons.instance.detailedMovieJson = getDetailedMovieDetailed(movieId)

        if (checkTagLinePresence()) {
            startActivity(detailsSplashActivityIntent)
        } else {
            startActivity(detailsActivityIntent)
        }

    }

    private fun getDetailedMovieDetailed(movieId: Int): String {

        val detailedMovieJson = DetailedMovieDataRetriever().getDetailedMovieJson("$movieId")
        TmdbJsons.instance.detailedMovieJson = detailedMovieJson

        detailedMovieData = DetailedMovieDataRetriever().parseDetailedMovieFromString(detailedMovieJson)
        return detailedMovieJson

    }

    private fun checkTagLinePresence(): Boolean {
        if(detailedMovieData.tagline != null && detailedMovieData.tagline != ""){
            return true
        }
        return false
    }

    companion object {
        fun newInstance(position: Int): RecyclerFragment {
            val fragment = RecyclerFragment(position)
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        var clickedMovieId = 0
    }
}