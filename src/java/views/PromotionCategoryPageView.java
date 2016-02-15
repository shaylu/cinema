/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;
import java.util.List;
import models.Promotion;
import models.PromotionCategory;

/**
 *
 * @author efrat
 */
public class PromotionCategoryPageView implements ICinemaView {

    PromotionCategory promoCat;

    public PromotionCategoryPageView(PromotionCategory promoCat) {
        this.promoCat = promoCat;

    }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<div>Special offers: " + promoCat.getName() + "</div>" +
        "<div class=\"content-box\" id=\"promos\" data-catId=\"" +  promoCat.getId() + "\"> </div>" +
        "<div><a href=\"/cinema_app/app/promos\"> << Back </a></div>");
        res.append(html.LayoutHelper.addScripts("/cinema_app/scripts/promos.js"));
         res.append(html.LayoutHelper.addScripts("/cinema_app/scripts/promo_category.js"));
        // res.append(html.LayoutHelper.addScripts("/cinema_app/scripts/promos.js","/cinema_app/scripts/promo_category.js"));
        res.append(LayoutHelper.getFooter());

        return res.toString();
    }

}
