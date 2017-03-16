package com.example.vinayg.tmdb.models;

import java.io.Serializable;

/**
 * Created by vinay.g.
 */

public class Movie implements Serializable {
    private String title,ImageUrl,Release_date,BackgroundImage,AverageRating,Overview,category;
    private long movieId;
    private Boolean isSaved = false;
    private int isFavorite;
    private int id;

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



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

    public String getBackgroundImage() {
        return BackgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        BackgroundImage = backgroundImage;
    }

    public String getAverageRating() {
        return AverageRating;
    }

    public void setAverageRating(String averageRating) {
        AverageRating = averageRating;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public Boolean isSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
