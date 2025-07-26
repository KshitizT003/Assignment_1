package com.example.assignment_1.part_2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment_1.databinding.ActivityMovieDetailsBinding
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MovieDetailsActivity : AppCompatActivity() {

    // Declaring the binding variable to access views using ViewBinding
    private lateinit var binding: ActivityMovieDetailsBinding

    //Storing API key
    private val apiKey = "d08f84dc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imdbID = intent.getStringExtra("imdbID")

        if (imdbID == null) {
            Toast.makeText(this, "Movie ID not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Starting a background thread to fetch data from the API
        thread {
            val urlString = "https://www.omdbapi.com/?apikey=$apiKey&i=$imdbID"
            val connection = URL(urlString).openConnection() as HttpURLConnection

            try {
                connection.requestMethod = "GET"
                val response = connection.inputStream.bufferedReader().readText()
                val json = JSONObject(response)

                // Extracting required movie information
                val title = json.getString("Title")
                val year = json.getString("Year")
                val rating = json.getString("imdbRating")
                val plot = json.getString("Plot")
                val director = json.getString("Director")
                val posterUrl = json.getString("Poster")

                runOnUiThread {
                    // Setting the data to respective views
                    binding.movieTitle.text = title
                    binding.movieYear.text = "Year: $year"
                    binding.movieRating.text = "IMDB Rating: $rating"
                    binding.movieDirector.text = "Director: $director"
                    binding.moviePlot.text = plot
                    binding.backButton.setOnClickListener {
                        finish()
                    }
                }

            }
            // Handling any errors and printing the message
            catch (e: Exception) {

                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            } finally {
                // Closing the connection
                connection.disconnect()
            }
        }
    }
}