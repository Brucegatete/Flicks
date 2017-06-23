package com.codepath.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by brucegatete on 6/22/17.
 */

public class Movie {
    //values from API
    private String title;
    private String overview;



    private String posterPath; //only the path


    //initialize JSON data
    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
    }

    public String getTitle() {
        return title;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
