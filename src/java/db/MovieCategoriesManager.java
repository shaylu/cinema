/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    public static final String SELECT_MOVIECAT_BY_NAME = "SELECT * FROM movie_categories WHERE name = (?)";
    public static final String REDIS_KEY = "allMovieCategories";
    Jedis jdisMovieCat;

    public MovieCategoriesManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(String name) throws ClassNotFoundException, SQLException {
        this.jdisMovieCat = new Jedis("localhost");
        int result = 0;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            statement.setString(1, name);
            result = statement.executeUpdate();
            MovieCategory movieCat = getMovieCategoryByName(name);
            jdisMovieCat.sadd(REDIS_KEY, movieCat.toRedisJson());
            return result;
        } finally {
            jdisMovieCat.disconnect();
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
//TODO

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

    public List<MovieCategory> getAllFromRedis() throws ClassNotFoundException, SQLException {
        this.jdisMovieCat = new Jedis("localhost");
        ArrayList<MovieCategory> allMovieCat = new ArrayList<>();
        try {
            Gson gson = new Gson();
            Set<String> allValues = jdisMovieCat.smembers(REDIS_KEY);

            for (String value : allValues) {
                MovieCategory catToAdd = gson.fromJson(value, MovieCategory.class);
                allMovieCat.add(catToAdd);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage() + "Redis all value fild");
        } finally {
            jdisMovieCat.disconnect();
        }

        return allMovieCat;
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

    public MovieCategory getMovieCategoryByName(String name) throws SQLException, ClassNotFoundException {
        MovieCategory result;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_MOVIECAT_BY_NAME);
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
    
     public void deletKeyFromRedis() {
        this.jdisMovieCat.del(REDIS_KEY);
    }
}
