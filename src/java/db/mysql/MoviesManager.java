/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import com.mysql.jdbc.exceptions.MySQLDataException;
import static db.MovieCategoryManager.getMovieCategoryByName;
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
import models.Movie;
import models.MovieCategory;

/**
 *
 * @author shay.lugasi
 */
public class MoviesManager extends DbManagerEntity {

    public static final String INSERT_QUERY = "INSERT INTO movies (name, release_date, mov_length, cat_id,"
            + " plot, poster,trailer,is_recommended) values(?,?,?,?,?,?,?,?)";
    public static final String SELECT_ALL = "SELECT * FROM movies M inner join movie_categories C on M.cat_id = C.cat_id ";
    public static final String SELECT_MOVIE_BY_ID = "";
    public static final String GET_MOVIE_QUERY = "SELECT * FROM movies WHERE movie_id = ?";
    public final static String DELET_MOVIE = "DELETE from movies WHERE movie_id = (?)";
    public final static String FILTER_QUERY_HASTRAILER_CAT = "SELECT * FROM movies where cat_id = ? and trailer != null and name like '%?'  ";
    public final static String FILTER_QUERY_HASNOTTRAILER_CAT = "SELECT * FROM movies where cat_id = ? and trailer = null and name like '%?'  ";
    public final static String FILTER_QUERY_HASTRAILER = "SELECT * FROM movies where trailer != null and name like '%?'  ";
    public final static String FILTER_QUERY_HASNOTTRAILER = "SELECT * FROM movies where trailer = null and name like '%?'  ";
    public final static String RECOMMENDED_QUERY = "SELECT * FROM movies WHERE is_recommended = true;";

    public enum ShowTime {

        DONT_CARE, MORNING, NOON, EVENING, NEXT_3_HOURS;
    }

    public MoviesManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(String name, Date release_date, int mov_length, int cat_id, String plot, String poster_url, String trailer_url, boolean is_recommended) throws SQLException, ClassNotFoundException {
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

            return statement.executeUpdate();
        }
    }

    public Movie getMovieById(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_MOVIE_QUERY);
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
        result.setMovie_length(rs.getInt("M.mov_length"));

        int cat_id = rs.getInt("C.cat_id");
        MovieCategory category = manager.getMovieCategoriesManager().getMovieCategoryById(cat_id);
        result.setCategory(category);

        result.setPlot(rs.getString("M.plot"));
        result.setPoster(rs.getString("M.poster"));
        result.setTrailer(rs.getString("M.trailer"));
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
    // if cat_id == 0 then it dosen't matter what category
    // i created a view named next_three_hours that selects the movie ids that shows up the next 3 hours 

    public List<Movie> getAllByFilter(String keyword, int cat_id, boolean has_trailer, boolean is_recommended) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = null;
        try (Connection conn = manager.getConnection()) {

            if (cat_id != 0 && has_trailer) {
                statement = conn.prepareStatement(FILTER_QUERY_HASTRAILER_CAT);
                statement.setInt(1, cat_id);
                statement.setBoolean(2, is_recommended);
                statement.setString(3, keyword);
            } else if (cat_id != 0 && !has_trailer) {
                statement = conn.prepareStatement(FILTER_QUERY_HASNOTTRAILER_CAT);
                statement.setInt(1, cat_id);
                statement.setBoolean(2, is_recommended);
                statement.setString(3, keyword);
            } else if (cat_id == 0 && has_trailer) {
                statement = conn.prepareStatement(FILTER_QUERY_HASTRAILER);
                statement.setBoolean(1, is_recommended);
                statement.setString(2, keyword);
            } else if (cat_id == 0 && !has_trailer) {
                statement = conn.prepareStatement(FILTER_QUERY_HASNOTTRAILER);
                statement.setBoolean(1, is_recommended);
                statement.setString(2, keyword);
            }
        }

        ArrayList<Movie> result = new ArrayList<>();
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Movie movie = createMovieFromMySql(rs);
            result.add(movie);
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
        MovieCategory category = getMovieCategoryByName("Action");

        result += add("Star Wars: The Force Awakens", release_date, 131, 1, plot, posterLink, trailer, true);

        //Creating Krampus
        release_date = formatter.parse("2016-04-23");
        plot = "A boy who has a bad Christmas ends up accidentally summoning a Christmas demon to his family home.";
        posterLink = "http://ia.media-imdb.com/images/M/MV5BMjk0MjMzMTI3NV5BMl5BanBnXkFtZTgwODEyODkxNzE@._V1_UX182_CR0,0,182,268_AL_.jpg";
        trailer = "https://www.youtube.com/watch?v=h6cVyoMH4QE";
        category = getMovieCategoryByName("Comedy");

        result += add("Krampus", release_date, 98, 2, plot, posterLink, trailer, true);

        return result;

    }
}
