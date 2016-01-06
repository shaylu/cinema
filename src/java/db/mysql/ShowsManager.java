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
import java.util.Date;
import java.util.List;
import models.Hall;
import models.Movie;
import models.Show;

/**
 *
 * @author shay.lugasi
 */
public class ShowsManager extends DbManagerEntity {

    public static final String INSERT_QUERY = "INSERT INTO shows (movie_id, hall_id, show_date, num_of_seats_left, price_per_seat) values(?,?,?,?,?);";
    public static final String SUBSTRUCT = "UPDATE shows SET num_of_seats_left = (num_of_seats_left - (?)) WHERE show_id = (?);";
    public static final String GET_BY_ID = "SELECT TOP 1 * FROM shows WHERE show_id = (?);";
    public static final String GET_ALL = "SELECT * FROM shows;";
    public static final String GET_BY_HALL = "SELECT * FROM shows WHERE hall_id = (?);";
    public static final String GET_BY_LAST_TICKETS = "SELECT * FROM shows WHERE num_of_seats_left < (?);";
    public static final String GET_BY_MOVIE = "SELECT * FROM shows WHERE movie_id = (?);";

    public ShowsManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(int movie_id, int hall_id, int num_of_seats_left, Date show_date, double price_per_seat) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            statement.setInt(1, movie_id);
            statement.setInt(2, hall_id);
            statement.setInt(3, num_of_seats_left);
            statement.setDate(4, new java.sql.Date(show_date.getTime()));
            statement.setDouble(5, price_per_seat);

            return statement.executeUpdate();
        }
    }

    public int substructTickets(int show_id, int num_of_tickets) throws ClassNotFoundException, SQLException {
        // when ordering a ticket to show, this function will be called.
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SUBSTRUCT);
            statement.setInt(1, num_of_tickets);
            statement.setInt(2, show_id);

            return statement.executeUpdate();
        }
    }

    public Show getShow(int id) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_BY_ID);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            rs.next();
            Show result = createShowFromMySql(rs);
            return result;
        }
    }

    public Show getShowByHall(int hall_id) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_BY_HALL);
            statement.setInt(1, hall_id);

            ResultSet rs = statement.executeQuery();
            rs.next();
            Show result = createShowFromMySql(rs);
            return result;
        }
    }

    public List<Show> getAllShows() throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL);
            
            ArrayList<Show> result = new ArrayList<>();
            
            while(rs.next()) {
                Show show = createShowFromMySql(rs);
                result.add(show);
            }
            
            return result;
        }
    }

    public List<Show> getAllShowsWithLastTickets(int tickets_less_than) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_BY_LAST_TICKETS);
            statement.setInt(1, tickets_less_than);

            ResultSet rs = statement.executeQuery();
            ArrayList<Show> result = new ArrayList<>();
            
            while(rs.next()) {
                Show show = createShowFromMySql(rs);
                result.add(show);
            }
            
            return result;
        }
    }

    public List<Show> getAllShowsForMovie(int movie_id) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_BY_MOVIE);
            statement.setInt(1, movie_id);

            ResultSet rs = statement.executeQuery();
            ArrayList<Show> result = new ArrayList<>();
            
            while(rs.next()) {
                Show show = createShowFromMySql(rs);
                result.add(show);
            }
            
            return result;
        }
    }
    
    public Show createShowFromMySql(ResultSet rs) throws SQLException, ClassNotFoundException {
        int movie_id = rs.getInt("movie_id");
        int hall_id = rs.getInt("hall_id");
        
        Movie movie = manager.getMoviesManager().getMovie(movie_id);
        Hall hall = manager.getHallsManager().get(hall_id);

        Show result = new Show();
        result.setId(rs.getInt("show_id"));
        result.setMovie(movie);
        result.setHall(hall);
        result.setDate(rs.getDate("show_date"));
        result.setNumOfSeatsLeft(rs.getInt("num_of_seats_left"));
        result.setPricePerSeat(rs.getDouble("price_per_seat"));
        return result;
    }

}
