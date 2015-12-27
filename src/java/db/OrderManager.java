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
import models.Order;

/**
 *
 * @author efrat
 */
public class OrderManager implements DBEntityManager<Order> {

    private static final Logger LOGGER = Logger.getLogger(MovieCategoryManager.class.getName());
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS orders  ( order_id INT AUTO_INCREMENT,\n"
            + "  client_id VARCHAR(9) NOT NULL,\n"
            + "  fname VARCHAR(45) NOT NULL,\n"
            + "  lname VARCHAR(45) NOT NULL,\n"
            + "  email VARCHAR(45) NOT NULL,\n"
            + "  phone VARCHAR(45) NOT NULL,\n"
            + "  show_id INT NOT NULL,\n"
            + "  num_of_seats INT ZEROFILL NOT NULL,\n"
            + "  total_payment DOUBLE ZEROFILL NOT NULL,\n"
            + "  credit_card_last_digit VARCHAR(4) NOT NULL,\n"
            + "  exp_date_month INT NOT NULL,\n"
            + "  exp_date_year INT NOT NULL,\n"
            + "  order_date DATE NOT NULL,\n"
            + "  PRIMARY KEY (order_id),\n"
            + "  INDEX show_id_idx (show_id ASC),\n"
            + "  CONSTRAINT show_id\n"
            + "    FOREIGN KEY (show_id)\n"
            + "    REFERENCES shows (show_id)\n"
            + "    ON DELETE NO ACTION\n"
            + "    ON UPDATE NO ACTION)";

    private final static String INSERT_TABLE = "INSERT INTO orders (client_id, fname, lname, email, phone, show_id, num_of_seats,"
            + "total_payment, credit_card_last_digit, exp_date_month, exp_date_year, order_date) values(?,?,?,?,?,?,?,?,?,?,?,?)";
    private final static String DELET_ORDER = "DELET from orders WHERE order_id = (?)";

    @Override
    public void createTable() {
        DBHelper.executeUpdateStatment(CREATE_TABLE);
    }

    @Override
    public boolean addEntity(Order entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_TABLE);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("dd-MM-yyyy");

            statement.setString(1, entity.getClientId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getPhoneNumber());
            statement.setInt(6, entity.getShowId());
            statement.setInt(7, entity.getNumOfSeats());
            statement.setDouble(8, entity.getTotalPayment());
            statement.setString(9, entity.getCreditCardLastDigit());
            statement.setInt(10, entity.getExpDateMonth());
            statement.setInt(11, entity.getExpDateYear());
            statement.setString(12, dateformatSql.format(entity.getOrderDate()));

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
    public void update(Order entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Order entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
