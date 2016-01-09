/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import com.google.gson.Gson;
import com.mysql.jdbc.exceptions.MySQLDataException;
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
import java.util.Set;
import models.Movie;
import models.MovieCategory;
import org.apache.jasper.tagplugins.jstl.core.Catch;
import redis.clients.jedis.Jedis;

/**
 *
 * @author shay.lugasi
 */
public class MoviesManager extends DbManagerEntity {

    public static final String INSERT_QUERY = "INSERT INTO movies (name, release_date, mov_length, cat_id,"
            + "plot, poster,trailer,is_recommended) values(?,?,?,?,?,?,?,?)";
    public static final String SELECT_ALL = "SELECT * FROM movies M inner join movie_categories C on M.cat_id = C.cat_id ";
    public static final String SELECT_MOVIE_BY_ID = "SELECT * FROM movies M WHERE M.movie_id = ?";
    public static final String SELECT_MOVIE_BY_KEYWORD = "SELECT * FROM movies M WHERE M.name like ?";
    public final static String DELET_MOVIE = "DELETE from movies M WHERE movie_id = (?)";
    public final static String FILTER_QUERY_HASTRAILER_CAT = "SELECT * FROM movies M where M.cat_id = ? and M.trailer = ? and M.name like ?  ";
    public final static String FILTER_QUERY_HASNOTTRAILER_CAT = "SELECT * FROM movies M where M.cat_id = ? and M.trailer = null and M.name like ? ";
    public final static String FILTER_QUERY_HASTRAILER = "SELECT * FROM movies M where M.trailer != null and M.name like ?  ";
    public final static String FILTER_QUERY_HASNOTTRAILER = "SELECT * FROM movies M where M.trailer = null and M.name like ?  ";
    public final static String RECOMMENDED_QUERY = "SELECT * FROM movies M WHERE M.is_recommended = true;";
    public static final String SELECT_MOVIE_BY_NAME = "SELECT * FROM movies M WHERE name = (?)";
    public final static String REDIS_KEY = "recommended";
    Jedis jdisMovie;

    public MoviesManager(DbManager manager) {
        this.manager = manager;
    }
//    public enum ShowTime {
//        DONT_CARE, MORNING, NOON, EVENING, NEXT_3_HOURS;
//    }

    public int add(String name, Date release_date, int mov_length, int cat_id, String plot, String poster_url, String trailer_url, boolean is_recommended) throws SQLException, ClassNotFoundException {
        this.jdisMovie = new Jedis("localhost");
        int result = 0;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);

            statement.setString(1, name);
            statement.setDate(2, new java.sql.Date(release_date.getTime()));
            statement.setDouble(3, mov_length);
            statement.setInt(4, cat_id);
            statement.setString(5, plot);
            statement.setString(6, poster_url);
            statement.setString(7, trailer_url);
            statement.setBoolean(8, is_recommended);
            result = statement.executeUpdate();

            if (is_recommended) {
                Movie movie = getMovieByName(name);
                String moviesJson = movie.toRedisRecommanded();
                jdisMovie.sadd(REDIS_KEY, moviesJson);
            }

            return result;
        } finally {
            this.jdisMovie.disconnect();
        }

    }

    public Movie getMovieById(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_MOVIE_BY_ID);
            //for check :id = 1;
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            Movie result = createMovieFromMySql(rs);
            return result;
        }
    }

    public Movie createMovieFromMySql(ResultSet rs) throws SQLException, ClassNotFoundException {

        Movie result = new Movie();
        result.setId(rs.getInt("M.movie_id"));
        result.setName(rs.getString("M.name"));
        result.setRelease_date(rs.getDate("M.release_date"));
        result.setMovie_length(rs.getDouble("M.mov_length"));
        result.setCategory(manager.getMovieCategoriesManager().getMovieCategoryById(rs.getInt("M.cat_id")));
        result.setPlot(rs.getString("M.plot"));
        result.setPoster(rs.getString("M.poster"));
        result.setIs_recomanded(rs.getBoolean("M.is_recommended"));
        return result;
    }

    public List<Movie> getAll() throws ClassNotFoundException, SQLException {
        ArrayList<Movie> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL);
            while (rs.next()) {
                Movie mc = createMovieFromMySql(rs);
                result.add(mc);
            }
        }
        return result;
    }

    public boolean delete(int mov_id) throws SQLException, ClassNotFoundException {
        boolean result = false;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELET_MOVIE);
            statement.setInt(1, mov_id);
            statement.executeUpdate(DELET_MOVIE);
            result = true;
        }
        return result;
    }

    public ArrayList<Movie> getRecommended() throws SQLException, ClassNotFoundException {

        ArrayList<Movie> moviesResult = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(RECOMMENDED_QUERY);
            while (rs.next()) {
                Movie mc = createMovieFromMySql(rs);
                moviesResult.add(mc);
            }
        }
        return moviesResult;
    }

    public String getRecommendedFromRedis() throws SQLException, ClassNotFoundException {
        this.jdisMovie = new Jedis("localhost");
        String str = this.jdisMovie.get(REDIS_KEY);
        this.jdisMovie.disconnect();
        return str;
    }

