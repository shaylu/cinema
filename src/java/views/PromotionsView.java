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

    public PromotionsView(List<PromotionCategory> categories) {
        this.categories = categories;

    }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        try {
            res.append("<h1>Promotions</h1>\n"
                    + "<p><button id=\"btnShowPromos\">Show Promotions</button>"
                    + "<div id=\"promos\"></div></p>"
                    + getPromotionCategoriesBoxs()
            );
        } catch (SQLException ex) {
            Logger.getLogger(PromotionsView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PromotionsView.class.getName()).log(Level.SEVERE, null, ex);
        }

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../scripts/promos.js"));
        res.append(LayoutHelper.getFooter());
        return res.toString();
    }

    /*<a href="http://www.webmasters.org.il">
  <img src="http://webmaster.org.il/images/logo.gif" 
       alt="אתר וובמאסטר" />
</a>*/
    private String getPromotionCategoriesBoxs() throws SQLException, ClassNotFoundException{
        StringBuilder res = new StringBuilder();
        for (PromotionCategory categoryPromo : categories) {

            res.append("<a href=\" " + getHref(categoryPromo.name) + "\" " + ">\n< img src = ");
            res.append("\"" + ControllerHelper.getDb().getPromosManager().getRndomPicByPromoCatId(categoryPromo.getId()) + "\" ");
            res.append("alt =\" " + categoryPromo.id + "\" " + "/>");
            res.append("</a>");
        }

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
