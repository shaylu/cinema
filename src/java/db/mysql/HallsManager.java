/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Hall;

/**
 *
 * @author shay.lugasi
 */
public class HallsManager extends DbManagerEntity {

    public static final String INSERT_QUERY = "INSERT INTO halls (hall_id, num_of_seats) values(?,?);";
    public static final String GET_BY_ID = "SELECT * FROM halls WHERE hall_id = (?) LIMIT 1;";
    public static final String GET_ALL = "SELECT * FROM halls;";

    public HallsManager(DbManager manager) throws ClassNotFoundException, SQLException {
        this.manager = manager;
    }

    public int add(int id, int num_of_seats) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            statement.setInt(1, id);
            statement.setInt(2, num_of_seats);
            return statement.executeUpdate();
        }
    }

    public Hall getHallById(int id) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            Hall result = createHallFromMySql(rs);
            return result;
        }
    }

    public List<Hall> getaAll() throws ClassNotFoundException, SQLException {

        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL);
            List<Hall> result = new ArrayList<>();
            while (rs.next()) {
                result.add(createHallFromMySql(rs));
            }
            return result;
        }
    }

    public int addDefaultValues() throws ClassNotFoundException, SQLException {
        int result = 0;
        result += add(1, 100);
        result += add(2, 35);
        result += add(3, 50);
        result += add(4, 80);
        result += add(5, 100);
        return result;
    }

    public Hall createHallFromMySql(ResultSet rs) throws SQLException {
        Hall result = new Hall();
        result.setId(rs.getInt("hall_id"));
        result.setNumOfSeats(rs.getInt("num_of_seats"));
        return result;
    }
}
