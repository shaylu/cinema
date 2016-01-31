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
public class ReviewAddView implements ICinemaView {

    int order_id;
    String last_digits;
    Movie movie;
    boolean review_exist;

    public ReviewAddView(int order_id, String last_digits, Movie movie, boolean review_exist) {
        this.review_exist = review_exist;
        this.order_id = order_id;
        this.last_digits = last_digits;
        this.movie = movie;
    }

    @Override
    public String getView() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<div style=\"margin-bottom: 10px; padding-bottom: 10px;\"><div style=\"min-height: 200px;\">"
                + "     <div class=\"movie_poster\" style=\"background-image: url(" + movie.getPoster() + ");\"></div>"
                + "     <div class=\"movie_body grey_text\">"
                + "         <h1>" + movie.getName() + "<span class=\"rank\" data-id=" + movie.getId() + "></span></h1>"
                + "         <p><span>" + movie.getCategory().getName() + "</span> <span>(" + df.format(movie.getRelease_date()) + ")</span></p>"
                + "         <div class=\"content-box\">" + movie.getPlot() + "</div>"
                + "     </div>"
                + "</div></div>");
        res.append("<h3>Add Review</h3>\n"
                + "<form name=\"addReview\" id=\"addReview\" method=\"post\" data-id=\"" + order_id + "\" data-last-digits=\"" + last_digits + "\">\n"
                + "  <div class=\"content-box\">");

        if (!review_exist) {
            res.append("<label for=\"txtRank\">Rank</label>\n"
                    + getRankSelectBox()
                    + "  <p><label for=\"txtReview\">Review</label>\n"
                    + "  <textarea id=\"txtReview\"></textarea></p>\n"
                    + "  <input type=\"submit\" value=\"Add\" /><span id=\"txtResult\"></span>\n");
        } else {
            res.append("<span><p>You have already posted a review.</p></span><div id=\"promo_box\"></div>"
                    + "<script>$(function() {$getRandomPromo($('#addReview #promo_box'));});</script>");
        }
        res.append("</div>"
                + "</form>");

        res.append(html.LayoutHelper.addScripts( "/cinema_app/scripts/promos.js", "/cinema_app/scripts/add_review.js"));
        res.append(LayoutHelper.getFooter());
        return res.toString();
    }

    private String getRankSelectBox() {
        StringBuilder res = new StringBuilder();
        res.append("<select id=\"selRank\" name=\"selRank\">\n");
        for (int i = 1; i <= 5; i++) {
            res.append("<option value=\"" + i + "\">" + i + "</option>");
        }
        res.append("</select>");
        return res.toString();
    }

}
