/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;
import java.util.List;
import models.Promotion;


/**
 *
 * @author efrat
 */
public class PromotionCategoryPageView implements ICinemaView {

    List<Promotion> promotionsOnCategory;

    public PromotionCategoryPageView(List<Promotion> promotionsOnCategory) {
        this.promotionsOnCategory = promotionsOnCategory;
    }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        for (Promotion promotionsOnCategory : promotionsOnCategory) {
            res.append("<div style=\"margin-bottom: 10px; padding-bottom: 10px;\"><div style=\"min-height: 200px;\">"
                    + "     style=\"background-image: url(" + promotionsOnCategory.getImage() + ");\"></div>"
                    + "         <h1>" + promotionsOnCategory.getDescription()+ "</h1>"
                    + "</div></div>");

            res.append(html.LayoutHelper.addScripts("../../scripts/movie.js"));
            res.append(LayoutHelper.getFooter());
        }
        return res.toString();
    }

}
