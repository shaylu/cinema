/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.admin;

import html.LayoutHelper;
import views.ICinemaView;

/**
 *
 * @author Dell
 */
public class AdminCompaniesView implements ICinemaView {

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<p><button id=\"btnAddDefualtCompanies\">Add Default Companies</button></p>");
        res.append("<h1>Admin > Companies</h1>\n"
                + "<h2>Companies</h2>\n"
                + "<div id=\"divAllCompanies\"></div>\n"
                + "<button name=\"btnRefreshCompanies\" id=\"btnRefreshCompanies\">Get All Companies</button>\n"
                + "<h2>Add Company</h2>\n"
                + "<form name=\"addCompany\" id=\"addCompany\" method=\"post\">\n"
                + "  <label for=\"txtCompanyName\">Company Name</label>\n"
                + "  <input type=\"text\" id=\"txtCompanyName\" name=\"txtCompanyName\">\n"
                + "  <label for=\"txtAddress\">Address</label>\n"
                + "  <input type=\"text\" id=\"txtAddress\" name=\"txtAddress\">\n"
                + "  <label for=\"txtAbout\">About</label>\n"
                + "  <input type=\"text\" id=\"txtAbout\" name=\"txtAbout\">\n"
                + "  <input type=\"submit\" value=\"Add\" />\n"
                + "</form>");

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../scripts/admin_companies.js"));
        res.append(LayoutHelper.getFooter());

        return res.toString();
    }

}
