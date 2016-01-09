/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;

/**
 *
 * @author Dell
 */
public class MoviePageView implements ICinemaView {

    int id;

    public MoviePageView(int id) {
        this.id = id;
    }
    
    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<h1>Movie Page</h1>\n"
                + "<p><button id=\"btnMovieDetails\" data-id=\"" + id + "\">Get Movie Details</button>"
                + "<div id=\"movieDetails\"></div></p>"
                + "<p><button id=\"btnMovieShows\" data-id=\"" + id + "\">Get Movie Shows</button>"
                + "<div id=\"movieShows\"></div></p>"
                + "<p><button id=\"btnMovieReviews\" data-id=\"" + id + "\">Get Movie Reviews</button>"
                + "<div id=\"movieReviews\"></div></p>"
        );

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../scripts/movie.js"));
        res.append(LayoutHelper.getFooter());
        return res.toString();
    }

}
