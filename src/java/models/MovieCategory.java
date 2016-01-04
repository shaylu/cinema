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
public class MovieCategory implements DBEntity{
    protected int id;
    protected String name;

    public MovieCategory(){
        
    }
    public MovieCategory(String name) {
        this.name = name;
    }
    
    public MovieCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public MovieCategory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}