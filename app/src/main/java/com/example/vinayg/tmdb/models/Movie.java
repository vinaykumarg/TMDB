package com.example.vinayg.tmdb.models;

/**
 * Created by vinay.g.
 */

public class Movie {
    private String title,ImageUrl,Release_date;

    public String getRelease_date() {
        return Release_date;
    }

    public void setRelease_date(String release_date) {
        Release_date = release_date;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
