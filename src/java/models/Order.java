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
public class Order implements DBEntity {

    public int id;
    public String clientId;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public Show show;
    public int numOfSeats;
    public double totalPayment;
    public String creditCardLastDigit;
    public int expDateMonth;
    public int expDateYear;
    public Date orderDate;

    public Order(int id, String clientId, String firstName, String lastName, String email, String phoneNumber, Show show, int numOfSeats, double totalPayment, String creditCardLastDigit, int expDateMonth, int expDateYear, Date orderDate) {
        this.id = id;
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.show = show;
        this.numOfSeats = numOfSeats;
        this.totalPayment = totalPayment;
        this.creditCardLastDigit = creditCardLastDigit;
        this.expDateMonth = expDateMonth;
        this.expDateYear = expDateYear;
        this.orderDate = orderDate;
    }

    public Order() {
    }

    public Show getShow() {
        return show;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public void setCreditCardLastDigit(String creditCardLastDigit) {
        this.creditCardLastDigit = creditCardLastDigit;
    }

    public void setExpDateMonth(int expDateMonth) {
        this.expDateMonth = expDateMonth;
    }

    public void setExpDateYear(int expDateYear) {
        this.expDateYear = expDateYear;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @return
     */
    public int getShowId() {
        return show.id;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public String getCreditCardLastDigit() {
        return creditCardLastDigit;
    }

    public int getExpDateMonth() {
        return expDateMonth;
    }

    public int getExpDateYear() {
        return expDateYear;
    }

    public Date getOrderDate() {
        return orderDate;
    }
}
