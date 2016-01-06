/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import static db.mysql.MovieCategoriesManager.SELECT_ALL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.MovieCategory;
import models.PromotionCategory;

/**
 *
 * @author shay.lugasi
 */
public class PromoCategoriesManager extends DbManagerEntity {

    public final static String INSERT_QUERY = "INSERT INTO promotion_categories (name) values(?)";
    public final static String DELET_PROMOTIONCATEGORY = "DELET from promotion_categories WHERE promo_cat_id = (?)";
    public final static String SELECT_ALL_PROMORIONCATEGORY = "SELECT * FROM  promotion_categories";

    public PromoCategoriesManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(String name) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            statement.setString(1, name);
            return statement.executeUpdate();
        }

    }

    public int addDefaultValues() throws ClassNotFoundException, SQLException {
        int result = 0;
        result += add("");
        result += add("");
        result += add("");
        result += add("");
        return result;
    }
  

    public List<PromotionCategory> getAll() throws ClassNotFoundException, SQLException {

        ArrayList<PromotionCategory> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL);
            while (rs.next()) {
                PromotionCategory mc = createPromotionCategoryFromMySql(rs);
                result.add(mc);
            }
        }

        return result;
    }

    private PromotionCategory createPromotionCategoryFromMySql(ResultSet rs) throws SQLException {
        PromotionCategory result = new PromotionCategory();
        result.setId(rs.getInt("promo_cat_id"));
        result.setName(rs.getString("name"));
        return result;

    }

}
