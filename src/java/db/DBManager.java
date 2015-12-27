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

    private static final DBManager instance = new DBManager();

    private MovieCategoryManager movieCategoryManager;
    private MovieManager movieManager;
    private HallManager hallManager;
  
    public static DBManager getInstance() {
        return instance;
    }

    private DBManager() {
        movieCategoryManager = new MovieCategoryManager();
        movieManager = new MovieManager();
        hallManager = new HallManager();
        initDataBase();
    }

    private void initDataBase() {
        createDB();
        movieCategoryManager.createTable();
        movieManager.createTable();
        hallManager.createTable();
    }

    private void createDB() {
        DBHelper.executeUpdateStatment(db.DBHelper.CREATE_DB);
    }

    public boolean addEntity(MovieCategory movieCategory) {
        return movieCategoryManager.addEntity(movieCategory);
    }
}
