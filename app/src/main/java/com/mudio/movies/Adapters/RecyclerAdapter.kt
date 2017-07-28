package com.mudio.movies.Adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.arasthel.asyncjob.AsyncJob
import com.elmargomez.typer.Font
import com.elmargomez.typer.Typer
import com.mudio.movies.DataClasses.MovieData.MovieResult
import com.mudio.movies.DataRetrievers.UrlCreator
import com.mudio.movies.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RecyclerAdapter(var resultsArray: ArrayList<MovieResult>,
                      var con: Context,
                      listener: ListItemClickListener)
    : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var SIZE_FORMAT = "w600"
    private var mOnClickListener = listener

    interface ListItemClickListener { fun onListItemClick(movieId: Int, holder: ViewHolder, position: Int) }

    override fun getItemCount(): Int { return resultsArray.size }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(con).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = resultsArray[position]

        initData(data, holder)
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


        Picasso.with(holder.posterIV.context)
                .load(URL)
                .placeholder(R.drawable.placeholder)
                .into(holder.posterIV, object : Callback {
                    override fun onSuccess() {
                        holder.progCircle.visibility = View.GONE
                        briefDescriptionColors(holder)
                        setTvTypeface(holder, con)
                    }

                    override fun onError() {

                        holder.cancelPicassoRequests()

                        holder.progCircle.visibility = View.GONE
                        holder.reloadIV.visibility = View.VISIBLE
                    }
                })

    }

    private fun initEvents(holder: ViewHolder) {
        holder.itemLayout.setOnClickListener(holder)
        holder.posterIV.setOnClickListener(holder)
    }

    companion object {
        fun briefDescriptionColors(holder: ViewHolder){
            val bitmap = holder.posterIV.drawable as BitmapDrawable
            val palette = Palette.from(bitmap.bitmap).generate()

            holder.briefDescriptionLayout.
                    setBackgroundColor(palette.
                            getDarkVibrantColor(palette.
                                    getVibrantColor(Color.parseColor("#000a12"))))
        }

        fun setTvTypeface(holder: ViewHolder, con: Context){
            holder.movieNameTV.typeface = Typer.set(con).getFont(Font.ROBOTO_MEDIUM)
            holder.releaseDateTV.typeface = Typer.set(con).getFont(Font.ROBOTO_REGULAR)
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view),View.OnClickListener{

        val posterIV = view.findViewById<View>(R.id.posterIV) as ImageView
        val movieNameTV = view.findViewById<View>(R.id.MovieNameTV) as TextView
        val releaseDateTV = view.findViewById<View>(R.id.ReleaseDateTV) as TextView
        val briefDescriptionLayout = view.findViewById<View>(R.id.BriefDescriptionLayout) as RelativeLayout
        val itemLayout = view.findViewById<View>(R.id.itemLayout) as RelativeLayout
        val progCircle = view.findViewById<View>(R.id.progressCircle) as com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar
        val reloadIV = view.findViewById<View>(R.id.reloadIV) as ImageView

        override fun onClick(view: View) {
            mOnClickListener.onListItemClick(resultsArray[adapterPosition].id!!, this, adapterPosition)
        }

        fun cancelPicassoRequests(){
            Picasso.with(posterIV.context).cancelRequest(posterIV)

        }
    }
}