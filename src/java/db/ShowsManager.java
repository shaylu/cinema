/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Show;

/**
 *
 * @author Liraz
 */
public class ShowsManager implements DBEntityManager<Show> {

    private static final Logger LOGGER = Logger.getLogger(ShowsManager.class.getName());
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS shows(\n"
            + "  show_id INT NOT NULL ,\n"
            + "  movie_id INT NOT NULL,\n"
            + "  hall_id INT NOT NULL,\n"
            + "  show_date DATE NOT NULL,\n"
            + "  num_of_seats_left INT ZEROFILL NOT NULL,\n"
            + "  price_per_seat DOUBLE ZEROFILL NOT NULL,\n"
            + "  PRIMARY KEY (show_id),\n"
            + "  INDEX movie_id_idx (movie_id ASC),\n"
            + "  INDEX hall_id_idx (hall_id ASC),\n"
            + "  CONSTRAINT movie_id FOREIGN KEY (movie_id) REFERENCES movies (movie_id),\n"
            + "  CONSTRAINT hall_id FOREIGN KEY (hall_id) REFERENCES hall (hall_id)\n)";

    private final static String INSERT_TABLE = "INSERT INTO shows (show_id, mov_id, hall_id, show_date,"
            + " num_of_seats_left, price_per_seat ) values(?,?,?,?,?,?)";
    private final static String DELET_SHOW = "DELET from shows WHERE show_id = (?)";
    private final static String UPDATE_SHOW = "UPDATE shows SET  mov_id = ?, hall_id = ?, show_date = ?,"
            + " num_of_seats_left = ?, price_per_seat = ? WHERE show_id = ?";
    private final static String SELECT_ALL_SHOWS = "select * from (shows S inner join hall H on H.hall_id=S.hall_id) "
            + "inner join movies M on S.movie_id = M.movie_id";

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
    public boolean update(Show entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(UPDATE_SHOW);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("dd-MM-yyyy");

            statement.setInt(1, (entity.getMovie().getId()));
            statement.setInt(2, entity.getHall().getId());
            statement.setString(3, dateformatSql.format(entity.getShowDate()));
            statement.setInt(4, entity.getNumOfSeatsLeft());
            statement.setDouble(5, entity.getPricePerSeat());
            statement.setInt(6, entity.getId());
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
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public ArrayList<Show> allShows() {
        ArrayList<Show> allShows = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = DBHelper.executeQueryStatment(SELECT_ALL_SHOWS);
            while (rs.next()) {
                Show show = getShowByResultSetLine(rs);
                allShows.add(show);
            }
        } catch (Exception e) {
        }
        return allShows;
    }

    public static Show getShowByResultSetLine(ResultSet rs) {
        Show show = new Show();
        try {
            show.setId(rs.getInt("show_id"));
            show.setMovie(MovieManager.getMovieByResultSetLine(rs));
            show.setHall(HallManager.getHallByResultSetLine(rs));
            show.setDate(rs.getDate("show_date"));
            show.setNumOfSeatsLeft(rs.getInt("num_of_seats_left"));
            show.setPricePerSeat(rs.getDouble("price_per_seat"));
        } catch (Exception e) {
        }
        return show;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
