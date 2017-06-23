package com.codepath.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.codepath.flicks.models.Config;
import com.codepath.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class MovieListActivity extends AppCompatActivity {
    //Constants
    //the base URL for the API
    public final static String API_BASE_URL = "http://api.themoviedb.org/3";
    //the parameter name of the API key
    public final static String API_KEY_PARAM = "api_key";

    //tag for all logging
    public final static String TAG = "MovieListActivity";

    //instance fields
    AsyncHttpClient client;

    //base url for loading images
    String imageBaseUrl;
    // the poster size to use when fetching images;
    String posterSize;
    //the list of currently playing movies
    ArrayList<Movie> movies;
    // the recycler view
    RecyclerView rvMovies;
    // adapter wired to the recycler view
    MovieAdapter adapter;
    // image config
    Config config;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        //initialize the client
        client = new AsyncHttpClient();
        //initiate the list of movies
        movies = new ArrayList<>();
        // initialize the adapter
        adapter = new MovieAdapter(movies);
        //resolve the recycler view and connect the layout manager and adapter
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);


        //get the configuration on app creation
        getConfiguration();
        //get the now playing movie list
        getNowPlaying();

    }
    //get the list of configuration form the API
    private void getNowPlaying () {
        //create a url
        String url = API_BASE_URL + "/movie/now_playing";
        //set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        //execute a GET request that expects a JSON response
        client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //load the results into a movie list
                        try {
                            JSONArray results = response.getJSONArray("results");
                            //iterate through the result set and create Movie objects
                            for (int i = 0; i < results.length(); i++) {
                                Movie movie = new Movie(results.getJSONObject(i));
                                movies.add(movie);
                                //notify adapter that a row was added
                                adapter.notifyItemInserted(movies.size() - 1);
                            }

                            log.i(TAG, String.format("loaded %s movies", results.length()));
                        } catch (JSONException e) {
                            logError("failed to parse now playinfg movies", e, true);
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        logError("failed to get the data from the now playing playlist", throwable, true);
                    }
        });
    }

    //get the configuration from the API
    private void getConfiguration(){
        //create a url
        String url = API_BASE_URL + "/configuration";
        //set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        //execute a GET request that expects a JSON response
        client.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed getting configuration", throwable, true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               //get the image base url
                try{
                    config = new Config(response);
                    //get the poster size
                    log.i(TAG, String.format("loaded configuration with imageBaseUrl %s and posterSize %s", config.getImageBaseUrl(), config.getPosterSize()));
                    // pass config to adapter
                    adapter.setConfig(config);
                    //get the now playing movie list
                    getNowPlaying();
                } catch (JSONException e) {
                    logError("Failed parsing the configuration", e, true );
                }

            }
        });

        }


    //handle errors, log and alert user
    private void logError(String message, Throwable error, boolean alertUser) {
        //always get the log error
        Log.e(TAG, message, error);
        //alert users to avoid silent errors
        if (alertUser){
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


}
}

