/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author efrat
 */
public class PromotionCategoryPresentation {

    public PromotionCategory promoCat;
    public String img;

    public PromotionCategoryPresentation() {
    }

    public PromotionCategoryPresentation(int id, String name, String img) {
        this.promoCat = new PromotionCategory(id, name);
        this.img = img;
    }

    public PromotionCategory getPromoCat() {
        return promoCat;
    }

    public String getImg() {
        return img;
    }
}
