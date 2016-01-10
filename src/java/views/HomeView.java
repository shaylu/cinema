/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;
import java.util.List;
import models.MovieCategory;

/**
 *
 * @author Dell
 */
public class HomeView implements ICinemaView {

    List<MovieCategory> categories;

    public HomeView(List<MovieCategory> categories) {
        this.categories = categories;
    }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<h1>Home</h1>\n"
                + "<p><form name=\"searchByKeyword\" id=\"searchByKeyword\" method=\"post\">\n"
                + "  <label for=\"txtKeyword\">Search by Keyword</label>\n"
                + "  <input type=\"text\" id=\"txtKeyword\" name=\"txtKeyword\">\n"
                + "  <input type=\"submit\" value=\"Search\" />\n"
                + "</form>"
                + "<div id=\"byKeword\"></div></p>"
                + "<p><form name=\"searchByCategory\" id=\"searchByCategory\" method=\"post\">\n"
                + "  <label for=\"selCatID\">Search by Category</label>\n"
                + getCategoriesSelectBox()
                + "  <input type=\"submit\" value=\"Search\" />\n"
                + "</form>"
                + "<div id=\"byCategory\"></div></p>"
                + "<p><h2>Recomended Movies</h2>"
                + "<button id=\"btnGetRecomended\">Get Recomended Movies</button><br />"
                + "<p><div id=\"recomendedMovies\"></div></p></p>"
        );

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../scripts/home.js"));
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
