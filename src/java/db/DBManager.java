/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import static db.DBHelper.CREATE_DB;
import static db.DBHelper.getConnection;
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
    private ReviewsManager reviewsManager;

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
        reviewsManager = new ReviewsManager();

        initDataBase();
    }

    private void initDataBase() {
        createDB();
        creatTables();
    }

    private void createDB() {
        DBHelper.executeUpdateStatment(db.DBHelper.CREATE_DB);
    }

    public boolean addEntity(MovieCategory movieCategory) {
        return movieCategoryManager.addEntity(movieCategory);
    }

    private void addCommand(String command, StringBuilder strBuilder) {
        strBuilder.append(command);
        strBuilder.append(";\n");
    }

    private void creatTables() {
        Connection conn = null;
        try {

            conn = DBHelper.getConnection(); // for use when db wasn't created yet
            Statement stmt = (Statement) conn.createStatement();

            stmt.executeUpdate(movieCategoryManager.getCREATE_TABLE());
            stmt.executeUpdate(movieManager.getCREATE_TABLE());
            stmt.executeUpdate(hallManager.getCREATE_TABLE());
            stmt.executeUpdate(showManager.getCREATE_TABLE());
            stmt.executeUpdate(orderManager.getCREATE_TABLE());
            stmt.executeUpdate(reviewsManager.getCREATE_TABLE());
            stmt.executeUpdate(companyManager.getCREATE_TABLE());
            stmt.executeUpdate(promotionCategoryManager.getCREATE_TABLE());
            stmt.executeUpdate(promotionManager.getCREATE_TABLE());
            stmt.executeUpdate(usersManager.getCREATE_TABLE());

        } catch (Exception e) {
            System.out.println("Faield");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }

        //StringBuilder createAllTablesStr = new StringBuilder();
        //addCommand(movieCategoryManager.getCREATE_TABLE(), createAllTablesStr);
//        addCommand(movieManager.getCREATE_TABLE(), createAllTablesStr);
//        addCommand(hallManager.getCREATE_TABLE(), createAllTablesStr);
        //  addCommand(showManager.getCREATE_TABLE(), createAllTablesStr);
        //  addCommand(orderManager.getCREATE_TABLE(), createAllTablesStr);
        //  addCommand(companyManager.getCREATE_TABLE(), createAllTablesStr);
        // addCommand(promotionCategoryManager.getCREATE_TABLE(), createAllTablesStr);
        //addCommand(promotionManager.getCREATE_TABLE(), createAllTablesStr);
        //addCommand(usersManager.getCREATE_TABLE(), createAllTablesStr);
        //System.out.println(createAllTablesStr.toString());
        //DBHelper.executeUpdateStatment(createAllTablesStr.toString());
    }
}
