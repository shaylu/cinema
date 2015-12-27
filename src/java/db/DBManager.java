/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import models.MovieCategory;

/**
 *
 * @author Dell
 */
public class DBManager {
    public final static String MySqlClassName = "com.mysql.jdbc.Driver";
    public final static String MySqlHost = "jdbc:mysql://localhost:3306/cinema_city";
    public final static String MySqlHost2 = "jdbc:mysql://localhost:3306";
    public final static String MySqlUsername = "root";
    public final static String MySqlPassword = "1234";
    private static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS cinema_city";

    private static final DBManager instance = new DBManager();

    private MovieCategoryManager movieCategoryManager;

    public static DBManager getInstance() {
        return instance;
    }

    private DBManager() {
        movieCategoryManager = new MovieCategoryManager();
        initDataBase();
    }

    private void initDataBase() {
        createDB();
        movieCategoryManager.createTable();
    }

    
    private void createDB() {
        DBHelper.executeUpdateStatment(CREATE_DB);
    }
    
    public boolean addEntity(MovieCategory movieCategory){
        return movieCategoryManager.addEntity(movieCategory);
    }
}