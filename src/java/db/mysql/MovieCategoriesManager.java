/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.MovieCategory;
import redis.clients.jedis.Jedis;

/**
 *
 * @author shay.lugasi
 */
public class MovieCategoriesManager extends DbManagerEntity {

    public static final String INSERT_QUERY = "INSERT INTO movie_categories (name) values(?)";
    public static final String SELECT_ALL = "SELECT * FROM movie_categories";
    public static final String SELECT_MOVIE_CATEGORY = "SELECT * FROM movie_categories WHERE cat_id = (?)";
    public static final String SELET_MOVIECAT_BY_NAME = "SELECT * FROM movie_categories WHERE name = (?)";

    Jedis jdisMovieCat;

    public MovieCategoriesManager(DbManager manager) {
        this.jdisMovieCat = new Jedis("localhost");
        this.manager = manager;
    }

    public int add(String name) throws ClassNotFoundException, SQLException {
        int result = 0;

        try (Connection conn = manager.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            statement.setString(1, name);
            result = statement.executeUpdate();

            MovieCategory movieCat = getMovieCategoryByName(name);
            jdisMovieCat.set(new Integer(movieCat.getId()).toString(), movieCat.toRedisJson());
            return result;
        }

    }

    public int addDefaultValues() throws ClassNotFoundException, SQLException {
        int result = 0;
        result += add("Action");
        result += add("Comedy");
        result += add("Drama");
        result += add("Sci-Fi");
        return result;
    }

    public List<MovieCategory> getAll() throws ClassNotFoundException, SQLException {

        ArrayList<MovieCategory> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL);
            while (rs.next()) {
                MovieCategory mc = createMovieCtaegoryFromMySql(rs);
                result.add(mc);
            }
        }

        return result;
    }

    public MovieCategory getMovieCategoryById(int id) throws SQLException, ClassNotFoundException {
        MovieCategory result;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_MOVIE_CATEGORY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            result = createMovieCtaegoryFromMySql(rs);
        }

        return result;
    }

    public MovieCategory getMovieCategoryRedisById(int id) {
        String jsonRes = jdisMovieCat.get(new Integer(id).toString());
        Gson gson = new Gson();
        MovieCategory ctegory = gson.fromJson(jsonRes, MovieCategory.class);
        return ctegory;
    }

    public MovieCategory getMovieCategoryByName(String name) throws SQLException, ClassNotFoundException {
        MovieCategory result;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELET_MOVIECAT_BY_NAME);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            rs.next();
            result = createMovieCtaegoryFromMySql(rs);
        }

        return result;
    }

    public MovieCategory createMovieCtaegoryFromMySql(ResultSet rs) throws SQLException {
        MovieCategory result = new MovieCategory();
        result.setId(rs.getInt("cat_id"));
        result.setName(rs.getString("name"));
        return result;
    }
}
