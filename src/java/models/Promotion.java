/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import db.DBEntity;
import java.util.Date;

/**
 *
 * @author Dell
 */
public class Promotion implements DBEntity {

    protected int id;
    protected Company company;
    protected PromotionCategory promoCategorie;
    protected String description;
    protected Date date;
    protected String promoCode;
    protected String image;

    public Promotion(int id, Company companie, PromotionCategory promoCategorie, String description, Date date, String promoCode, String image) {
        this.id = id;
        this.company = companie;
        this.promoCategorie = promoCategorie;
        this.description = description;
        this.date = date;
        this.promoCode = promoCode;
        this.image = image;
    }

    public Promotion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public Company getCompanie() {
        return company;
    }

    public PromotionCategory getPromoCategorie() {
        return promoCategorie;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompanie(Company company) {
        this.company = company;
    }

    public void setPromoCategorie(PromotionCategory promoCategorie) {
        this.promoCategorie = promoCategorie;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public void setImage(String image) {
        this.image = image;
    }

}