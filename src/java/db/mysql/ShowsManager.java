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
import java.sql.Timestamp;
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

    public static final String INSERT_QUERY = "INSERT INTO shows (movie_id, hall_id, show_date, show_time, num_of_seats_left, price_per_seat) values(?,?,?,?,?,?);";
    public static final String SUBSTRUCT = "UPDATE shows S SET num_of_seats_left = (num_of_seats_left - (?)) WHERE show_id = (?);";
    public static final String GET_BY_ID = "SELECT * FROM shows S WHERE show_id = (?) LIMIT 1;";
    public static final String GET_ALL = "SELECT * FROM shows S;";
    public static final String GET_BY_HALL = "SELECT * FROM shows S inner join halls H on S.hall_id = H.hall_id WHERE hall_id = (?);";
    public static final String GET_BY_LAST_TICKETS = "SELECT * FROM shows WHERE num_of_seats_left < (?);";
    public static final String GET_BY_MOVIE = "SELECT * FROM shows S inner join movies M on S.movie_id = M.movie_id  WHERE movie_id = (?);";

    public ShowsManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(int movie_id, int hall_id, int num_of_seats_left, String show_date, String time, double price_per_seat) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            statement.setInt(1, movie_id);
            statement.setInt(2, hall_id);
            statement.setString(3, show_date);
            statement.setString(4, time);
            statement.setInt(5, num_of_seats_left);
            statement.setDouble(6, price_per_seat);

            return statement.executeUpdate();
        }
    }

    public int addDefaultValues() throws ClassNotFoundException, SQLException {
        int result = 0;
        //   public int add(int movie_id, int hall_id, int num_of_seats_left, Date show_date, double price_per_seat) throws ClassNotFoundException, SQLException {
        result += add(1, 5, 100, "2016-01-02", "10:20", 25.5);
        result += add(2, 3, 50, "2016-01-15", "12:45", 29.5);
        result += add(1, 2, 35, "2016-02-20", "16:15", 22);
        result += add(2, 1, 100, "2016-02-29", "21:00", 35.5);
        return result;
    }

    public int substructTickets(int show_id, int num_of_tickets, Connection conn) throws ClassNotFoundException, SQLException {
        // when ordering a ticket to show, this function will be called.
        PreparedStatement statement = conn.prepareStatement(SUBSTRUCT);
        statement.setInt(1, num_of_tickets);
        statement.setInt(2, show_id);

        return statement.executeUpdate();
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

            while (rs.next()) {
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

            while (rs.next()) {
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

            while (rs.next()) {
                Show show = createShowFromMySql(rs);
                result.add(show);
            }

            return result;
        }
    }

    public Show createShowFromMySql(ResultSet rs) throws SQLException, ClassNotFoundException {

        Show showToReturn = new Show();

        showToReturn.setId(rs.getInt("S.show_id"));
        showToReturn.setMovie(manager.getMoviesManager().getMovieById(rs.getInt("S.movie_id")));
        showToReturn.setHall(manager.getHallsManager().getHallById(rs.getInt("S.hall_id")));
        showToReturn.setDate(rs.getDate("S.show_date"));
        showToReturn.setTime(rs.getString("S.show_time"));
        showToReturn.setNumOfSeatsLeft(rs.getInt("S.num_of_seats_left"));
        showToReturn.setPricePerSeat(rs.getDouble("S.price_per_seat"));

        return showToReturn;
    }

}
