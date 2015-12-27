/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Show;

/**
 *
 * @author Liraz
 */
public class ShowsManager implements DBEntityManager<Show> {

    private static final Logger LOGGER = Logger.getLogger(ShowsManager.class.getName());
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS shows(\n" +
"  show_id INT NOT NULL ,\n" +
"  movie_id INT NOT NULL,\n" +
"  hall_id INT NOT NULL,\n" +
"  show_date DATE NOT NULL,\n" +
"  num_of_seats_left INT ZEROFILL NOT NULL,\n" +
"  price_per_seat DOUBLE ZEROFILL NOT NULL,\n" +
"  PRIMARY KEY (show_id),\n" +
"  INDEX movie_id_idx (movie_id ASC),\n" +
"  INDEX hall_id_idx (hall_id ASC),\n" +
"  CONSTRAINT movie_id FOREIGN KEY (movie_id) REFERENCES movies (movie_id),\n" +
"  CONSTRAINT hall_id FOREIGN KEY (hall_id) REFERENCES hall (hall_id)\n)";
    
    private final static String INSERT_TABLE = "INSERT INTO shows (show_id, mov_id, hall_id, show_date,"
            + " num_of_seats_left, price_per_seat ) values(?,?,?,?,?,?)";
    private final static String DELET_SHOW = "DELET from shows WHERE show_id = (?)";

    @Override
    public void createTable() {
        DBHelper.executeUpdateStatment(CREATE_TABLE);
    }

    @Override
    public boolean addEntity(Show entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_TABLE);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("dd-MM-yyyy");

            statement.setInt(1, entity.getId());
            statement.setInt(2, (entity.getMovie().getId()));
            statement.setInt(3, entity.getHall().getId());
            statement.setString(4, dateformatSql.format(entity.getShowDate()));
            statement.setInt(5, entity.getNumOfSeatsLeft());
            statement.setDouble(6, entity.getPricePerSeat());            
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
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Show entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Show entity) {
               Connection conn = null;
        boolean result = false;
        try {
            conn = DBHelper.getConnection();
            com.mysql.jdbc.PreparedStatement statement = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(DELET_SHOW);
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
