package com.codepath.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by brucegatete on 6/22/17.
 */

public class Config {
    //base url for loading images
    String imageBaseUrl;
    // the poster size to use when fetching images;
    String posterSize;
    //the list of currently playing movies

    public Config (JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("images");
        //get the  base url
        imageBaseUrl = images.getString("secure_base_url");
        JSONArray posterSizeOptions = images.getJSONArray ("poster_sizes");
        //use the option at index 3 or w342 as a fallback
        posterSize = posterSizeOptions.optString(3, "w342");
    }
    //helper method for creating urls
    public String getImageUrl (String size, String path){
        return String.format("%s%s%s", imageBaseUrl, size, path);
    }


    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }
}
