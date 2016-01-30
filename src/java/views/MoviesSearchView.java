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
    String keyword;
    int cat_id;
    boolean has_trailer, last_tickets, recomended;

    public MoviesSearchView(List<MovieCategory> categories, String keyword, int cat_id, boolean has_trailer, boolean last_tickets, boolean recomended) {
        this.categories = categories;
        this.keyword = keyword;
        this.cat_id = cat_id;
        this.has_trailer = has_trailer;
        this.last_tickets = last_tickets;
        this.recomended = recomended;
    }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<h1>Search Results</h1>\n"
                + "<div class=\"content-box\">"
                + "<form name=\"search\" id=\"search\" method=\"post\">"
                + "  <p><div class=\"filter-div\">\n"
                + "  <label for=\"keyword\">Search by keyword:</label>\n"
                + "  <input type=\"text\" name=\"keyword\" id=\"keyword\" value=\"" + getKeywordValue() + "\"/>"
                + "  </div>\n"
                + "  <div class=\"filter-div\"><label for=\"selCatID\">Category:</label>\n"
                + getCategoriesSelectBox()
                + "</div>"
                + "  <div class=\"filter-div\">"
                + "     <label for\"hasTrailer\">Has Trailer</label>\n"
                + "     <input type=\"checkbox\" name=\"hasTrailer\" id=\"hasTrailer\" value=\"1\" " + getChecked(has_trailer) + "/>"
                + "  </div>\n"
                + "  <div class=\"filter-div\">"
                + "     <label for=\"lastTickets\">Last Tickets</label>\n"
                + "     <input type=\"checkbox\" name=\"lastTickets\" id=\"lastTickets\" value=\"1\"  " + getChecked(last_tickets) + "/>"
                + "  </div>\n"
                + "  <div class=\"filter-div\">"
                + "     <label for=\"isRecommanded\">Is Recomanded</label>\n"
                + "     <input type=\"checkbox\" name=\"isRecommanded\" id=\"isRecommanded\" value=\"1\" " + getChecked(recomended) + "/>"
                + " </div>"
                + " <div style=\"clear: both; margin: 10px;\"><p><input type=\"submit\" value=\"Search\" /></p>"
                + "<div class=\"filter-div sort-div\">"
                + " Sort by: "
                + "     <a href=\"#\" class=\"sort-by active\" data-sort-by=\"name\">Name</a> "
                + "     <a href=\"#\" class=\"sort-by\" data-sort-by=\"num_of_seats_left\">Seats Left</a> "
                + "     <a href=\"#\" class=\"sort-by\" data-sort-by=\"rank\">Rank</a> "
                + "     <a href=\"#\" class=\"sort-by\" data-sort-by=\"cat_name\">Category</a>\n"
                + "</div></div>"
                + "</p>"
                + "\n"
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
            res.append("<option value=\"" + category.getId() + "\" " + getChecked(cat_id, category.getId()) + ">" + category.getName() + "</option>");
        }
        res.append("</select>");
        return res.toString();
    }

    String getKeywordValue() {
        if (keyword != null && !keyword.isEmpty()) {
            return keyword;
        } else {
            return "";
        }
    }

    private String getChecked(int cat_id, int id) {
        if (cat_id == id) {
            return "selected";
        } else {
            return "";
        }
    }

    private String getChecked(boolean has_trailer) {
        if (has_trailer) {
            return "checked";
        } else {
            return "";
        }
    }

}
