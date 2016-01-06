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
public class AdminCategoriesView implements ICinemaView {

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<p><button id=\"btnAddDefualtCategories\">Add Default Categories</button></p>");
        res.append("<h1>Admin > Categories</h1>\n"
                + "<h2>Categories</h2>\n"
                + "<div id=\"divAllCategories\"></div>\n"
                + "<button name=\"btnRefreshCategories\" id=\"btnRefreshCategories\">Get All Categories</button>\n"
                + "<h2>Add Category</h2>\n"
                + "<form name=\"addCategory\" id=\"addCategory\" method=\"post\">\n"
                + "  <label for=\"txtCatName\">Category Name</label>\n"
                + "  <input type=\"text\" id=\"txtCatName\" name=\"txtCatName\">\n"
                + "  <input type=\"submit\" value=\"Add\" />\n"
                + "</form>");

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../scripts/admin_categories.js"));
        res.append(LayoutHelper.getFooter());
        
        return res.toString();
    }
}
