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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Company;

/**
 *
 * @author efrat
 */
public class CompanyManager implements DBEntityManager<Company> {

    private static final Logger LOGGER = Logger.getLogger(MovieCategoryManager.class.getName());
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS companies (\n"
            + "  comp_id INT AUTO_INCREMENT ,\n"
            + "  name VARCHAR(50) NOT NULL,\n"
            + "  address VARCHAR(250) NOT NULL,\n"
            + "  about_text VARCHAR(500) NULL,\n"
            + "  PRIMARY KEY (comp_id),\n"
            + "  UNIQUE INDEX comp_id_UNIQUE (comp_id ASC))";

    private final static String INSERT_TABLE = "INSERT INTO companies (name, address, about_text) values(?,?,?)";
    private final static String DELET_COMPANY = "DELET from companies WHERE name = (?)";
    private final static String UPDATE_COMPANY = "UPDATE companies SET name = ?, address = ?, about_text = ?"
            + " WHERE comp_id = ?";
    private final static String SELECT_ALLCOMPANY = "SELECT * FROM cinema_city.companies";

    public static String getCREATE_TABLE() {
        return CREATE_TABLE;
    }
    @Override
    public void createTable() {
        DBHelper.executeUpdateStatment(CREATE_TABLE);
    }

    @Override
    public boolean addEntity(Company entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_TABLE);

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getAddress());
            statement.setString(3, entity.getAboutText());

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
    public boolean update(Company entity) {
        Connection conn = null;
        boolean result = false;

        try {
            conn = DBHelper.getConnection();
            java.sql.PreparedStatement statement = conn.prepareStatement(UPDATE_COMPANY);

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getAddress());
            statement.setString(3, entity.getAboutText());
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
    public void delete(Company entity) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(DELET_COMPANY);
            statement.setString(1, entity.getName());
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static Company getCampanyByResultSetLine(ResultSet rs) throws SQLException {
        Company CompanyToReturn = new Company();
        CompanyToReturn.setId(rs.getInt("comp_id"));
        CompanyToReturn.setName(rs.getString("name"));
        CompanyToReturn.setName(rs.getString("address"));
        CompanyToReturn.setName(rs.getString("about_text"));

        return CompanyToReturn;
    }

    public ArrayList<Company> getAllCampany() {
        ArrayList<Company> ListToReturn = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = DBHelper.executeQueryStatment(SELECT_ALLCOMPANY);
            while (rs.next()) {
                ListToReturn.add(getCampanyByResultSetLine(rs));
            }
          
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return ListToReturn;
    }
}
