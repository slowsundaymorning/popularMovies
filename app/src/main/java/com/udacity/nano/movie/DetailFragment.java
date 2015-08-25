package com.udacity.nano.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private String mMovieDetailString;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        MovieItem mi = (MovieItem) intent.getExtras().getSerializable(MovieItem.EXTRA_TAG);
        Log.e(LOG_TAG, mi.getOriginalTitle());

        TextView originalTitle = (TextView) rootView.findViewById(R.id.movie_detail_title);
        originalTitle.setText(mi.getOriginalTitle());

        TextView releaseDate = (TextView) rootView.findViewById(R.id.movie_detail_release_date);
        releaseDate.setText(mi.getReleaseDate());

        TextView plot = (TextView) rootView.findViewById(R.id.movie_detail_plot);
        plot.setText(mi.getPlot());

        ImageView poster = (ImageView) rootView.findViewById(R.id.movie_detail_poster);
        Picasso.with(rootView.getContext()).
                load(String.format(MovieListAdapter.BASE_POSTER_URL, mi.getPosterUrl())).
                resize(500,800).
                centerInside().
                into(poster);

        TextView rate = (TextView) rootView.findViewById(R.id.movie_detail_rate);
        rate.setText(mi.getUserRating());


        return rootView;
    }
}
