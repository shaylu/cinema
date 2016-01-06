/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Dell
 */
public class DBHelper {

    public static String MySqlClassName = "com.mysql.jdbc.Driver";
    public static String MySqlHost = "jdbc:mysql://localhost:3306/cinema_city";
    public static String MySqlHost2 = "jdbc:mysql://localhost:3306";
    public static String MySqlUsername = "root";
    public static String MySqlPassword = "1234";
    public static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS cinema_city";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(MySqlClassName);
        com.mysql.jdbc.Connection conn = null;
        conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(MySqlHost, MySqlUsername, MySqlPassword);
        return conn;
    }

    static void executeUpdateStatment(String sql) {
        Connection conn = null;
        try {
            if (!sql.equals(CREATE_DB)) {
                conn = getConnection();
            } else {
                conn = getDataBaseConnection(); // for use when db wasn't created yet
            }
            Statement stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
                 
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }
    }

    // for use when database wasn't created yet
    private static Connection getDataBaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName(MySqlClassName);
        com.mysql.jdbc.Connection conn = null;
        conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(MySqlHost2, MySqlUsername, MySqlPassword);
        return conn;
    }

    static ResultSet executeQueryStatment(String sql) {
        ResultSet resultSetToReturn = null;
        Connection conn = null;
        try {
            if (!sql.equals(CREATE_DB)) {
                conn = getConnection();
            } else {
                conn = getDataBaseConnection(); // for use when db wasn't created yet
            }
            Statement stmt = (Statement) conn.createStatement();
            resultSetToReturn = stmt.executeQuery(sql);
        } catch (Exception e) {
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }
        return resultSetToReturn;
    }
}
