/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import java.sql.SQLException;
import java.util.List;
import models.Order;

/**
 *
 * @author shay.lugasi
 */
public class OrdersManager extends DbManagerEntity {

    public OrdersManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(String client_id, String fname, String lname, String email, String phone, int show_id, int num_of_seats, double total_payment, String credit_card_last_digits, int exp_month, int exp_year) throws ClassNotFoundException, SQLException {
        // no date parameter sent but be sure to update order date to NOW();
        throw new UnsupportedOperationException();
    }

    public int delete(int order_id) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException();
    }

    public Order get(int order_id) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Order> getAll() throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException();
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
