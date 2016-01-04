/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Promotion;

/**
 *
 * @author efrat
 */
public class PromotionManager implements DBEntityManager<Promotion> {

    private static final Logger LOGGER = Logger.getLogger(MovieCategoryManager.class.getName());
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS promotions(\n"
            + "  promo_id INT AUTO_INCREMENT,\n"
            + "  comp_id INT NOT NULL,\n"
            + "  promo_cat_id INT NOT NULL,\n"
            + "  description VARCHAR(500) NULL,\n"
            + "  exp_date DATE NULL,\n"
            + "  promo_code VARCHAR(50) NULL,\n"
            + "  image VARCHAR(50) NULL,\n"
            + "  PRIMARY KEY (promo_id),\n"
            + "  INDEX promo_comp_idx (comp_id ASC),\n"
            + "  INDEX promo_cat_id_idx (promo_cat_id ASC),\n"
            + "  CONSTRAINT promo_comp\n"
            + "    FOREIGN KEY (comp_id)\n"
            + "    REFERENCES companies (comp_id)\n"
            + "    ON DELETE NO ACTION\n"
            + "    ON UPDATE NO ACTION,\n"
            + "  CONSTRAINT promo_cat_id\n"
            + "    FOREIGN KEY(promo_cat_id)\n"
            + "    REFERENCES promotion_categories (promo_cat_id)\n"
            + "    ON DELETE NO ACTION\n"
            + "    ON UPDATE NO ACTION)";
    private final static String INSERT_TABLE = "INSERT INTO promotions (comp_id, promo_cat_id, description, exp_date,"
            + "promo_code, image) values(?,?,?,?,?,?)";
    private final static String DELET_PROMOTION = "DELET from promotions WHERE promo_id = (?)";
    private final static String UPDATE_PROMOTION = "UPDATE promotions SET comp_id = ?, promo_cat_id = ?, "
            + "description = ?, exp_date = ?, promo_code = ?, image = ?   WHERE promo_id = ?";
    private final static String SELECT_ALL_PROMOTION = "SELECT * FROM (promotions P inner join companys C "
            + "on P.comp_id = C.comp_id) inner join promotion_categories PC on PC.promo_cat_id = P.promo_cat_id";

    public static String getCREATE_TABLE() {
        return CREATE_TABLE;
    }

    @Override
    public void createTable() {
        DBHelper.executeUpdateStatment(CREATE_TABLE);
    }

    @Override
    public boolean addEntity(Promotion entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = (PreparedStatement) conn.prepareStatement(INSERT_TABLE);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("dd-MM-yyyy");

            statement.setInt(1, entity.getCompanie().getId());
            statement.setInt(2, entity.getPromoCategorie().getId());
            statement.setString(3, entity.getDescription());
            statement.setString(4, dateformatSql.format(entity.getDate()));
            statement.setString(5, entity.getPromoCode());
            statement.setString(6, entity.getImage());
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
    public boolean update(Promotion entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = (PreparedStatement) conn.prepareStatement(UPDATE_PROMOTION);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("dd-MM-yyyy");

            statement.setInt(1, entity.getCompanie().getId());
            statement.setInt(2, entity.getPromoCategorie().getId());
            statement.setString(3, entity.getDescription());
            statement.setString(4, dateformatSql.format(entity.getDate()));
            statement.setString(5, entity.getPromoCode());
            statement.setString(6, entity.getImage());
            statement.setInt(7, entity.getId());
            statement.executeUpdate();
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
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Promotion entity) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBHelper.getConnection();
            java.sql.PreparedStatement statement = conn.prepareStatement(DELET_PROMOTION);
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

    public ArrayList<Promotion> getAllPromotion() {
        ArrayList<Promotion> allPromotion = new ArrayList<Promotion>();
        ResultSet rs = null;
        try {
            rs = DBHelper.executeQueryStatment(SELECT_ALL_PROMOTION);
            while (rs.next()) {
                Promotion promo = getPromotionByResultSetLine(rs);
                allPromotion.add(promo);
            }

        } catch (Exception e) {
        }
        return allPromotion;
    }

    public static Promotion getPromotionByResultSetLine(ResultSet rs) {
        Promotion promo = new Promotion();
        try {
            promo.setId(rs.getInt(" promo_id"));
            promo.setCompanie(CompanyManager.getCampanyByResultSetLine(rs));
            promo.setPromoCategorie(PromotionCategoryManager.getPromotionCategoryByResultSetLine(rs));
            promo.setDescription(rs.getString("description"));
            promo.setDate(rs.getDate("exp_date"));
            promo.setPromoCode(rs.getString("promo_code"));
            promo.setImage(rs.getString("image"));
        } catch (Exception e) {
        }
        return promo;
// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
