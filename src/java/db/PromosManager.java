/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import static db.MovieCategoriesManager.INSERT_QUERY;
import static db.MovieCategoriesManager.SELECT_ALL;
import static db.MovieCategoriesManager.SELECT_MOVIE_CATEGORY;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.MovieCategory;
import models.Promotion;

/**
 *
 * @author shay.lugasi
 */
public class PromosManager extends DbManagerEntity {

    public final static String INSERT_QUERY = "INSERT INTO promotions (comp_id, promo_cat_id, description, exp_date,"
            + "promo_code, image) values(?,?,?,?,?,?)";
    public final static String DELET_PROMOTION = "DELETE FROM promotions WHERE promo_id = (?)";
    public final static String SELECT_ALL = "SELECT * FROM promotions P inner join companies C on P.comp_id = C.comp_id";
    public static final String SELECT_PROMOION = "SELECT * FROM promotions P inner join companies C on P.comp_id = C.comp_id WHERE promo_id = ?";
    public static final String SELECT_RAND = "SELECT * FROM promotions p inner join companies c on P.comp_id = C.comp_id ORDER BY RAND() LIMIT 1;";
    public static final String SELECT_PROMOION_BY_CAT_ID = "";
    
    public PromosManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(int comp_id, int promo_cat_id, String description, Date exp_date, String promo_code, String image) throws SQLException, ClassNotFoundException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("yyyy-MM-dd");
            statement.setInt(1, comp_id);
            statement.setInt(2, promo_cat_id);
            statement.setString(3, description);
            statement.setDate(4, new java.sql.Date(exp_date.getTime()));
            statement.setString(5, promo_code);
            statement.setString(6, image);

            return statement.executeUpdate();
        }
    }
//TODO

    public int addDefaultValues() throws ClassNotFoundException, SQLException, ParseException {
        int result = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        result += add(1, 1, "coffe and cockis in 25 nis", formatter.parse("2016-05-01"), "1111", "coffe.jpg");
        result += add(2, 1, "a meal that includes a burger fries and drink in 35 nis", formatter.parse("2016-08-31"), "2222", "burger.jpg");
        result += add(2, 1, "a T-base in 25 nis", formatter.parse("2016-12-31"), "3333", "shirts.jpg");

        return result;
    }

    public List<Promotion> getAll() throws ClassNotFoundException, SQLException {

        ArrayList<Promotion> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL);
            while (rs.next()) {
                Promotion mc = createPromotionFromMySql(rs);
                result.add(mc);
            }
        }

        return result;
    }
    // get random promotion picture according to promotion category id
    public String getRndomPicByPromoId(int promoCatId) throws SQLException, ClassNotFoundException{
        
        String retPic;
        Promotion promotionToReturn = getPromotionById(promoCatId);
        
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_PROMOION_BY_CAT_ID);
            statement.setInt(1, promoCatId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            promotionToReturn = createPromotionFromMySql(rs);
        }
        
        retPic = promotionToReturn.getImage();
        return retPic;
        
    }

    public Promotion getPromotionById(int id) throws SQLException, ClassNotFoundException {
        Promotion promotionToReturn;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_PROMOION);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            promotionToReturn = createPromotionFromMySql(rs);
        }

        return promotionToReturn;
    }

    public int delete(int id) throws SQLException, ClassNotFoundException {
        int result = 0;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELET_PROMOTION);
            statement.setInt(1, id);
            result = statement.executeUpdate();
            return result;
        }

    }

    public Promotion createPromotionFromMySql(ResultSet rs) throws SQLException, ClassNotFoundException {

        Promotion result = new Promotion();
        result.setId(rs.getInt("P.promo_id"));
        result.setCompanie(manager.getPromoCompaniesManager().getCompanyById(rs.getInt("P.comp_id")));
        result.setPromoCategorie(manager.getPromoCategoriesManager().getPromotionCategoryById(rs.getInt("P.promo_cat_id")));
        result.setDescription(rs.getString("P.description"));
        result.setDate(rs.getDate("P.exp_date"));
        result.setPromoCode(rs.getString("P.promo_code"));
        result.setImage(rs.getString("P.image"));
        return result;
    }
    
    public Promotion getRand() throws SQLException, ClassNotFoundException {
        Promotion promotionToReturn;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_RAND);
            ResultSet rs = statement.executeQuery();
            rs.next();
            promotionToReturn = createPromotionFromMySql(rs);
        }

        return promotionToReturn;
    }
}
