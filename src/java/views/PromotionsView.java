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
import models.PromotionCategoryPresentation;
import java.util.Map;

/**
 *
 * @author Dell
 */
public class PromotionsView implements ICinemaView {

    List<PromotionCategoryPresentation> promoCatPresent;

    public PromotionsView(List<PromotionCategoryPresentation> promoCatPresent) {
        this.promoCatPresent = promoCatPresent;
    }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        for (PromotionCategoryPresentation promoCatPresent : promoCatPresent) {

            res.append("<div class=\"promoCat\" data-id=\" " + promoCatPresent.getPromoCat().id + "\" style=\"background-image: url(/cinema_app/images/promos/" + promoCatPresent.getImg() + ");\">");
            res.append("<h3>"+promoCatPresent.getPromoCat().name + "</h3></div>");
        //    res.append("");

        }
        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../scripts/promos.js"));
        res.append(LayoutHelper.getFooter());
        return res.toString();
    }

}
