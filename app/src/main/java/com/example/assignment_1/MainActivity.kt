package com.example.assignment_1

import com.example.assignment_1.part_1.Movie
import com.example.assignment_1.part_1.MovieSearch
import com.example.assignment_1.part_1.MovieView
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment_1.databinding.ActivityMainBinding
import com.example.assignment_1.part_2.MovieDetailsActivity
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    // Declaring the binding variable to access views using ViewBinding
    private lateinit var binding: ActivityMainBinding

    // Storing the OMDb API key
    private val apiKey = "d08f84dc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.searchButton.setOnClickListener {
            val query = binding.searchInput.text.toString().trim()

            // Checking if the input is not empty
            if (query.isNotEmpty()) {
                thread {
                    val movieList: List<Movie> = MovieSearch.searchMovies(query, apiKey)

                    runOnUiThread {
                        if (movieList.isNotEmpty()) {
                            val adapter = MovieView(movieList) { movie ->
                                // Opens details screen when movie clicked
                                val intent = Intent(this, MovieDetailsActivity::class.java)
                                intent.putExtra("imdbID", movie.imdbID)
                                startActivity(intent)
                            }
                            binding.recyclerView.adapter = adapter
                        } else {
                            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                // Showing a message if the search box is empty
                Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
