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
public class Hall implements DBEntity {

    public int id;
    public int num_of_seats;

    public Hall(int id, int num_of_seats) {
        this.id = id;
        this.num_of_seats = num_of_seats;
    }

    public Hall() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setId(int hall_id) {
        this.id = hall_id;
    }

    public int getId() {
        return this.id;
    }

    public void setNumOfSeats(int num_of_seats) {
        this.num_of_seats = num_of_seats;
    }

    public int getNumOfSeats() {
        return this.num_of_seats;
    }

}
