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
            res.append("<div style=\"margin-bottom: 10px; padding-bottom: 10px;\"><div style=\"min-height: 200px;\">"
                    + "     <div class=\"movie_poster\" style=\"background-image: url(" + movie.getPoster() + ");\"></div>"
                    + "     <div class=\"movie_body grey_text\">"
                    + "         <h1>" + movie.getName() + "<span class=\"rank\" data-id=" + movie.getId() + "></span></h1>"
                    + "         <p><span>"+ movie.getCategory().getName() + "</span> <span>(" + df.format(movie.getRelease_date()) + ")</span></p>"
                    + "         <div class=\"content-box\">" + movie.getPlot() + "</div>"
                    + "     </div>"
                    + "</div></div>"
                    + "<h4>Shows</h4>"
                    + "<div class=\"content-box\" id=\"shows\" data-id=\"" + movie.getId() + "\">" 
                    + "</div>"
                    + "<h4>Reviews</h4>"
                    + "<div class=\"content-box\" id=\"reviews\" data-id=\"" + movie.getId() + "\">" 
                    + "</div>"
                    + "<p>"
            );
        }

        res.append(html.LayoutHelper.addScripts("../../scripts/movie.js"));
        res.append(LayoutHelper.getFooter());
        return res.toString();
    }

}
