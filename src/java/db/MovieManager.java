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
import models.Movie;

/**
 *
 * @author Dell
 */
public class MovieManager implements DBEntityManager<Movie> {

    private static final Logger LOGGER = Logger.getLogger(MovieManager.class.getName());
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS movies (movie_id INT AUTO_INCREMENT ,\n"
            + "  name VARCHAR(45) NOT NULL,\n"
            + "  realse_date DATE NULL,\n"
            + "  mov_length DOUBLE ZEROFILL NULL,\n"
            + "  cat_id INT NOT NULL,\n"
            + "  plot VARCHAR(255) NULL,\n"
            + "  poster VARCHAR(255) NULL,\n"
            + "  trailer VARCHAR(255) NULL,\n"
            + "  is_recommended TINYINT(1) NOT NULL,\n"
            + "  PRIMARY KEY (movie_id),\n"
            + "  INDEX cat_id_idx (cat_id ASC),\n"
            + "  CONSTRAINT cat_id FOREIGN KEY (cat_id) REFERENCES movies_categories (cat_id))";

    private final static String INSERT_TABLE = "INSERT INTO movies (name, realse_date, mov_length, cat_id, plot, poster,trailer,is_recommended) values(?,?,?,?,?,?,?,?)";
    private final static String DELET_MOVIE = "DELET from movies WHERE name = (?)";

    @Override
    public void createTable() {
        DBHelper.executeUpdateStatment(CREATE_TABLE);

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addEntity(Movie entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_TABLE);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("dd-MM-yyyy");

            statement.setString(1, entity.getName());
            statement.setString(2, dateformatSql.format(entity.getRelease_date()));
            statement.setInt(3, (entity.getMovie_length()));
            statement.setInt(4, entity.getCategory().getId());
            statement.setString(5, entity.getPlot());
            statement.setString(6, entity.getPoster());
            statement.setString(7, entity.getTrailer());
            statement.setString(8, Boolean.toString(entity.is_recomanded()));
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
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Movie entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Movie entity) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(DELET_MOVIE);
            statement.setString(1, entity.getName());
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

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
