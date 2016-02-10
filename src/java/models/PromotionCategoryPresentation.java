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
public class PromotionCategoryPresentation extends PromotionCategory {

    String img;

    public PromotionCategoryPresentation(int id, String name, String img) {
        super(id, name);
        this.img = img;

    }

    public String getImg() {
        return img;
    }
}
