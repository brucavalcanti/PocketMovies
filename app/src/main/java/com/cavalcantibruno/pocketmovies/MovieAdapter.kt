package com.cavalcantibruno.pocketmovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cavalcantibruno.pocketmovies.api.model.SimpleMovie
import com.cavalcantibruno.pocketmovies.databinding.MovieItemBinding
import com.squareup.picasso.Picasso

class MovieAdapter(private val movieList:List<SimpleMovie>)
    :RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemBinding: MovieItemBinding)
        :RecyclerView.ViewHolder(itemBinding.root)
    {
        private val binding:MovieItemBinding

        init{
            binding = itemBinding
        }

        fun binding(movieItem:SimpleMovie){
            Picasso.get().load(movieItem.Poster).resize(300,400).into(binding.posterImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieItemBinding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(movieItemBinding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
       val movie = movieList[position]
       holder.binding(movie)

    }

}