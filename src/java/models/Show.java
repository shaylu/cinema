/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.google.gson.JsonObject;
import java.util.Date;
import org.joda.time.LocalTime;

/**
 *
 * @author Dell
 */
public class Show {

    public int id;
    public Movie movie;
    public Hall hall;
    public Date date;
    public String time;
    public int num_of_seats_left;
    public double price_per_seate;

    public Show(int id, Movie movie, Hall hall, Date date, String time, int num_of_seats_left, double price_per_seate) {
        this.id = id;
        this.movie = movie;
        this.hall = hall;
        this.date = date;
        this.time = time;
        this.num_of_seats_left = num_of_seats_left;
        this.price_per_seate = price_per_seate;
    }

    public Show(int id, Date date, int num_of_seats_left) {
        this.id = id;
        this.date = date;
        this.num_of_seats_left = num_of_seats_left;
        
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
        this.num_of_seats_left = num_of_sets_left;
    }

    public int getNumOfSeatsLeft() {
        return this.num_of_seats_left;
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

    public Date getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
      public String toRedisJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", getId());
        json.addProperty("date", getDate().toString());
        json.addProperty("num_of_seats_left", getNumOfSeatsLeft());
        return json.toString();
    }

}
