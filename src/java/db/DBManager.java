/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.ArrayList;
import models.Hall;
import models.MovieCategory;

/**
 *
 * @author Dell
 */
public class DBManager {

    private static final DBManager instance = new DBManager();

    private MovieCategoryManager movieCategoryManager;
    private CompanyManager companyManager;
    private HallManager hallManager;
    private OrderManager orderManager;
    private MovieManager movieManager;
    private ShowsManager showManager;
    private PromotionManager promotionManager;
    private PromotionCategoryManager promotionCategoryManager;
    private UsersManager usersManager;

    public static DBManager getInstance() {
        return instance;
    }

    private DBManager() {
        movieCategoryManager = new MovieCategoryManager();
        showManager = new ShowsManager();
        companyManager = new CompanyManager();
        hallManager = new HallManager();
        orderManager = new OrderManager();
        movieManager = new MovieManager();
        promotionManager = new PromotionManager();
        promotionCategoryManager = new PromotionCategoryManager();
        usersManager = new UsersManager();

        initDataBase();
    }

    private void initDataBase() {
        createDB();
        creatTables();
        /*movieCategoryManager.createTable();
        companyManager.createTable();
        showManager.createTable();
        hallManager.createTable();
        orderManager.createTable();
        movieManager.createTable();
        promotionManager.createTable();
        promotionCategoryManager.createTable();
        usersManager.createTable();*/
    }

    private void createDB() {
        DBHelper.executeUpdateStatment(db.DBHelper.CREATE_DB);
    }

    public boolean addEntity(MovieCategory movieCategory) {
        return movieCategoryManager.addEntity(movieCategory);
    }

    private void creatTables() {
        String createAllTablesStr = movieCategoryManager.getCREATE_TABLE()
                + companyManager.getCREATE_TABLE()
                + showManager.getCREATE_TABLE()
                + hallManager.getCREATE_TABLE()
                + orderManager.getCREATE_TABLE()
                + movieManager.getCREATE_TABLE()
                + promotionManager.getCREATE_TABLE()
                + promotionCategoryManager.getCREATE_TABLE()
                + usersManager.getCREATE_TABLE();
        DBHelper.executeUpdateStatment(createAllTablesStr);
    }
}
