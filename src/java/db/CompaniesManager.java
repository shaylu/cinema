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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Company;
import models.MovieCategory;

/**
 *
 * @author shay.lugasi
 */
public class CompaniesManager extends DbManagerEntity {

    public final static String INSERT_TABLE = "INSERT INTO companies (name, address, about_text) values(?,?,?)";
    public final static String DELET_COMPANY = "DELET from companies WHERE name = (?)";
    public final static String SELECT_ALLCOMPANY = "SELECT * FROM companies";
    public final static String SELECT_COMPANY = "SELECT * FROM companies WHERE comp_id = ?";

    public CompaniesManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(String name, String address, String aboutText) throws ClassNotFoundException, SQLException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_TABLE);
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, aboutText);
            return statement.executeUpdate();
        }
    }

    public int addDefaultValues() throws ClassNotFoundException, SQLException {
        int result = 0;
        
        result += add("Elit", "Alenbi 55,Haifa", "coffe and candy");
        result += add("McDodals", "Herzel 55, TelAvivi", "fast food");
        result += add("Lizi Boutique", "Ben Zvi 156,Jerusalem ", "fashion");
        result += add("Best Merchandise Ever", "Ben Ziyon 20,Jerusalem ", "movie merchandise ");
        return result;
    }

    public List<Company> getAll() throws ClassNotFoundException, SQLException {

        ArrayList<Company> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALLCOMPANY);
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
            ResultSet rs = statement.executeQuery();
            rs.next();
            result = createCompanyFromMySql(rs);
        }
        return result;
    }

    public Company createCompanyFromMySql(ResultSet rs) throws SQLException {
        Company result = new Company();
        result.setId(rs.getInt("comp_id"));
        result.setName(rs.getString("name"));
        result.setAddress(rs.getString("address"));
        result.setAboutText(rs.getString("about_text"));
        return result;
    }

}
