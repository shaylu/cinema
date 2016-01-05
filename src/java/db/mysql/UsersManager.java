/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author shay.lugasi
 */
public class UsersManager extends DbManagerEntity {

    private static final String INSERT_QUERY = "INSERT INTO users (fldUserName, fldPassword, fldFname, fldLname)"
            + " values(?,?,?,?)";

    public UsersManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(String username, String password, String fname, String lname) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, fname);
            statement.setString(4, lname);
            return statement.executeUpdate();
        }
    }

    public int addDefaultValues() throws ClassNotFoundException, SQLException {
        int result = 0;
        result += add("liraz", "liraz", "Liraz", "Megadish");
        result += add("efrat", "efrat", "Efrat", "Shaul");
        result += add("shay", "shay", "Shay", "Lugassy");
        return result;
    }
}
