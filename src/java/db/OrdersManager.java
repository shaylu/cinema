/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
            + "total_payment, credit_card_last_digit, exp_date_month, exp_date_year, order_date) values(?,?,?,?,?,?,?,?,?,?,?,CURDATE())";
    public static final String SELECT_ALL = "SELECT * FROM orders O inner join shows S on O.show_id = S.show_id ";
    private final static String DELET_QUERY = "DELET from orders WHERE order_id = (?)";
    public static final String SELECT_ORDER_BY_ID = "SELECT * FROM cinemacity.orders O inner join shows S on O.show_id = S.show_id WHERE order_id = ?";
    public static final String SELECT_ORDER_BY_SHOW_ID = "SELECT * FROM orders O inner join shows S on O.show_id = S.show_id  WHERE show_id = ?";
    public static final String SELECT_ORDER_BY_CLIENT_ID = "SELECT * FROM orders O inner join shows S on O.show_id = S.show_id client_id = ?";
    public static final String SELECT_ORDER = "SELECT * FROM orders O inner join shows S on O.show_id = S.show_id WHERE order_id = ?";

    public OrdersManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(String client_id, String fname, String lname, String email, String phone, int show_id, int num_of_seats, double total_payment, String credit_card_last_digits, int exp_month, int exp_year) throws ClassNotFoundException, SQLException {

        try (Connection conn = manager.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY,PreparedStatement.RETURN_GENERATED_KEYS);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("yyyy-MM-dd");

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
            statement.executeUpdate();
            int result = manager.getShowsManager().substructTickets(show_id, num_of_seats, conn);

            conn.commit();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            //Order order =getOrderById(rs.getInt(1));
            
            return rs.getInt(1);
        }
    }

    public int addDefaultValues() throws ClassNotFoundException, SQLException, ParseException {
        int result = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        result += add("123456", "Mor", "Shalom", "morS@gmail.com", "05244781256", 1, 1, 25.5, "2588", 05, 2019);
        result += add("111444", "Inbar", "Gal", "inbargal@gmail.com", "0507778899", 2, 3, 88.5, "3669", 02, 2021);
        result += add("265487", "Raz", "Maor", "razmaor@gmail.com", "057874556", 3, 2, 44, "5987", 07, 2017);
        result += add("841576", "Ben", "Tapuzi", "tapuzi@gmail.com", "0505547123", 4, 2, 71, "5987", 07, 2017);

        return result;
    }
//TODO

    public int delete(int order_id) throws ClassNotFoundException, SQLException {

        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELET_QUERY);
            statement.setInt(1, order_id);
            return statement.executeUpdate();
        }
    }
//TODO

    public Order get(int order_id) throws ClassNotFoundException, SQLException {

        Order orderToReturn;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_ORDER_BY_ID);
            statement.setInt(1, order_id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            orderToReturn = createOrderFromMySqlWithInnerJoin(rs);
        }
        return orderToReturn;
    }

    public Order createOrderFromMySqlWithInnerJoin(ResultSet rs) throws SQLException, ClassNotFoundException {
        Order OrderToReturn = new Order();

        OrderToReturn.setClientId(rs.getString("O.client_id"));
        OrderToReturn.setFirstName(rs.getString("O.fname"));
        OrderToReturn.setLastName(rs.getString("O.lname"));
        OrderToReturn.setEmail(rs.getString("O.email"));
        OrderToReturn.setPhoneNumber(rs.getString("O.phone"));
        OrderToReturn.setShow(manager.getShowsManager().getShowById(rs.getInt("O.show_id")));
        OrderToReturn.setNumOfSeats(rs.getInt("O.num_of_seats"));
        OrderToReturn.setTotalPayment(rs.getDouble("O.total_payment"));
        OrderToReturn.setCreditCardLastDigit(rs.getString("O.credit_card_last_digit"));
        OrderToReturn.setExpDateMonth(rs.getInt("O.exp_date_month"));
        OrderToReturn.setExpDateYear(rs.getInt("O.exp_date_year"));
        OrderToReturn.setOrderDate(rs.getDate("O.order_date"));
        return OrderToReturn;
    }

//     public Order createOrderFromMySql(ResultSet rs) throws SQLException, ClassNotFoundException {
//        Order OrderToReturn = new Order();
//
//        OrderToReturn.setClientId(rs.getString("client_id"));
//        OrderToReturn.setFirstName(rs.getString("fname"));
//        OrderToReturn.setLastName(rs.getString("lname"));
//        OrderToReturn.setEmail(rs.getString("email"));
//        OrderToReturn.setPhoneNumber(rs.getString("phone"));
//        OrderToReturn.setShow(manager.getShowsManager().getShowById(rs.getInt("show_id")));
//        OrderToReturn.setNumOfSeats(rs.getInt("num_of_seats"));
//        OrderToReturn.setTotalPayment(rs.getDouble("total_payment"));
//        OrderToReturn.setCreditCardLastDigit(rs.getString("credit_card_last_digit"));
//        OrderToReturn.setExpDateMonth(rs.getInt("exp_date_month"));
//        OrderToReturn.setExpDateYear(rs.getInt("exp_date_year"));
//        OrderToReturn.setOrderDate(rs.getDate("order_date"));
//        return OrderToReturn;
//    }

    public List<Order> getAll() throws ClassNotFoundException, SQLException {

        ArrayList<Order> ListToReturn = new ArrayList<Order>();

        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = createOrderFromMySqlWithInnerJoin(rs);
                ListToReturn.add(order);
            }
        }
        return ListToReturn;
    }

    public List<Order> getAllByShow(int show_id) throws ClassNotFoundException, SQLException {
        ArrayList<Order> ListToReturn = new ArrayList<Order>();

        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_ORDER_BY_SHOW_ID);
            statement.setInt(1, show_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ListToReturn.add(createOrderFromMySqlWithInnerJoin(rs));
            }
        }
        return ListToReturn;
    }
//TODO

    public List<Order> getAllByClientId(String client_id) throws ClassNotFoundException, SQLException {

        ArrayList<Order> ListToReturn = new ArrayList<Order>();
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_ORDER_BY_CLIENT_ID);
            statement.setString(1, client_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ListToReturn.add(createOrderFromMySqlWithInnerJoin(rs));
            }
        }
        return ListToReturn;
    }

    public Order getOrderById(int id) throws SQLException, ClassNotFoundException {
        Order orderToReturn = new Order();

        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_ORDER);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            orderToReturn = createOrderFromMySqlWithInnerJoin(rs);
        }

        return orderToReturn;
    }
}
