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
public class Company implements DBEntity{
    protected int id;
    protected String name;
    protected String address;
    protected String aboutText;

    public Company(int id, String name, String address, String aboutText) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.aboutText = aboutText;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getAboutText() {
        return aboutText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAboutText(String aboutText) {
        this.aboutText = aboutText;
    }
}
