/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import static db.mysql.PromosManager.SELECT_ALL;
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
    public final static String SELECT_ALLREVIWES = "SELECT * FROM reviews R inner join orders O on 'R.order_id = O.id' ";
    public final static String SELECT_REVIEW = "SELECT * FROM reviews WHERE rev_id = ?";

    public ReviewsManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(int order_id, int rank, String review_text) throws ClassNotFoundException, SQLException {
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

    public int addDefaultValues() throws ClassNotFoundException, SQLException {
        int result = 0;
        // result += add();
        //result += add();
        //result += add();
        //result += add();
        return result;
    }

    public Review getReviewById(int id) throws SQLException, ClassNotFoundException {
        Review reviewToReturn;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_REVIEW);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery(SELECT_REVIEW);
            reviewToReturn = createReviewFromMySql(rs);
        }
        return reviewToReturn;
    }

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

    private Review createReviewFromMySql(ResultSet rs) throws SQLException {

        Review result = new Review();
        result.setId(rs.getInt("rev_id"));
        result.setOrder(manager.getOrdersManager().getOrderById(rs.getInt("order_id")));
        result.setRank(rs.getDouble("rank"));
        result.setText(rs.getString("review_text"));
        result.setDate(rs.getDate("review_date"));
        return result;
    }

}
