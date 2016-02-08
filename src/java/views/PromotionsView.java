/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;
import java.util.List;
import models.PromotionCategory;

/**
 *
 * @author Dell
 */
public class PromotionsView implements ICinemaView {

    List<PromotionCategory> categories;

    public PromotionsView(List<PromotionCategory> categories) {
        this.categories = categories;
    }

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

    private String getPromotionCategoriesBoxs() {
        StringBuilder res = new StringBuilder();
        for (PromotionCategory category : categories) {
            /*<a href="http://www.webmasters.org.il">
  <img src="http://webmaster.org.il/images/logo.gif" 
       alt="אתר וובמאסטר" />
</a>*/
            res.append("<a href=\"" + getHref(category.name) + "< img src = " + /* getImg() +*/  "alt = " + category.name + "/>");
        }

        return res.toString();
    }

    private String getHref(String catName) {
        StringBuilder res = new StringBuilder();
        res.append("http://webedu15.mtacloud.co.il:8080/cinema_app/app/promos/{");
        res.append(catName);
        res.append("}\n");
        return res.toString();
    }
}
