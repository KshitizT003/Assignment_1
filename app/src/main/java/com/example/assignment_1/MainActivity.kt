package com.example.assignment_1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment_1.adapter.MovieView
import com.example.assignment_1.databinding.ActivityMainBinding
import com.example.assignment_1.Movie
import com.example.assignment_1.MovieSearch
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val apiKey = "d08f84dc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.searchButton.setOnClickListener {
            val query = binding.searchInput.text.toString().trim()

            if (query.isNotEmpty()) {
                thread {
                    val movieList: List<Movie> = MovieSearch.searchMovies(query, apiKey)

                    runOnUiThread {
                        if (movieList.isNotEmpty()) {
                            val adapter = MovieView(movieList) { movie ->
                                Toast.makeText(this, "Clicked: ${movie.Title}", Toast.LENGTH_SHORT).show()
                            }
                            binding.recyclerView.adapter = adapter
                        } else {
                            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
            }
        }
    }
}