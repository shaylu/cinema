/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import static db.mysql.MovieCategoriesManager.INSERT_QUERY;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Movie;

/**
 *
 * @author shay.lugasi
 */
public class MoviesManager extends DbManagerEntity {

    public static final String INSERT_QUERY = "INSERT INTO movies (name, realse_date, mov_length, cat_id,"
            + " plot, poster,trailer,is_recommended) values(?,?,?,?,?,?,?,?)";
    public static final String SELECT_ALL = "SELECT * FROM movies M inner join movies_categories C on M.cat_id = C.cat_id ";
    public static final String SELECT_MOVIE_BY_ID = "";
    public static final String GET_MOVIE_QUERY = "SELECT * FROM movies WHERE movie_id = ?";
    private final static String DELET_MOVIE = "DELETE from movies WHERE movie_id = (?)";

    public enum ShowTime {

        DONT_CARE, MORNING, NOON, EVENING, NEXT_3_HOURS;
    }

    public MoviesManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(String name, Date release_date, int mov_length, int cat_id, String plot, String poster_url, String trailer_url, boolean is_recommended) throws SQLException, ClassNotFoundException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("dd-MM-yyyy");

            statement.setString(1, name);
            statement.setString(2, dateformatSql.format(release_date));
            statement.setDouble(3, mov_length);
            statement.setInt(4, cat_id);
            statement.setString(5, plot);
            statement.setString(6, poster_url);
            statement.setString(7, trailer_url);
            statement.setBoolean(8, is_recommended);

            return statement.executeUpdate();
        }
    }

    public Movie getMovie(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_MOVIE_QUERY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery(GET_MOVIE_QUERY);
            Movie result = createMovieFromMySql(rs);
            return result;
        }
    }

    public List<Movie> getAll() {
        throw new UnsupportedOperationException();
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

    public List<Movie> getRecommended() throws SQLException, ClassNotFoundException {
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

    // if cat_id == 0 then it dosen't matter what category
    // i created a view named next_three_hours that selects the movie ids that shows up the next 3 hours 
    public List<Movie> getAllByFilter(String keyword, int cat_id, boolean has_trailer, boolean is_recommended, ShowTime show_time) {
        throw new UnsupportedOperationException();
    }

    public Movie createMovieFromMySql(ResultSet rs) throws SQLException {
        Movie result = new Movie();
        result.setId(rs.getInt("movie_id"));
        result.setName(rs.getString("M.name"));
        result.setRelease_date(rs.getDate("release_date"));
        result.setName(rs.getString("mov_length"));
        result.setCategory(manager.getMovieCategoriesManager().createMovieCtaegoryFromMySql(rs));
        result.setPlot(rs.getString("plot"));
        result.setPoster(rs.getString("poster"));
        result.setTrailer(rs.getString("trailer"));
        result.setIs_recomanded(rs.getBoolean("is_recommended"));

        return result;
    }
}
