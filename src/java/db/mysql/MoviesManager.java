/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import models.Movie;

/**
 *
 * @author shay.lugasi
 */
public class MoviesManager extends DbManagerEntity {

    public enum ShowTime {

        DONT_CARE, MORNING, NOON, EVENING, NEXT_3_HOURS;
    }

    public MoviesManager(DbManager manager) {
        this.manager = manager;
    }

    public boolean add(String name, Date release_date, int mov_length, int cat_id, String plot, String poster_url, String trailer_url, boolean is_recommended) {
        throw new UnsupportedOperationException();
    }

    public Movie getMovie(int id) {
        throw new UnsupportedOperationException();
    }

    public List<Movie> getAll() {
        throw new UnsupportedOperationException();
    }

    public boolean delete(int mov_id) {
        throw new UnsupportedOperationException();
    }

    public List<Movie> getRecommended() {
        throw new UnsupportedOperationException();
    }

    // if cat_id == 0 then it dosen't matter what category
    public List<Movie> getAllByFilter(String keyword, int cat_id, boolean has_trailer, boolean is_recommended, ShowTime show_time) {
        throw new UnsupportedOperationException();
    }
}
