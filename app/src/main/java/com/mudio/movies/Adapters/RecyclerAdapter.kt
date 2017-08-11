package com.mudio.movies.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.mudio.movies.DataClasses.MovieData.MovieResult
import com.mudio.movies.DataRetrievers.UrlCreator
import com.mudio.movies.R
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar
import com.mudio.movies.DataRetrievers.PicassoImageRetriever

class RecyclerAdapter(var resultsList: ArrayList<MovieResult>,
                      var con: Context,
                      private val listener: ListItemClickListener)
    : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val SIZE_FORMAT = "w600"
    private val PERPENDICULAR = R.drawable.placeholder

    interface ListItemClickListener { fun onListItemClick(movieId: Int, holder: ViewHolder, position: Int) }

    override fun getItemCount(): Int { return resultsList.size }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(con).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        initData(resultsList[position], holder)
        initEvents(holder)
    }

    private fun initData(data: MovieResult, holder: ViewHolder) {
        loadPosterImage(data, holder)
        loadEllipsizedMovieTitle(data, holder)
        loadEllipsizedReleaseData(data, holder)
    }

    private fun loadEllipsizedMovieTitle(data: MovieResult, holder: ViewHolder) {
        val title = data.title!!

        if (title.length > 14) {
            if(title[14] != " ".toCharArray()[0]){
            holder.movieNameTV.text = title.substring(0, 14) + "..."
            }else{
                holder.movieNameTV.text = title.substring(0, 13) + "..."
            }
        } else {
            holder.movieNameTV.text = title
        }
    }

    private fun loadEllipsizedReleaseData(data: MovieResult, holder: ViewHolder){
        holder.releaseDateTV.text = data.releaseDate!!.substring(0,4)
    }

    private fun loadPosterImage(data: MovieResult, holder: ViewHolder) {
        val URL = UrlCreator().getPosterPathUrl(data.posterPath!!, SIZE_FORMAT)

        PicassoImageRetriever()
                .loadImage( holder.posterIV, URL, holder.progCircle, PERPENDICULAR)
    }

    private fun initEvents(holder: ViewHolder) {
        holder.itemLayout.setOnClickListener(holder)
        holder.posterIV.setOnClickListener(holder)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view),View.OnClickListener{

        val posterIV = view.findViewById<ImageView>(R.id.posterIV)!!
        val movieNameTV = view.findViewById<TextView>(R.id.MovieNameTV)!!
        val releaseDateTV = view.findViewById<TextView>(R.id.ReleaseDateTV)!!
        val itemLayout = view.findViewById<RelativeLayout>(R.id.itemLayout)!!
        val progCircle = view.findViewById<CircleProgressBar>(R.id.progressCircle) !!
        val reloadIV = view.findViewById<ImageView>(R.id.reloadIV) !!

        override fun onClick(view: View) {
            listener.onListItemClick(resultsList[adapterPosition].id!!, this, adapterPosition)
        }
    }
}