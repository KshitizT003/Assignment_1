package com.example.assignment_1.part_1

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

// Creating an object to handle movie search from the OMDb API
object MovieSearch {

    // Function to search for movies using the OMDb API
    fun searchMovies(query: String, apiKey: String): List<Movie> {
        val urlString = "https://www.omdbapi.com/?apikey=$apiKey&s=$query"
        val url = URL(urlString)

        // Open a connection to the API
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        return try {

            val response = connection.inputStream.bufferedReader().readText()
            val json = JSONObject(response)


            val searchArray = json.optJSONArray("Search") ?: return emptyList()


            List(searchArray.length()) { i ->
                val item = searchArray.getJSONObject(i)

                // Returning a list of Movie data using values from JSON
                Movie(
                    Title = item.getString("Title"),
                    Year = item.getString("Year"),
                    imdbID = item.getString("imdbID"),
                    Type = item.getString("Type"),
                    Poster = item.getString("Poster")
                )
            }

        } catch (e: Exception) {
            println("Error fetching movies: ${e.message}")
            emptyList()
        } finally {
            connection.disconnect()
        }
    }
}