/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;
import java.util.List;
import models.MovieCategory;
import models.Promotion;

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
        res.append(LayoutHelper.getHeader("/cinema_app/scripts/owl-carousel/owl.carousel.css", "/cinema_app/scripts/owl-carousel/owl.theme.css"));
        res.append(""
                + getSlider()
                + "<h3>Search for Movies:</h3><div class=\"content-box\">"
                + "  <p><form name=\"searchByKeyword\" id=\"searchByKeyword\" method=\"post\">\n"
                + "     <label for=\"txtKeyword\">Search by Keyword</label>\n"
                + "     <input type=\"text\" id=\"txtKeyword\" name=\"txtKeyword\">\n"
                + "     <input type=\"submit\" value=\"Search\" />\n"
                + "  </form></p>"
                + "  <p><form name=\"searchByCategory\" id=\"searchByCategory\" method=\"post\">\n"
                + "  <label for=\"selCatID\">Search by Category</label>\n"
                + getCategoriesSelectBox()
                + "  <input type=\"submit\" value=\"Search\" />\n"
                + "</form></p>"
                + "<p>&nbsp;</p></div>"
                + "<h3>Special Offer For U:</h3><div id=\"promoDiv\" class=\"promo\"></div>"
                + "<p><h3>Recomended Movies:</h3>"
                + "<p><div id=\"recomendedMovies\" class='content-box'></div></p></p>"
        );

        res.append(html.LayoutHelper.addScripts("/cinema_app/scripts/promos.js"));
        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "/cinema_app/scripts/owl-carousel/owl.carousel.js", "../scripts/home.js"));
        //   res.append(html.LayoutHelper.addScripts("../../scripts/promos.js"));
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

    private String getSlider() {
        return "<div class='more-movies'><h4>>> More Movies</h4></div><div id='owl-slider' class='owl-carousel'></div>";
    }
}
