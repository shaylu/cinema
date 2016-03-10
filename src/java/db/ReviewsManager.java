/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import static db.PromosManager.SELECT_ALL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Review;

/**
 *
 * @author shay.lugasi
 */
public class ReviewsManager extends DbManagerEntity {

    private final static String INSERT_QUERY = "INSERT INTO reviews (order_id, rank, review_text,"
            + " review_date) values(?,?,?,?)";
    public final static String SELECT_ALLREVIWES = "SELECT * FROM reviews R inner join orders O on R.order_id = O.id ";
    public final static String SELECT_REVIEW = "SELECT * FROM reviews WHERE rev_id = ?";
    public final static String SELECT_BY_MOVIE = "SELECT * FROM reviews R INNER JOIN orders O ON R.order_id = O.order_id INNER JOIN shows ON O.show_id = shows.show_id WHERE shows.movie_id=?";
    public final static String SELECT_BY_ORDER_ID = "SELECT * FROM reviews WHERE order_id = ?";

    public ReviewsManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(int order_id, double rank, String review_text) throws ClassNotFoundException, SQLException {
        // dont forget to set the review date using the now() function
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            statement.setInt(1, order_id);
            statement.setDouble(2, rank);
            statement.setString(3, review_text);
            statement.setDate(4, getCurrentDate());
            return statement.executeUpdate();
        }
    }

    private static Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }

    public boolean isReviewExistForOrder(int order_id) throws SQLException, ClassNotFoundException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_ORDER_ID);
            statement.setInt(1, order_id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public int addDefaultValues() throws ClassNotFoundException, SQLException {
        int result = 0;
        //int order_id, int rank, String review_text
        result += add(1, 5, "Awesome movie! warmly recommended!");
        result += add(2, 2, "Very Good");
        result += add(3, 1, "I've seen better...");
        result += add(4, 4, "It is highly recommended, thrilling, exciting, WOW!");
        result += add(6, 5, "Amazing, WOW!");
        result += add(5, 4, "Hilarious! LOL!!");
        result += add(7, 4, "It is highly recommended!");

        return result;
    }
//TODO

    public Review getReviewById(int id) throws SQLException, ClassNotFoundException {
        Review reviewToReturn;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_REVIEW);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            reviewToReturn = createReviewFromMySql(rs);
        }
        return reviewToReturn;
    }
//TODO

    public List<Review> getAll() throws ClassNotFoundException, SQLException {

        ArrayList<Review> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL);
            while (rs.next()) {
                Review mc = createReviewFromMySql(rs);
                result.add(mc);
            }
        }

        return result;
    }

    private Review createReviewFromMySql(ResultSet rs) throws SQLException, ClassNotFoundException {

        Review result = new Review();
        result.setId(rs.getInt("R.rev_id"));
        result.setOrder(manager.getOrdersManager().getOrderById(rs.getInt("O.order_id")));
        result.setRank(rs.getDouble("R.rank"));
        result.setText(rs.getString("R.review_text"));
        result.setDate(rs.getDate("R.review_date"));
        return result;
    }

    public List<Review> getByMovieId(int movie_id) throws ClassNotFoundException, SQLException {
        ArrayList<Review> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_MOVIE);
            statement.setInt(1, movie_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Review mc = createReviewFromMySql(rs);
                result.add(mc);
            }
        }
        return result;
    }

}
