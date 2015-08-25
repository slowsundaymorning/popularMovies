package com.udacity.nano.movie;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class FetchMovieTask extends AsyncTask<String, Void, Collection<MovieItem>> {

    private static final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    private MovieListAdapter mMovieAdapter;
    private final Context mContext;

    public FetchMovieTask(Context context, MovieListAdapter movieAdapter) {
        mContext = context;
        mMovieAdapter = movieAdapter;
    }

    @Override
    protected Collection<MovieItem> doInBackground(String... params) {
        if(params.length == 0)
            return null;
        HttpURLConnection urlConnection = null;
        BufferedReader br = null;
        String movieListJsonStr = null;
        try {
            Log.d(LOG_TAG, "app_key "+params[0]);
            String MOVIE_LIST_BASE_URL = getBaseUrl(params[0], params[1]);
            Log.d(LOG_TAG, "MOVIE_LIST_BASE_URLy "+MOVIE_LIST_BASE_URL);
            URL url = new URL(MOVIE_LIST_BASE_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null)
                buffer.append(line + "\n");
            if (buffer.length() == 0) {
                return null;
            }
            movieListJsonStr = buffer.toString();
            Log.d(LOG_TAG, "movieListJsonStr "+movieListJsonStr);
        }catch (Exception e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
            if(br != null)
                try {
                    br.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream",e);
                }
        }
        return getMovieDataFromJson(movieListJsonStr);
    }

    private String getBaseUrl(String key, String orderby) {
        return String.format("http://api.themoviedb.org/3/discover/movie?sort_by=%s.desc&api_key=%s",orderby,key);
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
         "title":"Minions","video":false,
         "vote_average":7.0,
         "vote_count":850}
     */

    private Collection<MovieItem> getMovieDataFromJson(String movieListJsonStr)  {

        /*
        original title
    movie poster image thumbnail
    A plot synopsis (called overview in the api)
    user rating (called vote_average in the api)
     release date
         */
        final String RESULT = "results";
        final String MOVIE_ID = "id";
        final String POSTER_PATH = "poster_path";
        final String RELEASE_DATE = "release_date";
        final String USER_RATING = "vote_average";
        final String ORIGINAL_TITLE = "original_title";
        final String PLOT = "overview";
        Collection<MovieItem> results = new ArrayList<>();
        try {
            JSONObject movieListJson = new JSONObject(movieListJsonStr);
            JSONArray movieList = movieListJson.getJSONArray(RESULT);
            for(int i = 0; i < movieList.length(); i++) {
                JSONObject movieItem = movieList.getJSONObject(i);
                results.add(new MovieItem.Builder(
                        movieItem.getLong(MOVIE_ID),
                        movieItem.getString(POSTER_PATH))
                        .originalTitle(movieItem.getString(ORIGINAL_TITLE))
                        .userRating(movieItem.getString(USER_RATING))
                        .plot(movieItem.getString(PLOT))
                        .releaseDate(movieItem.getString(RELEASE_DATE))
                        .build());
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return results;
    }

    @Override
    protected void onPostExecute(Collection<MovieItem> result) {

        if(result != null && !result.isEmpty()){
            mMovieAdapter.clear();
            mMovieAdapter.addAll(result);
        }
    }
}
