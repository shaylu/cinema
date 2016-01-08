/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.admin;

import html.LayoutHelper;
import java.util.List;
import models.Movie;
import views.ICinemaView;

/**
 *
 * @author Dell
 */
public class AdminShowsView implements ICinemaView {

    List<Movie> movies;
    List<Integer> halls;

    public AdminShowsView(List<Movie> movies, List<Integer> halls) {
        this.movies = movies;
        this.halls = halls;
    }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<p><button id=\"btnAddDefualtHalls\">Add Default Shows</button></p>");
        res.append("<h1>Admin > Shows</h1>\n"
                + "<h2>Shows</h2>\n"
                + "<div id=\"divAllShows\"></div>\n"
                + "<button name=\"btnRefreshShows\" id=\"btnRefreshShows\">Get All Shows</button>\n"
                + "<h2>Add Show</h2>\n"
                + "<form name=\"addShow\" id=\"addShow\" method=\"post\">\n"
                + "<label for=\"selMovieId\">Movie</label>\n"
                + getMoviesSelectBox()
                + "  <label for=\"selHallId\">Hall</label>\n"
                + getHallsSelectBox()
                + "  <label for=\"txtDateTime\">Date and Time</label>\n"
                + "  <input type=\"datetime-local\" id=\"txtDateTime\" name=\"txtNumOfSeats\">\n"
                + "  <label for=\"numPricePerSeat\">Price Per Seat</label>\n"
                + "  <input type=\"number\" id=\"numPricePerSeat\" name=\"numPricePerSeat\">\n"
                + "  <input type=\"submit\" value=\"Add\" />\n"
                + "</form>");

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../scripts/admin_shows.js"));
        res.append(LayoutHelper.getFooter());

        return res.toString();
    }

    private String getMoviesSelectBox() {
        StringBuilder res = new StringBuilder();
        res.append("<select id=\"selMovieId\" name=\"selMovieId\">\n");
        for (Movie movie : movies) {
            res.append("<option value=\"" + movie.getId() + "\">" + movie.getName() + "</option>");
        }
        res.append("</select>");
        return res.toString();
    }

    private String getHallsSelectBox() {
        StringBuilder res = new StringBuilder();
        res.append("<select id=\"selHallId\" name=\"selHallId\">\n");
        for (Integer hall_id : halls) {
            res.append("<option value=\"" + hall_id + "\">" + hall_id + "</option>");
        }
        res.append("</select>");
        return res.toString();
    }

}
