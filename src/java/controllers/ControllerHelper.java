/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.mysql.DbManager;

/**
 *
 * @author Liraz
 */
public class ControllerHelper {
     protected static db.mysql.DbManager db;

    static {
        try {
            db = new DbManager();
        } catch (Exception e) {
            String txt;
            txt = e.getMessage();
        }
    }

    
}
