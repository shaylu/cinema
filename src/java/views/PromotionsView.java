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
public class PromotionsView implements ICinemaView {
    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<h1>Promotions</h1>\n"
                + "<p><button id=\"btnShowPromos\">Show Promotions</button>"
                + "<div id=\"promos\"></div></p>"
        );

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../scripts/promos.js"));
        res.append(LayoutHelper.getFooter());
        return res.toString();
    }
}
