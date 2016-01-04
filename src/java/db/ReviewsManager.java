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
import models.Review;

/**
 *
 * @author Liraz
 */
public class ReviewsManager implements DBEntityManager<Review> {

    private static final Logger LOGGER = Logger.getLogger(ReviewsManager.class.getName());
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS reviews (\n"
            + "  rev_id INT NOT NULL AUTO_INCREMENT,\n"
            + "  order_id INT NOT NULL,\n"
            + "  rank DOUBLE ZEROFILL NULL,\n"
            + "  review_text VARCHAR(500) NULL,\n"
            + "  review_date DATE NOT NULL,\n"
            + "  PRIMARY KEY (rev_id), INDEX order_id_idx (order_id ASC),\n"
            + "  CONSTRAINT order_id FOREIGN KEY (order_id) REFERENCES orders (Order_id))";
    private final static String INSERT_TABLE = "INSERT INTO reviews (rev_id, order_id, rank, review_text,"
            + " review_date) values(?,?,?,?,?)";
    private final static String DELET_REVIEW = "DELET from reviews WHERE rev_id = (?)";
    private final static String UPDATE_REVIWE = "UPDATE reviews SET order_id = ?, rank = ?, review_text = ?,"
            + " review_date = ? WHERE rev_id = ?";
    private final static String SELECT_ALLREVIWES = "SELECT * FROM cinema_city.reviews";
    
    @Override
    public void createTable() {
        DBHelper.executeUpdateStatment(CREATE_TABLE);
    }

    @Override
    public boolean addEntity(Review entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_TABLE);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("dd-MM-yyyy");

            statement.setInt(1, entity.getId());
            statement.setInt(2, (entity.getOrder().getId()));
            statement.setDouble(3, entity.getRank());
            statement.setString(4, entity.getText());
            statement.setString(5, dateformatSql.format(entity.getDate()));
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
    public boolean update(Review entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Review entity) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBHelper.getConnection();
            com.mysql.jdbc.PreparedStatement statement = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(DELET_REVIEW);
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
