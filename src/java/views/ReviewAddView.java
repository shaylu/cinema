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
public class ReviewAddView implements ICinemaView {
    int order_id;

    public ReviewAddView(int cat_id) {
        this.order_id = order_id;
    }
    
    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<h1>Add Review Page</h1>\n"
                + "<form name=\"addReview\" id=\"addReview\" method=\"post\" data-id=\"" + order_id +"\">\n"
                + "  <label for=\"txtRank\">Rank</label>\n"
                + getRankSelectBox()
                + "  <p><label for=\"txtReview\">Review</label>\n"
                + "  <textarea id=\"txtReview\"></textarea></p>\n"
                + "  <input type=\"submit\" value=\"Add\" /><span id=\"txtResult\"></span>\n"
                + "</form>"
        );

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../../scripts/add_review.js"));
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
