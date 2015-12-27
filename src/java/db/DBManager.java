/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;
import models.Hall;
import models.MovieCategory;

/**
 *
 * @author Dell
 */
public class DBManager {

    private static final DBManager instance = new DBManager();

    private MovieCategoryManager movieCategoryManager;
    private CopanyManager companyManager;
    private HallManager hallManager;
    private OrderManager orderManager;
    private MovieManager movieManager;
    private ShowsManager showManager;
    private PromotionManager promotionManager;
    private PromotionCategoryManager promotionCategoryManager;

    public static DBManager getInstance() {
        return instance;
    }

    private DBManager() {
        movieCategoryManager = new MovieCategoryManager();
        showManager = new ShowsManager();
        companyManager = new CopanyManager();
        hallManager = new HallManager(); 
        orderManager = new OrderManager();
        movieManager = new MovieManager();
        promotionManager = new PromotionManager();
        promotionCategoryManager = new PromotionCategoryManager();    
        initDataBase();
    }

    private void initDataBase() {
        
        createDB();
        movieCategoryManager.createTable();
        companyManager.createTable();
        showManager.createTable();
        hallManager.createTable();
        orderManager.createTable();
        movieManager.createTable();
        promotionManager.createTable();
        promotionCategoryManager.createTable();
    }

    private void createDB() {
        DBHelper.executeUpdateStatment(db.DBHelper.CREATE_DB);
    }

    public boolean addEntity(MovieCategory movieCategory) {
        return movieCategoryManager.addEntity(movieCategory);
    }
}


