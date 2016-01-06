/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import static db.OrderManager.getOrderByResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Order;

/**
 *
 * @author shay.lugasi
 */
public class OrdersManager extends DbManagerEntity {

    public static final String INSERT_QUERY = "INSERT INTO orders (client_id, fname, lname, email, phone, show_id, num_of_seats,"
            + "total_payment, credit_card_last_digit, exp_date_month, exp_date_year, order_date) values(?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String SELECT_ALL = "SELECT * FROM orders O inner join shows S on O.show_id = S.show_id ";
    private final static String DELET_QUERY = "DELET from orders WHERE order_id = (?)";
    public static final String SELECT_ORDER_BY_ID = "SELECT client_id,fname,lname,email,phone,show_id,num_of_seats,total_payment,credit_card_last_digit,exp_date_month,exp_date_year"
    + "FROM cinemacity.orders WHERE order_id = ? ";
    
    public OrdersManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(String client_id, String fname, String lname, String email, String phone, int show_id, int num_of_seats, double total_payment, String credit_card_last_digits, int exp_month, int exp_year, Date order_date) throws ClassNotFoundException, SQLException {
                
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("dd-MM-yyyy");

            statement.setString(1, client_id);
            statement.setString(2, fname);
            statement.setString(3, lname);
            statement.setString(4, email);
            statement.setString(5, phone);
            statement.setInt(6, show_id);
            statement.setInt(7, num_of_seats);
            statement.setDouble(8, total_payment);
            statement.setString(9, credit_card_last_digits);
            statement.setInt(10, exp_month);
            statement.setInt(11, exp_year);
            statement.setString(12, dateformatSql.format(order_date));
            return statement.executeUpdate();
        }
    }

    public int delete(int order_id) throws ClassNotFoundException, SQLException {

        try (Connection conn = manager.getConnection()){
            PreparedStatement statement = conn.prepareStatement(DELET_QUERY);
            statement.setInt(1, order_id);
            return statement.executeUpdate();
        } 
    }

    public Order get(int order_id) throws ClassNotFoundException, SQLException {
            
        Order orderToReturn;
        try (Connection conn = manager.getConnection()){
            PreparedStatement statement = conn.prepareStatement(SELECT_ORDER_BY_ID);
            statement.setInt(1, order_id);
            ResultSet rs = statement.executeQuery();
            orderToReturn = getOrderByResultSet(rs);
        }
        return orderToReturn;
    }
    
        public Order getOrderByResultSet(ResultSet rs) throws SQLException, ClassNotFoundException {
        Order OrderToReturn = new Order();

        OrderToReturn.setClientId(rs.getString("client_id"));
        OrderToReturn.setFirstName(rs.getString("fname"));
        OrderToReturn.setLastName(rs.getString("lname"));
        OrderToReturn.setEmail(rs.getString("email"));
        OrderToReturn.setPhoneNumber(rs.getString("phone"));
        OrderToReturn.setShow(manager.getShowsManager().getShow(rs.getInt("show_id")));
        OrderToReturn.setNumOfSeats(rs.getInt("num_of_seats"));
        OrderToReturn.setTotalPayment(rs.getDouble("total_payment"));
        OrderToReturn.setCreditCardLastDigit(rs.getString("credit_card_last_digit"));
        OrderToReturn.setExpDateMonth(rs.getInt("exp_date_month"));
        OrderToReturn.setExpDateYear(rs.getInt("exp_date_year"));
        OrderToReturn.setOrderDate(rs.getDate("order_date"));
        return OrderToReturn;
    }

    public List<Order> getAll() throws ClassNotFoundException, SQLException {
        
        ArrayList<Order> ListToReturn = new ArrayList<Order>();
        
        try (Connection conn = manager.getConnection()){
            PreparedStatement statement = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ListToReturn.add(getOrderByResultSet(rs));
            }
        }
        return ListToReturn;
    }

    public List<Order> getAllByMovie(int movie_id) throws ClassNotFoundException, SQLException {
                throw new UnsupportedOperationException();
    }

    public List<Order> getAllByShow(int show_id) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Order> getAllByClientId(String client_id) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException();
    }

}
