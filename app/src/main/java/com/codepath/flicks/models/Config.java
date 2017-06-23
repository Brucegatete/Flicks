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
    //backdrop size for fetching images
    String  backdropSize;

    public String getBackdropSize() {
        return backdropSize;
    }

    public Config (JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("images");
        //get the  base url
        imageBaseUrl = images.getString("secure_base_url");
        JSONArray posterSizeOptions = images.getJSONArray ("poster_sizes");
        //use the option at index 3 or w342 as a fallback
        posterSize = posterSizeOptions.optString(3, "w342");
        //parse the backdropsize and use the optiosns of 1 and w780
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeOptions.optString(1, "w780");
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
