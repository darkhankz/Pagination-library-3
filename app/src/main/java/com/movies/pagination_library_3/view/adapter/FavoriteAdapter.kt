package com.movies.pagination_library_3.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.movies.pagination_library_3.R
import com.movies.pagination_library_3.data.MoviesDetailsData
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val mList: List<MoviesDetailsData>?, private val listener: OnItemClickListener?) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList?.get(position)

        Picasso.get().load("https://image.tmdb.org/t/p/w500/${itemsViewModel?.poster_path}").into(holder.imageView)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = itemsViewModel?.title

        holder.itemView.setOnClickListener {
            val movieId = mList?.get(position)?.id
            if (movieId != null) {
                listener?.onItemClick(movieId)
            }
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList!!.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.name)
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
    }

    interface OnItemClickListener {
        fun onItemClick(movieId: Int)
    }
}
