/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.PromotionCategory;

/**
 *
 * @author efrat
 */
public class PromotionCategoryManager implements DBEntityManager<PromotionCategory>  {

    private static final Logger LOGGER = Logger.getLogger(HallManager.class.getName());
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS promotion_categories(\n" +
"  promo_cat_id INT AUTO_INCREMENT,\n" +
"  name VARCHAR(100) NOT NULL,\n" +
"  PRIMARY KEY (promo_cat_id));";
    private final static String INSERT_TABLE = "INSERT INTO promotion_categories (name) values(?)";
    private final static String DELET_PROMOTIONCATEGORY = "DELET from promotion_categories WHERE promo_cat_id = (?)";

    @Override
    public void createTable() {
        DBHelper.executeUpdateStatment(CREATE_TABLE);
    }

    @Override
    public boolean addEntity(PromotionCategory entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = (PreparedStatement) conn.prepareStatement(INSERT_TABLE);

            statement.setString(1, entity.getName());
            statement.execute();
            result = true;
        } catch (ClassNotFoundException | SQLException ex) {
            result = false;
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
    }

    @Override
    public void update(PromotionCategory entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(PromotionCategory entity) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBHelper.getConnection();
            java.sql.PreparedStatement statement = conn.prepareStatement(DELET_PROMOTIONCATEGORY);
            statement.setInt(1, entity.getId());
            statement.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            result = false;
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }  
    }
    
}