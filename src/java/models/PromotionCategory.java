/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import db.DBEntity;

/**
 *
 * @author Dell
 */
public class PromotionCategory implements DBEntity {

    protected int id;
    protected String name;

    public PromotionCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PromotionCategory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}