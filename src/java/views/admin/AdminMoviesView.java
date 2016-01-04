/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.admin;

import html.LayoutHelper;
import java.util.List;
import models.MovieCategory;
import views.ICinemaView;

/**
 *
 * @author Dell
 */
public class AdminMoviesView implements ICinemaView {
    
    List<models.MovieCategory> categories;
    
   public AdminMoviesView(List<models.MovieCategory> categories) {
       this.categories = categories;
   }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<h1>Admin > Movies</h1>\n"
                + "<h2>All Movies</h2>\n"
                + "<div id=\"divAllMovies\"></div>\n"
                + "<button name=\"btnRefreshAllMovies\" id=\"btnRefreshAllMovies\">Get All Movies</button>\n"
                + "<h2>Add Movie</h2>\n"
                + "<form name=\"addMovie\" id=\"addMovie\" method=\"post\">\n"
                + "  <label for=\"txtMovieName\">Movie Name</label>\n"
                + "  <input type=\"text\" id=\"txtMovieName\" name=\"txtMovieName\">\n"
                + "  <label for=\"dateReleaseDate\">Release Date</label>\n"
                + "  <input type=\"date\" id=\"dateReleaseDate\" name=\"dateReleaseDate\">\n"
                + "  <label for=\"numMovieLength\">Movie Length</label>\n"
                + "  <input type=\"number\" id=\"numMovieLength\" name=\"numMovieLength\" min=\"0\" max=\"999\" />\n"
                + "  <label for=\"selCatID\">Movie Category</label>\n"
                + getCategoriesSelectBox()
                + "  <label for=\"txtPlot\">Plot</label>\n"
                + "  <textarea id=\"txtPlot\" rows=4></textarea>\n"
                + "  <label for=\"txtPoster\">Poster URL</label>\n"
                + "  <input type=\"text\" id=\"txtPoster\" name=\"txtPoster\">\n"
                + "  <label for=\"txtTrailer\">Trailer URL</label>\n"
                + "  <input type=\"text\" id=\"txtTrailer\" name=\"txtTrailer\">\n"
                + "  <label for=\"chkIsRecommanded\">Is Recommanded</label>\n"
                + "  <input type=\"checkbox\" id=\"chkIsRecommanded\" name=\"chkIsRecommanded\" value=\"true\" />\n"
                + "  <input type=\"submit\" value=\"Add\" />\n"
                + "</form>");

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../scripts/admin_movies.js"));
        res.append(LayoutHelper.getFooter());

        return res.toString();
    }
    
    private String getCategoriesSelectBox() {
        StringBuilder res = new StringBuilder();
        res.append("<select id=\"selCatID\" name=\"selCatID\">\n");
        for (MovieCategory category : categories) {
            res.append("<option value=\"" + category.getId() + "\">" + category.getName() + "</option>");
        }
        res.append("</select>");
        return res.toString();
    }

}
