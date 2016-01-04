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
import models.Order;

/**
 *
 * @author efrat
 */
public class OrderManager implements DBEntityManager<Order> {

    private static final Logger LOGGER = Logger.getLogger(MovieCategoryManager.class.getName());
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS orders(order_id INT AUTO_INCREMENT,\n"
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
    private final static String UPDATE_ORDER = "UPDATE orders SET client_id = ?, fname = ? , lname = ? , email = ?,"
            + " phone = ? , show_id = ?, num_of_seats = ? ,total_payment = ?, credit_card_last_digit = ?,"
            + " exp_date_month = ?, exp_date_year = ?, order_date = ? WHERE order_id = ?";
    private final static String SELECT_ALL_ORDER = "SELECT * FROM orders O inner join shows S on 'O.show_id = S.id' ";

    public static String getCREATE_TABLE() {
        return CREATE_TABLE;
    }

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
    public boolean update(Order entity) {
        Connection conn = null;
        boolean result = false;

        try {
            conn = DBHelper.getConnection();
            java.sql.PreparedStatement statement = conn.prepareStatement(UPDATE_ORDER);
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
    }

    @Override
    public void delete(Order entity) {

        Connection conn = null;
        boolean result = false;
        try {
            conn = DBHelper.getConnection();
            com.mysql.jdbc.PreparedStatement statement = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(DELET_ORDER);
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

    public static Order getOrderByResultSet(ResultSet rs) throws SQLException {
        Order OrderToReturn = new Order();

        OrderToReturn.setClientId(rs.getString("client_id"));
        OrderToReturn.setFirstName(rs.getString("fname"));
        OrderToReturn.setLastName(rs.getString("lname"));
        OrderToReturn.setEmail(rs.getString("email"));
        OrderToReturn.setPhoneNumber(rs.getString("phone"));
        OrderToReturn.setShow(ShowsManager.getShowByResultSetLine(rs));
        OrderToReturn.setNumOfSeats(rs.getInt("num_of_seats"));
        OrderToReturn.setTotalPayment(rs.getDouble("total_payment"));
        OrderToReturn.setCreditCardLastDigit(rs.getString("credit_card_last_digit"));
        OrderToReturn.setExpDateMonth(rs.getInt("exp_date_month"));
        OrderToReturn.setExpDateYear(rs.getInt("exp_date_year"));
        OrderToReturn.setOrderDate(rs.getDate("order_date"));

        OrderToReturn.setOrderDate(rs.getDate("order_date"));
        return OrderToReturn;
    }

    public ArrayList<Order> getAllOrders() {

        ArrayList<Order> ListToReturn = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = DBHelper.executeQueryStatment(SELECT_ALL_ORDER);
            while (rs.next()) {
                ListToReturn.add(getOrderByResultSet(rs));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return ListToReturn;
    }
}
