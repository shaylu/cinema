/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Company;
import models.MovieCategory;
import models.PromotionCategory;

/**
 *
 * @author shay.lugasi
 */
public class PromoCategoriesManager extends DbManagerEntity {

    public final static String INSERT_QUERY = "INSERT INTO promotion_categories (name) values(?)";
    public final static String DELET_PROMOTIONCATEGORY = "DELETE from promotion_categories WHERE promo_cat_id = (?)";
    public final static String SELECT_ALL_PROMORIONCATEGORY = "SELECT * FROM promotion_categories";
    public final static String SELECT_PROMORIONCATEGORY = "SELECT * FROM  promotion_categories WHERE promo_cat_id = ?";

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
        
        result += add("Foood");
        result += add("Fashion");
        result += add("Movies");
       
        return result;
    }

    public List<PromotionCategory> getAll() throws ClassNotFoundException, SQLException {

        ArrayList<PromotionCategory> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL_PROMORIONCATEGORY);
            while (rs.next()) {
                PromotionCategory mc = createPromotionCategoryFromMySql(rs);
                result.add(mc);
            }
        }

        return result;
    }

    private PromotionCategory createPromotionCategoryFromMySql(ResultSet rs) throws SQLException {
        PromotionCategory result = new PromotionCategory();
        
        int aInt = rs.getInt("promo_cat_id");
        result.setId(aInt);
        result.setName(rs.getString("name"));
        return result;

    }

    public PromotionCategory getPromotionCategoryById(int id) throws ClassNotFoundException, SQLException {
        PromotionCategory result;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_PROMORIONCATEGORY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            result = createPromotionCategoryFromMySql(rs);
        }

        return result;
    }

}
