package com.udacity.nano.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
//        if(intent != null && intent.hasExtra(MovieItem.EXTRA_TAG)) {
            MovieItem mi = (MovieItem) intent.getExtras().getSerializable(MovieItem.EXTRA_TAG);
            Log.e(LOG_TAG, mi.getOriginalTitle());
            TextView originalTitle = (TextView) rootView.findViewById(R.id.movie_detail_title);
            TextView plot = (TextView) rootView.findViewById(R.id.movie_detail_plot);
            originalTitle.setText(mi.getOriginalTitle());
            plot.setText(mi.getPlot());
//        }
        return rootView;
    }
}
