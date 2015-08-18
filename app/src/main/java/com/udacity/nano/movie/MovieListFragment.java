package com.udacity.nano.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListFragment extends Fragment {

    private final String LOG_TAG = MovieListFragment.class.getSimpleName();

    private MovieListAdapter mSearchResultAdapter;
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
        mSearchResultAdapter = new MovieListAdapter(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_list_view);
        gridView.setAdapter(mSearchResultAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItem mi = (MovieItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra(MovieItem.EXTRA_TAG, mi);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
