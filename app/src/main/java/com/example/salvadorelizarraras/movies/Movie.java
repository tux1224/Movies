package com.example.salvadorelizarraras.movies;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Salvador Elizarraras on 11/01/2017.
 */

public class Movie implements Serializable {




    public String getPosterPath() {
            return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguge() {
        return originalLanguge;
    }

    public void setOriginalLanguge(String originalLanguge) {
        this.originalLanguge = originalLanguge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBakcDropPath() {
        return bakcDropPath;
    }

    public void setBakcDropPath(String bakcDropPath) {
        this.bakcDropPath = bakcDropPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public byte[] getImageBakc() {
        return imageBakc;
    }

    public void setImageBakc(byte[] imageBakc) {
        this.imageBakc = imageBakc;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    public Bitmap getImageBackBitmap() {
        return imageBackBitmap;
    }

    public void setImageBackBitmap(Bitmap imageBackBitmap) {
        this.imageBackBitmap = imageBackBitmap;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }


    //region fields
    private String posterPath;
    private boolean adult;
    private String overView;
    private String releaseDate;
    private int [] genre_ids;
    private int id;
    private String originalTitle;
    private String originalLanguge;
    private String title;
    private String bakcDropPath;
    private float popularity;
    private int voteCount;
    private boolean video;
    private float voteAverage;
    private transient Bitmap imageBitmap;
    private transient Bitmap imageBackBitmap;
    private byte[] image;
    private byte[] imageBakc;
    //endregion





}
