package com.reversecoder.kml.model;

import com.google.gson.Gson;

/**
 * Created by alam on 6/15/16.
 */
public class Movies {

    private String id = "";
    private String categoryId = "";
    private String movieName = "";
    private String movieType = "";
    private String releaseDate = "";
    private String imdbLink = "";
    private String imdbRating = "";
    private String plotSummary = "";
    private String synopsis = "";
    private String posterUrl = "";
    private String duration = "";
    private String director = "";
    private String writer = "";
    private String producer = "";
    private String editor = "";
    private String trailerUrl = "";
    private String stars = "";
    private String downloadLink = "";
    private boolean isLiked=false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImdbLink() {
        return imdbLink;
    }

    public void setImdbLink(String imdbLink) {
        this.imdbLink = imdbLink;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getPlotSummary() {
        return plotSummary;
    }

    public void setPlotSummary(String plotSummary) {
        this.plotSummary = plotSummary;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public static Movies getStringObject(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Movies.class);
    }

    public String toStringObject() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "Movies{" +
                "id='" + id + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", movieName='" + movieName + '\'' +
                ", movieType='" + movieType + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", imdbLink='" + imdbLink + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                ", plotSummary='" + plotSummary + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", duration='" + duration + '\'' +
                ", director='" + director + '\'' +
                ", writer='" + writer + '\'' +
                ", producer='" + producer + '\'' +
                ", editor='" + editor + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", stars='" + stars + '\'' +
                ", downloadLink='" + downloadLink + '\'' +
                '}';
    }
}
