/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;
import java.util.List;
import java.util.Locale;
import models.MovieCategory;

/**
 *
 * @author Dell
 */
public class MoviesSearchView implements ICinemaView {
    List<MovieCategory> categories;

    public MoviesSearchView(List<MovieCategory> categories) {
        this.categories = categories;
    }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<h1>Search Results</h1>\n"
                + "<div class=\"content-box\">"
                + "<form name=\"search\" id=\"search\" method=\"post\">\n"
                + "  <label for=\"keyword\">Search by keyword:</label>\n"
                + "  <input type=\"text\" name=\"keyword\" id=\"keyword\"/>\n"
                + "  <label for=\"selCatID\">Category:</label>\n"
                + getCategoriesSelectBox()
                + "  <label for\"hasTrailer\">Has Trailer</label>\n"
                + "  <input type=\"checkbox\" name=\"hasTrailer\" id=\"hasTrailer\" value=\"1\" />\n"
                + "  <label for=\"lastTickets\">Last Tickets</label>\n"
                + "  <input type=\"checkbox\" name=\"lastTickets\" id=\"lastTickets\" value=\"1\" />\n"
                + "  <label for=\"isRecommanded\">Is Recomanded</label>\n"
                + "  <input type=\"checkbox\" name=\"isRecommanded\" id=\"isRecommanded\" value=\"1\" />\n"
                + "  <input type=\"submit\" value=\"Search\" />\n"
                + "</form></div>"
                + "<div id=\"results\"></div>"
        );

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../scripts/movies.js"));
        res.append(LayoutHelper.getFooter());

        return res.toString();
    }
    
       private String getCategoriesSelectBox() {
        StringBuilder res = new StringBuilder();
        res.append("<select id=\"selCatID\" name=\"selCatID\">\n");
        res.append("<option value=\"0\">All</option>");
        for (MovieCategory category : categories) {
            res.append("<option value=\"" + category.getId() + "\">" + category.getName() + "</option>");
        }
        res.append("</select>");
        return res.toString();
    }

}
