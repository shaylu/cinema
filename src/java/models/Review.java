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
public class Review implements DBEntity {

    protected int id;
    protected Order order;
    protected double rank;
    protected String text;
    protected Date date;

    public Review(int id, Order order, double rank, String text, Date date) {
        this.id = id;
        this.order = order;
        this.rank = rank;
        this.text = text;
        this.date = date;
    }
}
