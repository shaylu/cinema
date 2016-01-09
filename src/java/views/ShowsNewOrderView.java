/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;

/**
 *
 * @author Dell
 */
public class ShowsNewOrderView implements ICinemaView {

    int show_id;

    public ShowsNewOrderView(int show_id) {
        this.show_id = show_id;
    }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<h1>Shows > New Order</h1>\n"
                + "<form name=\"addOrder\" id=\"addOrder\" method=\"post\">\n"
                + "  <label for=\"txtClientId\">Client Id</label>\n"
                + "  <input type=\"text\" id=\"txtClientId\" name=\"txtClientId\">\n"
                + "  <label for=\"txtFname\">First Name</label>\n"
                + "  <input type=\"text\" id=\"txtFname\" name=\"txtFname\">\n"
                + "  <label for=\"txtFname\">Last Name</label>\n"
                + "  <input type=\"text\" id=\"txtLname\" name=\"txtFname\">\n"
                + "  <label for=\"txtEmail\">Email</label>\n"
                + "  <input type=\"email\" id=\"txtEmail\" name=\"txtEmail\">\n"
                + "  <label for=\"txtPhone\">Phone Number</label>\n"
                + "  <input type=\"text\" id=\"txtPhone\" name=\"txtPhone\">\n"
                + "  <label for=\"txtNumOfSeats\">Number of Seats</label>\n"
                + "  <input type=\"number\" id=\"txtNumOfSeats\" name=\"txtNumOfSeats\">\n"
                + "  <label for=\"txtCreditCardNum\">Credit Card Number</label>\n"
                + "  <input type=\"text\" id=\"txtCreditCardNum\" name=\"txtCreditCardNum\">\n"
                + "  <label for=\"selMonth\">Expiration Date Month</label>\n"
                + getMonthSelectBox()
                + "  <label for=\"selYear\">Expiration Date Year</label>\n"
                + getYearSelectBox()
                + "  <input type=\"hidden\" id=\"txtShowId\" name=\"txtShowId\" value=\"" + show_id + "\">\n"
                + "  <input type=\"submit\" value=\"Add\" /><span id=\"txtResult\"></span>\n"
                + "</form>"
        );

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../../scripts/shows.js"));
        res.append(LayoutHelper.getFooter());

        return res.toString();
    }

    private String getMonthSelectBox() {
        StringBuilder res = new StringBuilder();
        res.append("<select id=\"selMonth\" name=\"selMonth\">\n");
        for (int i = 1; i <= 12; i++) {
            res.append("<option value=\"" + i + "\">" + i + "</option>");
        }
        res.append("</select>");
        return res.toString();
    }

    private String getYearSelectBox() {
        StringBuilder res = new StringBuilder();
        res.append("<select id=\"selYear\" name=\"selYear\">\n");
        for (int i = 2016; i <= 2025; i++) {
            res.append("<option value=\"" + i + "\">" + i + "</option>");
        }
        res.append("</select>");
        return res.toString();
    }

}
