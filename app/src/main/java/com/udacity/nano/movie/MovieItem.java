package com.udacity.nano.movie;


public class MovieItem implements Comparable<MovieItem> {
    private final long mId;
    private final String mPosterUrl;
    private final int mRank;

    private MovieItem(Builder b) {
        this.mId = b._id;
        this.mPosterUrl = b._posterUrl;
        this.mRank = b._rank;
    }

    public static class Builder {
        private final long _id;
        private final String _posterUrl;
        private int _rank = -1;
        public Builder(long id, String posterUrl) {
            this._id = id;
            this._posterUrl = posterUrl;
        }
        public Builder rank(int rank) {
            this._rank = rank;
            return this;
        }
        public MovieItem build() {
            return new MovieItem(this);
        }
    }

    public long getmId() {
        return mId;
    }

    public String getmPosterUrl() {
        return mPosterUrl;
    }

    public int getmRank() {
        return mRank;
    }

    @Override
    public int compareTo(MovieItem another) {
        return mRank - another.mRank;
    }
}
