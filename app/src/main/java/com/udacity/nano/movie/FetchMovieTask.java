package com.udacity.nano.movie;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchMovieTask extends AsyncTask<String, Void, List<MovieItem>> {

    private static final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    private ImageAdapter mMovieAdapter;
    private final Context mContext;

    public FetchMovieTask(Context context, ImageAdapter movieAdapter) {
        mContext = context;
        mMovieAdapter = movieAdapter;
    }

    @Override
    protected List<MovieItem> doInBackground(String... params) {
        if(params.length == 0)
            return null;
        HttpURLConnection urlConnection = null;
        BufferedReader br = null;
        String movieListJsonStr = null;
        try {
            String MOVIE_LIST_BASE_URL = getBaseUrl(params[0]);
        }catch (Exception e) {

        }
        return getMovieDataFromJson(movieListJsonStr);
    }

    private String getBaseUrl(String key) {
        return "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+key;
    }

    /*
       { " adult":false,
         "backdrop_path":"/sLbXneTErDvS3HIjqRWQJPiZ4Ci.jpg",
         "genre_ids":[10751,16,12,35],
         "id":211672,
         "original_language":"en",
         "original_title":"Minions",
         "overview":"e world.",
         "release_date":"2015-07-10",
         "poster_path":"/qARJ35IrJNFzFWQGcyWP4r1jyXE.jpg",
         "popularity":47.804014,
         "title":"Minions","video":false,"vote_average":7.0,"vote_count":850}
     */

    private List<MovieItem> getMovieDataFromJson(String movieListJsonStr)  {

        final String RESULT = "results";
        final String MOVIE_ID = "id";
        final String POSTER_PATH = "poster_path";
        List<MovieItem> results = new ArrayList<>();
        try {
            JSONObject movieListJson = new JSONObject(movieListJsonStr);
            JSONArray movieList = movieListJson.getJSONArray(RESULT);
            for(int i = 0; i < movieList.length(); i++) {
                JSONObject movieItem = movieList.getJSONObject(i);
                results.add(new MovieItem.Builder(
                                    movieItem.getLong(MOVIE_ID),
                                    movieItem.getString(POSTER_PATH))
                                .build());
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return results;
    }

    @Override
    protected void onPostExecute(List<MovieItem> result) {

        if(result != null && !result.isEmpty()){
            mMovieAdapter.clear();
            Collections.sort(result);
            for(MovieItem mi: result) {
                mMovieAdapter.add(mi.getmPosterUrl());
            }
        }
    }
}
