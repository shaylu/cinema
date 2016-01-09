/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author Dell
 */
public class Review {

    public int id;
    public Order order;
    public double rank;
    public String text;
    public Date date;

    public Review() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public double getRank() {
        return rank;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public Review(int id, Order order, double rank, String text, Date date) {
        this.id = id;
        this.order = order;
        this.rank = rank;
        this.text = text;
        this.date = date;
    }
}
