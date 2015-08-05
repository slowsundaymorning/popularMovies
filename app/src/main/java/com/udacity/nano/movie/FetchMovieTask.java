package com.udacity.nano.movie;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class FetchMovieTask extends AsyncTask<String, Void, String[]> {

    private static final String LOG_TAG = FetchMovieTask.class.getSimpleName();
//    private static final String API_KEY = "a3e2ad4f22a7e0c381d454b7c432d6c3";
    private final String API_KEY;
    public FetchMovieTask(String apiKey) {
        API_KEY = apiKey;
    }

    @Override
    protected String[] doInBackground(String... params) {
//        if(params.length == 0)
//            return null;
        HttpURLConnection urlConnection = null;
        BufferedReader br = null;
        String movieListJsonStr = null;
        try {
            String MOVIE_LIST_BASE_URL = getBaseUrl();
        }catch (Exception e) {

        }

        return getMovieDataFromJson(movieListJsonStr);
    }

    private String getBaseUrl() {
        return "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+API_KEY;
    }

    private String[] getMovieDataFromJson(String movieListJsonStr)  {

        final String RESULT = "results";
        final String TITLE = "title";
        List<String> results = new ArrayList<>();
        try {
            JSONObject movieListJson = new JSONObject(movieListJsonStr);
            JSONArray movieList = movieListJson.getJSONArray(RESULT);
            for(int i = 0; i < movieList.length(); i++) {
                JSONObject movieItem = movieList.getJSONObject(i);
                results.add(movieItem.getString(TITLE));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return results.toArray(new String[]{});
    }
}
