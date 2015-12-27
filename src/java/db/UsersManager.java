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
import models.User;

/**
 *
 * @author Liraz
 */
public class UsersManager implements DBEntityManager<User> {

    private static final Logger LOGGER = Logger.getLogger(UsersManager.class.getName());
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (\n"
            + "  fld_user_id INT NOT NULL AUTO_INCREMENT,\n"
            + "  fld_password VARCHAR(50) NOT NULL,\n"
            + "  fld_fname VARCHAR(50) NOT NULL,\n"
            + "   fld_lname VARCHAR(50) NOT NULL,\n"
            + "  PRIMARY KEY (fld_user_id))";
    private final static String INSERT_TABLE = "INSERT INTO users (fld_user_id, fld_password, fld_fname, fld_lname)"
            + " values(?,?,?,?,?)";
    private final static String DELET_USER = "DELET from users WHERE fld_user_id = (?)";

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
    public void update(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(User entity) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBHelper.getConnection();
            com.mysql.jdbc.PreparedStatement statement = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(DELET_REVIEW);
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

}
