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
 * @author shay.lugasi
 */
public class AdminHallsView implements ICinemaView {

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<p><button id=\"btnAddDefualtHalls\">Add Default halls</button></p>");
        res.append("<h1>Admin > Halls</h1>\n"
                + "<h2>Halls</h2>\n"
                + "<div id=\"divAllHalls\"></div>\n"
                + "<button name=\"btnRefreshHalls\" id=\"btnRefreshHalls\">Get All Halls</button>\n"
                + "<h2>Add Hall</h2>\n"
                + "<form name=\"addHall\" id=\"addHall\" method=\"post\">\n"
                + "  <label for=\"txtHallId\">Hall Id</label>\n"
                + "  <input type=\"num\" id=\"txtHallId\" name=\"txtHallId\">\n"
                + "  <label for=\"txtNumOfSeats\">Number Of Seats</label>\n"
                + "  <input type=\"num\" id=\"txtNumOfSeats\" name=\"txtNumOfSeats\">\n"
                + "  <input type=\"submit\" value=\"Add\" />\n"
                + "</form>");

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../scripts/admin_halls.js"));
        res.append(LayoutHelper.getFooter());

        return res.toString();
    }

}
