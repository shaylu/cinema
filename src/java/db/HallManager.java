/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    
}
