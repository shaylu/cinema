/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.google.gson.Gson;
import java.util.Date;

/**
 *
 * @author shay.lugasi
 */
public class MovieSearchDetails {
    public static class MovieSearchDetailsShow {
        public int show_id;
        public Date date;
        public String time;
        public int num_of_seats_left;

        public MovieSearchDetailsShow(int show_id, Date date, String time, int num_of_seats_left) {
            this.show_id = show_id;
            this.date = date;
            this.time = time;
            this.num_of_seats_left = num_of_seats_left;
        }
    }
    
    public static class MovieSearchDetailsCategory {
        public int cat_id;
        public String cat_name;

        public MovieSearchDetailsCategory(int cat_id, String cat_name) {
            this.cat_id = cat_id;
            this.cat_name = cat_name;
        }
    }
    
    public int id;
    public String name;
    public Date release_date;
    public double movie_length;
    public MovieSearchDetailsCategory category;
    public String plot;
    public String poster;
    public String trailer;
    public boolean is_recomanded;
    
    public int rank;
    public MovieSearchDetailsShow show;

    public MovieSearchDetails(int id, String name, Date release_date, double movie_length, MovieSearchDetailsCategory category, String plot, String poster, String trailer, boolean is_recomanded, int rank, MovieSearchDetailsShow show) {
        this.id = id;
        this.name = name;
        this.release_date = release_date;
        this.movie_length = movie_length;
        this.category = category;
        this.plot = plot;
        this.poster = poster;
        this.trailer = trailer;
        this.is_recomanded = is_recomanded;
        this.rank = rank;
        this.show = show;
    }
    
    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
