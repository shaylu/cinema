/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.google.gson.JsonObject;
import db.DBEntity;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dell
 */
public class Movie implements DBEntity {

    public int id;
    public String name;
    public Date release_date;
    public double movie_length;
    public MovieCategory category;
    public String plot;
    public String poster;
    public String trailer;
    public boolean is_recomanded;

    public Movie(String name, Date release_date, int movie_length, String plot, String poster, String trailer, MovieCategory category, boolean is_recomanded) {

        this.name = name;
        this.release_date = release_date;
        this.movie_length = movie_length;
        this.plot = plot;
        this.poster = poster;
        this.trailer = trailer;
        this.category = category;
        this.is_recomanded = is_recomanded;
    }

    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public double getMovie_length() {
        return movie_length;
    }

    public void setMovie_length(double movie_length) {
        this.movie_length = movie_length;
    }

    public MovieCategory getCategory() {
        return category;
    }

    public void setCategory(MovieCategory category) {
        this.category = category;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public boolean is_recomanded() {
        return is_recomanded;
    }

    public void setIs_recomanded(boolean is_recomanded) {
        this.is_recomanded = is_recomanded;
    }

    public String toRedisJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", getId());
        json.addProperty("name", getName());
        json.addProperty("release_date", getRelease_date().toString());
        json.addProperty("movie_length", getMovie_length());
        json.addProperty("category", getCategory().toRedisJson());
        json.addProperty("plot", getPlot());
        json.addProperty("poster", getPoster());
        json.addProperty("trailer", getTrailer());
        json.addProperty("is_recomanded", is_recomanded());
        return json.toString();
    }
}
