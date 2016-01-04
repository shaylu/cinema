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
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

/**
 *
 * @author Liraz
 */
public class UsersManager implements DBEntityManager<User> {

    private static final Logger LOGGER = Logger.getLogger(UsersManager.class.getName());
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (\n"
            + "  fldUserId INT NOT NULL AUTO_INCREMENT,\n"
            + "  fldPassword VARCHAR(50) NOT NULL,\n"
            + "  fldUserName VARCHAR(50) NOT NULL UNIQUE,\n"
            + "  fldFname VARCHAR(50) NOT NULL,\n"
            + "  fldLname VARCHAR(50) NOT NULL,\n"
            + "  PRIMARY KEY (fld_user_id))";
    private final static String INSERT_TABLE = "INSERT INTO users (fld_user_id, fld_password, fld_fname, fld_lname)"
            + " values(?,?,?,?,?)";
    private final static String DELET_USER = "DELET from users WHERE fld_user_id = (?)";
    private final static String CHECK_USER = "SELECT * FROM users WHERE fldUserName = ? AND fldPassword = ?";

    private enum SQLFields {

        fldUserId, fldPassword, fldUserName, fldFname, fldLname
    }

    public static String getCREATE_TABLE() {
        return CREATE_TABLE;
    }

    @Override
    public void createTable() {
        DBHelper.executeUpdateStatment(CREATE_TABLE);
//    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addEntity(User entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_TABLE);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("dd-MM-yyyy");

            statement.setInt(1, entity.getFldUserId());
            statement.setString(2, entity.getFldPassword());
            statement.setString(3, entity.getFldFname());
            statement.setString(4, entity.getFldLname());
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
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(User entity) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBHelper.getConnection();
            com.mysql.jdbc.PreparedStatement statement = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(DELET_USER);
            statement.setString(1, entity.getFldUserName());
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
// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static User checkUserAndPassword(String user, String pass) throws Exception {
        Connection conn = null;
        try {
            conn = DBHelper.getConnection();
            com.mysql.jdbc.PreparedStatement statement = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(CHECK_USER);
            statement.setString(1, user);
            statement.setString(2, pass);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst()) {
                try {
                    rs.next();
                    return createUserFromRS(rs);
                } catch (Exception e) {
                    throw new Exception("failed to parse user record set.");
                }

            } else {
                throw new Exception("No records found.");
            }

        } catch (Exception e) {
            throw e;
        }
    }

    private static User createUserFromRS(ResultSet rs) throws SQLException {
        int id = rs.getInt(SQLFields.fldFname.name());
        String password = rs.getString(SQLFields.fldPassword.name());
        String username = rs.getString(SQLFields.fldUserName.name());
        String fname = rs.getString(SQLFields.fldFname.name());
        String lname = rs.getString(SQLFields.fldLname.name());
        return new User(id, password, username, fname, lname);
    }

}