// if cat_id == 0 then it dosen't matter what category
    // i created a view named next_three_hours that selects the movie ids that shows up the next 3 hours 
    public List<Movie> getAllByFilter(String keyword, int cat_id, boolean has_trailer, boolean is_recommended) throws SQLException, ClassNotFoundException {

        ArrayList<Movie> result = new ArrayList<>();

        PreparedStatement statement = null;
        try (Connection conn = manager.getConnection()) {

            if (cat_id != 0 && has_trailer) {
                statement = conn.prepareStatement(FILTER_QUERY_HASTRAILER_CAT);
                statement.setInt(1, cat_id);
                statement.setBoolean(2, is_recommended);
                statement.setString(3, "%" + keyword);
            } else if (cat_id != 0 && !has_trailer) {
                statement = conn.prepareStatement(FILTER_QUERY_HASNOTTRAILER_CAT);
                statement.setInt(1, cat_id);
                //statement.setBoolean(2, is_recommended);
                statement.setString(3, "%" + keyword);
            } else if (cat_id == 0 && has_trailer) {
                statement = conn.prepareStatement(FILTER_QUERY_HASTRAILER);
                //statement.setBoolean(1, is_recommended);
                statement.setString(2, "%" + keyword);
            } else if (cat_id == 0 && !has_trailer) {
                statement = conn.prepareStatement(FILTER_QUERY_HASNOTTRAILER);
                //statement.setBoolean(1, is_recommended);
                statement.setString(2, "%" + keyword);
            }
            //NOT WORKING
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Movie movie = createMovieFromMySql(rs);
                result.add(movie);
            }
        }

        return result;
    }

    // public int add(String name, Date release_date, int mov_length, int cat_id, String plot, String poster_url, String trailer_url, boolean is_recommended)
    public int addDefaultValues() throws SQLException, ClassNotFoundException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        int result = 0;
        // Creating Star Wars: The Force Awakens
        Date release_date = formatter.parse("2016-05-01");
        String plot = "Three decades after the defeat of the Galactic Empire, a new threat arises. The First Order attempts to rule the galaxy and only a rag-tag group of heroes can stop them, along with the help of the Resistance.";
        String posterLink = "http://ia.media-imdb.com/images/M/MV5BOTAzODEzNDAzMl5BMl5BanBnXkFtZTgwMDU1MTgzNzE@._V1_UX182_CR0,0,182,268_AL_.jpg";
        String trailer = "https://www.youtube.com/watch?v=sGbxmsDFVnE";
        MovieCategory category = controllers.ControllerHelper.getDb().getMovieCategoriesManager().getMovieCategoryByName("Action");

        result += add("Star Wars: The Force Awakens", release_date, 131, category.getId(), plot, posterLink, trailer, true);

        //Creating Krampus
        release_date = formatter.parse("2016-04-23");
        plot = "A boy who has a bad Christmas ends up accidentally summoning a Christmas demon to his family home.";
        posterLink = "http://ia.media-imdb.com/images/M/MV5BMjk0MjMzMTI3NV5BMl5BanBnXkFtZTgwODEyODkxNzE@._V1_UX182_CR0,0,182,268_AL_.jpg";
        trailer = "https://www.youtube.com/watch?v=h6cVyoMH4QE";
        category = controllers.ControllerHelper.getDb().getMovieCategoriesManager().getMovieCategoryByName("Comedy");

        result += add("Krampus", release_date, 98, 2, plot, posterLink, trailer, true);

        return result;

    }

    public Movie getMovieByName(String name) throws SQLException, ClassNotFoundException {
        Movie result;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_MOVIE_BY_NAME);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            rs.next();
            result = createMovieFromMySql(rs);
        }
        return result;
    }
    public List<Movie> getByKeyword(String keyword) throws SQLException, ClassNotFoundException {
        ArrayList<Movie> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_MOVIE_BY_ID);
            statement.setString(1,"%" + keyword);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Movie mc = createMovieFromMySql(rs);
                result.add(mc);
            }
        }
        return result;
    }

}
