/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import models.Show;

/**
 *
 * @author Dell
 */
public class ShowsNewOrderView implements ICinemaView {

    Show show;

    public ShowsNewOrderView(Show show) {
        this.show = show;
    }

    @Override
    public String getView() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<div class=\"movie-details\" data-id=\"" + show.getMovie().getId() + "\"></div>");
        res.append("<h3>New order</h3>\n");
        res.append("<div class=\"content-box light-color\">"
                + " <div><b>Show Date:</b>&nbsp;" + df.format(show.getShowDate()) + "</div>"
                + " <div><b>Show Time:</b>&nbsp;" + show.getTime() + "</div>"
                + " <div><b>Hall:</b>&nbsp;" + show.getHall().getId() + "</div>"
                + " <div><b>Seats left:</b>&nbsp;" + show.getNumOfSeatsLeft() + "</div>"
                + " <div><b>Price Per Seat:</b>&nbsp;$" + show.getPricePerSeat() + "</div>"
                + "</div>");
        res.append("<h3>Order Details</h3><div class=\"content-box\"><form name=\"addOrder\" id=\"addOrder\" method=\"post\">\n"
                + "  <label for=\"numSeats\">Number Of Seats</label>\n"
                + "  <input type=\"number\" id=\"numSeats\" name=\"numSeats\" min=1 max=" + show.getNumOfSeatsLeft() + " value=1><div class=\"light-color\" id=\"totalPrice\" data-price=\"" + show.getPricePerSeat() + "\">$" + show.getPricePerSeat() + "</div>\n"
                + "  <label for=\"txtClientId\">Client Id</label>\n"
                + "  <input type=\"text\" id=\"txtClientId\" name=\"txtClientId\">\n"
                + "  <div><div class=\"block\"><label for=\"txtFname\">First Name</label>\n"
                + "  <input type=\"text\" id=\"txtFname\" name=\"txtFname\"></div>\n"
                + "  <div class=\"block\"><label for=\"txtFname\">Last Name</label>\n"
                + "  <input type=\"text\" id=\"txtLname\" name=\"txtFname\"></div></div>\n"
                + "  <div><div class=\"block\"><label for=\"txtEmail\">Email</label>\n"
                + "  <input type=\"email\" id=\"txtEmail\" name=\"txtEmail\"></div>\n"
                + "  <div class=\"block\"><label for=\"txtPhone\">Phone Number</label>\n"
                + "  <input type=\"text\" id=\"txtPhone\" name=\"txtPhone\"></div></div>\n"
                + "  <label for=\"txtCreditCardNum\">Credit Card Number</label>\n"
                + "  <input type=\"text\" id=\"txtCreditCardNum\" name=\"txtCreditCardNum\">\n"
                + "  <div><div class=\"block\"><label for=\"selMonth\">Expiration Date Month</label>\n"
                + getMonthSelectBox() + "</div>"
                + "  <div class=\"block\"><label for=\"selYear\"> / Year</label>\n"
                + getYearSelectBox()
                + "  <input type=\"hidden\" id=\"txtShowId\" name=\"txtShowId\" value=\"" + show.getId() + "\">\n"
                + "  </div></div><input type=\"submit\" value=\"Place Order\" /><span id=\"txtResult\"></span>\n"
                + "</form></div>"
        );

        res.append(html.LayoutHelper.addScripts("/cinema_app/scripts/movies.js", "/cinema_app/scripts/promos.js", "/cinema_app/scripts/order.js"));
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
