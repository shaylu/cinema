/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import db.DBEntity;
import java.util.Date;

/**
 *
 * @author Dell
 */
public class Show implements DBEntity {

    public int id;
    public Movie movie;
    public Hall hall;
    public Date date;
    public int num_of_sets_left;
    public double price_per_seate;

    public Show(int id, Movie movie, Hall hall, Date date, int num_of_sets_left, double price_per_seate) {
        this.id = id;
        this.movie = movie;
        this.hall = hall;
        this.date = date;
        this.num_of_sets_left = num_of_sets_left;
        this.price_per_seate = price_per_seate;
    }

    public Show() {
    }

    public void setId(int show_id) {
        this.id = show_id;
    }

    public int getId() {
        return this.id;
    }

    public void setNumOfSeatsLeft(int num_of_sets_left) {
        this.num_of_sets_left = num_of_sets_left;
    }

    public int getNumOfSeatsLeft() {
        return this.num_of_sets_left;
    }

    public void setMovie(Movie movie_id) {
        this.movie = movie_id;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setHall(Hall hall_id) {
        this.hall = hall_id;
    }

    public Hall getHall() {
        return this.hall;
    }

    public void setDate(Date movie_date) {
        this.date = movie_date;
    }

    public Date getShowDate() {
        return this.date;
    }

    public void setPricePerSeat(double price_per_seat) {
        this.price_per_seate = price_per_seat;
    }

    public double getPricePerSeat() {
        return this.price_per_seate;
    }
}
