package com.example.flixster

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // Create and set up an AsyncHTTPClient()
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

        client[
            "https://api.themoviedb.org/3/movie/now_playing",
            params,
            object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                // The wait for a response is over
//                progressBar.hide()

                //TODO - Parse JSON into Models
                val moviesRawJSON : String = json.jsonObject.get("results").toString()
                val gson = Gson()
                val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
                val models : List<Movie> = gson.fromJson(moviesRawJSON, arrayMovieType)
                //recyclerView.adapter = BestSellerBooksRecyclerViewAdapter(models, this@BestSellerBooksFragment)





//                val resultsJSON : JSONObject = json.jsonObject.get("results") as JSONObject
//                val booksRawJSON : String = resultsJSON.get("books").toString()
//                val gson = Gson()
//                val arrayBookType = object : TypeToken<List<BestSellerBook>>() {}.type

//                val models : List<BestSellerBook> = gson.fromJson(booksRawJSON, arrayBookType)
//                recyclerView.adapter = BestSellerBooksRecyclerViewAdapter(models, this@BestSellerBooksFragment)

                // Look for this in Logcat:
                Log.d("API Request", "response successful")
                Log.d("API Request", models[0].title.toString())
                //Log.d("API Request", result[0])

            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                // The wait for a response is over
                //progressBar.hide()

                t?.message?.let {
                    Log.e("API Request", errorResponse)
                }
            }
        }]





    }
}