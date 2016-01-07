/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import static db.mysql.MovieCategoriesManager.INSERT_QUERY;
import static db.mysql.MovieCategoriesManager.SELECT_ALL;
import static db.mysql.MovieCategoriesManager.SELECT_MOVIE_CATEGORY;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Company;
import models.MovieCategory;

/**
 *
 * @author shay.lugasi
 */
public class PromoCompaniesManager extends DbManagerEntity {

    public final static String INSERT_TABLE = "INSERT INTO companies (name, address, about_text) values(?,?,?)";
    public final static String DELET_COMPANY = "DELET from companies WHERE name = (?)";
    public final static String SELECT_ALLCOMPANY = "SELECT * FROM cinema_city.companies";
    public final static String SELECT_COMPANY = "SELECT * FROM companies WHERE comp_id = ?";

    public PromoCompaniesManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(String name, String address, String abutText) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, abutText);
            return statement.executeUpdate();
        }
    }

    public int addDefaultValues() throws ClassNotFoundException, SQLException {
        int result = 0;
        result += add("", "", "");
        result += add("", "", "");
        result += add("", "", "");
        result += add("", "", "");
        return result;
    }

    public List<Company> getAll() throws ClassNotFoundException, SQLException {

        ArrayList<Company> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL);
            while (rs.next()) {
                Company mc = createCompanyFromMySql(rs);
                result.add(mc);
            }
        }

        return result;
    }

    public Company getCompanyById(int id) throws ClassNotFoundException, SQLException {
        Company result;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_COMPANY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery(SELECT_COMPANY);
            result = createCompanyFromMySql(rs);
        }
        return result;
    }

    public Company createCompanyFromMySql(ResultSet rs) throws SQLException {
        Company result = new Company();
        result.setId(rs.getInt("comp_id"));
        result.setName(rs.getString("name"));
        result.setAddress(rs.getString("address"));
        result.setAboutText(rs.getString("abut_text"));
        return result;
    }

}
