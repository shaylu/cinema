/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import db.DBEntity;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dell
 */
public class Movie implements DBEntity {
    protected int id;
    protected String name;
    protected Date release_date;
    protected int movie_length;
    protected MovieCategory category;
    protected String plot;
    protected String poster;
    protected String trailer;
    protected boolean is_recomanded;

    public Movie(int id, String name, Date release_date, int movie_length, int cat_id, String plot, String poster, String trailer, boolean is_recomanded, List<MovieCategory> categories) {
        this.id = id;
        this.name = name;
        this.release_date = release_date;
        this.movie_length = movie_length;
        
//        try {
//            this.category = categories.stream().filter(x -> x.id == id).findFirst().get();
//        } catch (Exception e) {
//            this.category = null;
//        }
        
        this.plot = plot;
        this.poster = poster;
        this.trailer = trailer;
        this.is_recomanded = is_recomanded;
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

    public int getMovie_length() {
        return movie_length;
    }

    public void setMovie_length(int movie_length) {
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
    
    
}