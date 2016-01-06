/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import static db.mysql.MovieCategoriesManager.INSERT_QUERY;
import static db.mysql.MovieCategoriesManager.SELECT_ALL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Hall;
import models.MovieCategory;

/**
 *
 * @author shay.lugasi
 */
public class HallsManager extends DbManagerEntity {

    public final static String INSERT_OUERY = "INSERT INTO hall  (num_of_seats) values(?)";
    public final static String DELET_HALL = "DELET from hall WHERE hall_id = (?)";
    public final static String UPDATE_HALL = "UPDATE HALL SET  num_of_seats = ? WHERE hall_id = ?";
    public final static String SELECT_ALL_HALLS = "SELECT * FROM hall";

    public HallsManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(int numOfSeats) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            statement.setInt(1, numOfSeats);
            return statement.executeUpdate();
        }
    }

    public int addDefaultValues() throws ClassNotFoundException, SQLException {
        int result = 0;
        result += add(30);
        result += add(110);
        result += add(70);
        result += add(50);
        return result;
    }

    public List<Hall> getAll() throws ClassNotFoundException, SQLException {

        ArrayList<Hall> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL);
            while (rs.next()) {
                Hall mc = createHallFromMySql(rs);
                result.add(mc);
            }
        }

        return result;
    }

    private Hall createHallFromMySql(ResultSet rs) throws SQLException {
        Hall result = new Hall();
        result.setId(rs.getInt("Hall_id"));
        result.setNumOfSeats(rs.getInt("num_of_seats"));
        return result;
    }
}
