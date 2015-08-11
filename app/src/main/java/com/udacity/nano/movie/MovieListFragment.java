package com.udacity.nano.movie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListFragment extends Fragment {

    private final String LOG_TAG = MovieListFragment.class.getSimpleName();

    private ImageAdapter mSearchResultAdapter;
    public MovieListFragment() {
    }

    private void updateMovieList() {
        FetchMovieTask task = new FetchMovieTask(getActivity(), mSearchResultAdapter);
        task.execute(getString(R.string.api_key));
    }


    @Override
    public void onStart() {
        super.onStart();
        updateMovieList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSearchResultAdapter = new ImageAdapter(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_list_view);
        gridView.setAdapter(mSearchResultAdapter);
        return rootView;
    }
}
