/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Hall;

/**
 *
 * @author Liraz
 */
public class HallManager implements DBEntityManager<Hall> {

    private static final Logger LOGGER = Logger.getLogger(HallManager.class.getName());
    private final static String CREATE_TABLE = " CREATE TABLE IF NOT EXISTS hall (hall_id INT NOT NULL AUTO_INCREMENT, num_of_seats INT ZEROFILL NOT NULL, PRIMARY KEY (hall_id))";
    private final static String INSERT_TABLE = "INSERT INTO hall (hall_id, num_of_seats) values(?,?)";
    private final static String DELET_HALL = "DELET from hall WHERE hall_id = (?)";
   private final static String UPDATE_HALL = "UPDATE HALL SET  num_of_seats = ? WHERE hall_id = ?";
    private final static String SELECT_ALL_HALLS = "SELECT * FROM hall";

    @Override
    public void createTable() {
        DBHelper.executeUpdateStatment(CREATE_TABLE);
    }

    @Override
    public boolean addEntity(Hall entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = (PreparedStatement) conn.prepareStatement(INSERT_TABLE);

            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getNumOfSeats());
            statement.execute();
            result = true;
        } catch (ClassNotFoundException | SQLException ex) {
            result = false;
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
    }

    @Override
    public boolean update(Hall entity) {
  Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = (PreparedStatement) conn.prepareStatement(UPDATE_HALL);

            statement.setInt(1, entity.getNumOfSeats());
            statement.setInt(2,entity.getId());
            statement.executeUpdate();
            result = true;
        } catch (ClassNotFoundException | SQLException ex) {
            result = false;
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;      

//  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Hall entity) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBHelper.getConnection();
            java.sql.PreparedStatement statement = conn.prepareStatement(DELET_HALL);
            statement.setInt(1, entity.getId());
            statement.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            result = false;
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public ArrayList<Hall> allHalls() {
        ArrayList<Hall> allHalls = new ArrayList<Hall>();
        ResultSet rs = null;
        try {
            rs = DBHelper.executeQueryStatment(SELECT_ALL_HALLS);
            while (rs.next()) {
                Hall hall = getHallByResultSetLine(rs);
                allHalls.add(hall);
            }
        } catch (Exception e) {
        }
        return allHalls;
    }

    public static Hall getHallByResultSetLine(ResultSet rs) {
        Hall hall = new Hall();
        try {
            hall.setId(rs.getInt("hall_id"));
            hall.setNumOfSeats(rs.getInt("num_of_seats"));
        } catch (Exception e) {
        }
        return hall;
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
