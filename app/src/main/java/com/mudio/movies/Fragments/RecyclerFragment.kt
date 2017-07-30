package com.mudio.movies.Fragments

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mudio.movies.*
import com.mudio.movies.Activities.DetailsActivity
import com.mudio.movies.Activities.DetailsSplashActivity
import com.mudio.movies.Activities.FailedToGetDataActivity
import com.mudio.movies.Adapters.RecyclerAdapter
import com.mudio.movies.DataClasses.DetailedMovieData.SingleMovieDataResult
import com.mudio.movies.DataClasses.MovieData.MovieResult
import com.mudio.movies.DataRetrievers.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class RecyclerFragment(private val position:Int) : Fragment(), RecyclerAdapter.ListItemClickListener {

    lateinit private var detailedMovieData: SingleMovieDataResult
    lateinit private var movieResults : ArrayList<MovieResult>
    private var SIZE_FORMAT = "w600"
    private val ID_EXTRA_NAME = "movieId"
    private val PERPENDICULAR = R.drawable.placeholder

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val (rootView, mRecyclerView) = initRecycler(inflater, container)

        if(movieResults == arrayListOf<MovieResult>()){

            if(ActivityCreated.instance.introSplashActivityCreated) {
                activity.startActivity<FailedToGetDataActivity>()
                ActivityCreated.instance.introSplashActivityCreated = true
                activity.finish()
            }
            else{ activity.finish() }
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
        launchDetailsActivity(movieId)
    }

    private fun reloadPosterImage(holder: RecyclerAdapter.ViewHolder, pos: Int){
        val url = UrlCreator().getPosterPathUrl(movieResults[pos].posterPath!!, SIZE_FORMAT)

        toggleProgCircleAndReloadIV(holder)

        PicassoImageRetriever()
                .loadImage( holder.posterIV, url, holder.progCircle, PERPENDICULAR
                        ,{ RecyclerAdapter.briefDescriptionColors(holder)}

                        ,{RecyclerAdapter.setTvTypeface(holder, context)}

                        ,{
                    holder.cancelPicassoRequests()
                    holder.progCircle.visibility = View.GONE
                    holder.reloadIV.visibility = View.VISIBLE
                })
    }

    private fun toggleProgCircleAndReloadIV(holder: RecyclerAdapter.ViewHolder) {
        holder.reloadIV.visibility = View.GONE
        holder.progCircle.visibility = View.VISIBLE
    }

    private fun launchDetailsActivity(movieId: Int) {
        val detailsActivityIntent = prepareIntent<DetailsActivity>(movieId)
        val detailsSplashActivityIntent = prepareIntent<DetailsSplashActivity>(movieId)

        getDetailedMovieDetailed(movieId)

        if (isTagLineBlank()) { startActivity(detailsActivityIntent) }

        else { startActivity(detailsSplashActivityIntent) }
    }

    inline private fun<reified T: Activity> prepareIntent(movieId: Int)
            = activity.createIntent<T>().putExtra(ID_EXTRA_NAME, movieId)

    private fun getDetailedMovieDetailed(movieId: Int){
        TmdbJsons.instance.detailedMovieJson = DetailedMovieDataRetriever().getDetailedMovieJson("$movieId")

        detailedMovieData = parseJson<SingleMovieDataResult>(TmdbJsons.instance.detailedMovieJson)
    }

    private fun isTagLineBlank() = detailedMovieData.tagline.isNullOrBlank()

    companion object {
        fun newInstance(position: Int): RecyclerFragment {
            val fragment = RecyclerFragment(position)
            fragment.arguments = Bundle()
            return fragment
        }
    }
}