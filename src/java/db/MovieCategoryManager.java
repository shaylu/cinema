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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.MovieCategory;

/**
 *
 * @author Dell
 */
public class MovieCategoryManager implements DBEntityManager<MovieCategory> {

    private static final Logger LOGGER = Logger.getLogger(MovieCategoryManager.class.getName());
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS movie_categories (cat_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50) NOT NULL)";
    private final static String INSERT_TABLE = "INSERT INTO movie_categories(name) values(?)";
    private final static String DELET_CATEGORY = "DELET from movie_categories WHERE name = (?)";
    private final static String UPDATE_MOVIECATAGORY = "UPDATE movie_categories SET name = ? WHERE cat_id = ?";
    private final static String SELECT_ALLCATAGORY = "SELECT * FROM cinema_city.movie_categories";
    private final static String SELECT_CATEGORYBYNAME = "SELECT cat_id from movie_categories where name = ?";

    public static String getCREATE_TABLE() {
        return CREATE_TABLE;
    }

    @Override
    public void createTable() {
        DBHelper.executeUpdateStatment(CREATE_TABLE);
    }

    public static MovieCategory getMovieCategory(ResultSet rs) throws SQLException {
        MovieCategory CatToReturn = new MovieCategory();
        CatToReturn.setId(rs.getInt("cat_id"));
        CatToReturn.setName(rs.getString("name"));

        return CatToReturn;
    }
    
    public static MovieCategory getMovieCategoryByName(String catName) throws SQLException{
       // Action, Comedy,Drama,Sci-Fi
       MovieCategory movieCatToReturn = new MovieCategory();
       ResultSet rs = null;

       rs = DBHelper.executeQueryStatment(SELECT_ALLCATAGORY);
       movieCatToReturn = getMovieCategory(rs);
       return movieCatToReturn;
    }

    public ArrayList<MovieCategory> getAllMovieCategories() {

        Connection conn = null;
        ArrayList<MovieCategory> ListToReturn = new ArrayList<>();
        boolean result = false;
        ResultSet rs = null;

        try {
            rs = DBHelper.executeQueryStatment(SELECT_ALLCATAGORY);
            while (rs.next()) {
                ListToReturn.add(getMovieCategory(rs));
            }
            result = true;
        } catch (SQLException ex) {
            result = false;
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return ListToReturn;
    }

    @Override
    public boolean addEntity(MovieCategory entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = (PreparedStatement) conn.prepareStatement(INSERT_TABLE);

            statement.setString(1, entity.getName());
            statement.setInt(1, entity.getId());
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
    public boolean update(MovieCategory entity) {

        Connection conn = null;
        boolean result = false;

        try {
            conn = DBHelper.getConnection();
            java.sql.PreparedStatement statement = conn.prepareStatement(UPDATE_MOVIECATAGORY);

            statement.setString(1, entity.getName());
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
    }

    @Override
    public void delete(MovieCategory entity) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = (PreparedStatement) conn.prepareStatement(DELET_CATEGORY);
            statement.setString(1, entity.getName());
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
