/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.DbManager;
import java.sql.SQLException;

/**
 *
 * @author Liraz
 */
public class ControllerHelper {

    private static db.DbManager db;

    public static DbManager getDb() {
        if (db == null) {
            init();
        }
        
        return db;
    }

    static {
        init();
    }

    private static void init() {
        try {
            db = new DbManager();
        } catch (Exception e) {
            String txt;
            txt = e.getMessage();
        }
    }
    
    public static void restartDbInstance() throws SQLException, Exception {
        if (db != null) {
            db = null;
        }
        
        db = new DbManager();
    }

}
