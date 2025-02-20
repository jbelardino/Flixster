package com.example.flixster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class MovieFragment : Fragment(), OnListFragmentInteractionListener {
    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)
        return view
    }
    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient()
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

        client[
            "https://api.themoviedb.org/3/movie/now_playing",
            params,
            object : JsonHttpResponseHandler() {

                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    // The wait for a response is over
                    progressBar.hide()

                    //TODO - Parse JSON into Models
                    val moviesRawJSON: String = json.jsonObject.get("results").toString()
                    val gson = Gson()
                    val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
                    val models: List<Movie> = gson.fromJson(moviesRawJSON, arrayMovieType)
                    recyclerView.adapter = MovieRecyclerViewAdapter(models, this@MovieFragment)

                    Log.d("API Request", "response successful")
                    //Log.d("API Request", models[0].posterPath.toString())
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    t: Throwable?
                ) {
                    // The wait for a response is over
                    //progressBar.hide()

                    t?.message?.let {
                        Log.e("API Request", errorResponse)
                    }
                }
            }]
    }

    /*
     * What happens when a particular book is clicked.
     */
    override fun onItemClick(item: Movie) {
        Toast.makeText(context, item.title, Toast.LENGTH_LONG).show()
    }

}
