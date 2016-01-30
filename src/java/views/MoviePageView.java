/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import models.Movie;

/**
 *
 * @author Dell
 */
public class MoviePageView implements ICinemaView {

    Movie movie;

    public MoviePageView(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        if (movie == null) {
            res.append("Oops... We can't find the movie you wanted.");
        } else {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            res.append("<div style=\"min-height: 200px\">"
                    + "     <div class=\"movie_poster\" style=\"background-image: url(" + movie.getPoster() + ");\"></div>"
                    + "     <div class=\"movie_body grey_text\">"
                    + "         <h1>" + movie.getName() + "</h1>"
                    + "         <p><span>"+ movie.getCategory().getName() + "</span> <span>(" + df.format(movie.getRelease_date()) + ")</span></p>"
                    + "         <div class=\"content-box\">" + movie.getPlot() + "</div>"
                    + "     </div>"
                    + "</div>"
                    + "     <div class=\"content-box\" id=\"shows\" data-id=\"" + movie.getId() + "\">" 
                    + "</div>"
                    + "<p><button id=\"btnMovieDetails\" data-id=\"" + movie.getId() + "\">Get Movie Details</button>"
                    + "<div id=\"movieDetails\"></div></p>"
                    + "<p><button id=\"btnMovieShows\" data-id=\"" + movie.getId() + "\">Get Movie Shows</button>"
                    + "<div id=\"movieShows\"></div></p>"
                    + "<p><button id=\"btnMovieReviews\" data-id=\"" + movie.getId() + "\">Get Movie Reviews</button>"
                    + "<div id=\"movieReviews\"></div></p>"
            );
        }

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../scripts/movie.js"));
        res.append(LayoutHelper.getFooter());
        return res.toString();
    }

}
