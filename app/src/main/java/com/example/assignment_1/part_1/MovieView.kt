package com.example.assignment_1.part_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_1.R

// Creating a custom RecyclerView adapter to show a list of movies
class MovieView(
    private val movieList: List<Movie>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieView.MovieViewHolder>() {

    // Defining a ViewHolder to hold the views for each row in the list
    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.titleText)
        val yearText: TextView = view.findViewById(R.id.yearText)
    }

    // Binding data to each row when it's being displayed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.new_layout, parent, false)
        return MovieViewHolder(view)
    }

    // Handling click events on each movie row
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.titleText.text = movie.Title
        holder.yearText.text = "Year: ${movie.Year}"
        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }
    }

    // Returning the total number of items in the list
    override fun getItemCount(): Int = movieList.size
}