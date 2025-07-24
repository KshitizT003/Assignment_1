package com.example.assignment_1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment_1.databinding.ActivityMainBinding
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
                                // Open details screen when movie clicked
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
                Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
