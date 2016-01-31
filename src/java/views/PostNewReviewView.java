/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;

/**
 *
 * @author shay.lugasi
 */
public class PostNewReviewView implements ICinemaView {

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<h3>Add new review</h3>"
                + "<div class=\"content-box\">"
                + " <form name=\"validateOrderCode\" id=\"validateOrderCode\">"
                + "     <p><label for=\"txtOrderCode\">Order Code</label>"
                + "     <input type=\"text\" id=\"txtOrderCode\" name=\"txtOrderCode\" /></p>"
                + "     <p><label for=\"txtLastDigits\">Lat 4 Digits of Credit Card</label>"
                + "     <input type=\"text\" id=\"txtLastDigits\" name=\"txtLastDigits\" /></p>"
                + "     <p><input type=\"submit\" value=\"Submit\"></p>"
                + " </form>"
                + "</div>");
        res.append(LayoutHelper.addScripts("/cinema_app/scripts/add_review.js"));
        res.append(LayoutHelper.getFooter());
        return res.toString();
    }
}
