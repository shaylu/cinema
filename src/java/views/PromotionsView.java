/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.ControllerHelper;
import db.DbManager;
import html.LayoutHelper;
import java.util.List;
import models.PromotionCategory;
import db.PromosManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class PromotionsView implements ICinemaView {

    List<PromotionCategory> categories;
    List<Promotion> promotions;
    ArrayList<PromotionCategoryPresentation> promoCatPresent;

    public PromotionsView(List<PromotionCategory> categories) {
        this.categories = categories;
        //this.promotions = promotions;
        CreatePromoCatByRandPicList();
    }

    private void CreatePromoCatByRandPicList() {
        boolean flag = false;
        this.promoCatPresent = new ArrayList<PromotionCategoryPresentation>();

        for (PromotionCategory categoryPromo : categories) {
            flag = false;
            for (Promotion promo : promotions && !flag) {
                if (categoryPromo == promo.getPromoCategorie()) {
                    this.promoCatPresent.add(new PromotionCategoryPresentation(categoryPromo.id, categoryPromo.name, promo.getImage()));
                    flag = true;
                }
            }
        }
    }


    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        for (PromotionCategoryPresentation promoCatPresent : promoCatPresent) {

            res.append("<div class=\"promoCat\" data-id=\" " + promoCatPresent.id + "\">");
            res.append(promoCatPresent.name + "</div>");
            res.append("style=\"background-image: url(" + promoCatPresent.getImg() + ");\"");

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../scripts/promos.js"));
        res.append(LayoutHelper.getFooter());
        return res.toString();
    }



    private String getHref(String catName) {
        StringBuilder res = new StringBuilder();
        res.append("http://localhost:8084/cinema_app/app/promos/{");
        res.append(catName);
        res.append("}");
        return res.toString();
    }
}
