/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;
import models.PromotionCategory;

/**
 *
 * @author efrat
 */
public class PromotionCategoryPageView implements ICinemaView {

    PromotionCategory promotionCategory;

    public PromotionCategoryPageView(PromotionCategory promotionCategory) {
        this.promotionCategory = promotionCategory;
    }
    
    
    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        
        
        res.append(html.LayoutHelper.addScripts("../../scripts/movie.js"));
        res.append(LayoutHelper.getFooter());
        return res.toString();
    }
    
}
