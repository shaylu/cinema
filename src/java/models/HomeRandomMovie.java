/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Dell
 */
public class HomeRandomMovie {
    public int id;
    public String poster;
    public String name;

    public HomeRandomMovie(int id, String poster, String name) {
        this.id = id;
        this.poster = poster;
        this.name = name;
    }
}
