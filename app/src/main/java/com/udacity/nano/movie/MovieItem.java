package com.udacity.nano.movie;


import java.io.Serializable;

public class MovieItem implements Comparable<MovieItem>, Serializable {

    public static final String EXTRA_TAG ="movieItem";

    private static final long serialVersionUID = 1L;
    /*
    original title
    movie poster image thumbnail
    A plot synopsis (called overview in the api)
    user rating (called vote_average in the api)
     release date
     */

    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";

    private final long mId;
    private final String mOriginalTitle;
    private final String mPosterUrl;
    private final String mUserRating;
    private final String mPlot;
    private final String releaseDate;

    private final int mRank;

    private MovieItem(Builder b) {
        this.mId = b._id;
        this.mPosterUrl = b._posterUrl;
        this.mRank = b._rank;
        this.mOriginalTitle = b._originalTitle;
        this.mUserRating = b._userRating;
        this.mPlot = b._plot;
        this.releaseDate = b._releaseDate;
    }

    public static class Builder {
        private final long _id;
        private final String _posterUrl;
        private String _originalTitle;
        private String _userRating;
        private String _plot;
        private String _releaseDate;
        private int _rank = -1;
        public Builder(long id, String posterUrl) {
            this._id = id;
            this._posterUrl = posterUrl;
        }
        public Builder rank(int rank) {
            this._rank = rank;
            return this;
        }
        public Builder originalTitle(String title) {
            this._originalTitle = title;
            return this;
        }
        public Builder userRating(String rating) {
            this._userRating = rating;
            return this;
        }
        public Builder releaseDate(String date) {
            this._releaseDate = date;
            return this;
        }
        public Builder plot(String plot) {
            this._plot = plot;
            return this;
        }
        public MovieItem build() {
            return new MovieItem(this);
        }
    }

    public long getId() {
        return mId;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public int getRank() {
        return mRank;
    }
    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public String getPlot() {
        return mPlot;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int compareTo(MovieItem another) {
        return mRank - another.mRank;
    }
}
