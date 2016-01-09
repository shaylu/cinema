/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.google.gson.JsonObject;
import db.DBEntity;
import java.sql.ResultSet;

public class MovieCategory implements DBEntity {

    public int id;
    public String name;

    public MovieCategory(String name) {
        this.name = name;
    }

    public MovieCategory() {
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

    public String toRedisJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", getId());
        json.addProperty("name", getName());
        return json.toString();
    }
}
