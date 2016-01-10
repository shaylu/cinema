/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.admin;

import html.LayoutHelper;
import java.util.List;
import models.Company;
import models.PromotionCategory;
import views.ICinemaView;

/**
 *
 * @author Dell
 */
public class AdminPromosView implements ICinemaView {

    List<Company> companies;
    List<PromotionCategory> categories;

    public AdminPromosView(List<Company> companies, List<PromotionCategory> categories) {
        this.companies = companies;
        this.categories = categories;
    }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<p><button id=\"btnAddDefualtCompanies\">Add Default Promos</button></p>");
        res.append("<h1>Admin > Promos</h1>\n"
                + "<h2>Promos</h2>\n"
                + "<div id=\"divAllPromos\"></div>\n"
                + "<button name=\"btnRefreshPromos\" id=\"btnRefreshPromos\">Get All Promos</button>\n"
                + "<h2>Add Promos</h2>\n"
                + "<form name=\"addPromos\" id=\"addPromos\" method=\"post\">\n"
                + "  <label for=\"selCatID\">Category</label>\n"
                + getPromoCatsSelectBox()
                + "  <label for=\"selCompID\">Company</label>\n"
                + getCompaniesSelectBox()
                + "  <label for=\"txtDescription\">Description</label>\n"
                + "  <input type=\"text\" id=\"txtDescription\" name=\"txtDescription\">\n"
                + "  <label for=\"txtExpDate\">Expiration Date</label>\n"
                + "  <input type=\"date\" id=\"txtExpDate\" name=\"txtExpDate\">\n"
                + "  <label for=\"txtPromoCode\">Promo Code</label>\n"
                + "  <input type=\"text\" id=\"txtPromoCode\" name=\"txtPromoCode\">\n"
                + "  <label for=\"txtImage\">Image</label>\n"
                + "  <input type=\"text\" id=\"txtImage\" name=\"txtImage\">\n"
                + "  <input type=\"submit\" value=\"Add\" />\n"
                + "</form>"
                + "<p>"
                + "<h2>Delete Promos</h2>\n"
                + "<form id=\"deletePromo\">"
                + "  <label for=\"txtPromoId\">Promo ID</label>\n"
                + "  <input type=\"number\" id=\"txtPromoId\" name=\"txtPromoId\">\n"
                + "  <input type=\"submit\" value=\"Delete\" />\n"
                + "</form></p>");

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../scripts/admin_promos.js"));
        res.append(LayoutHelper.getFooter());

        return res.toString();
    }

    private String getCompaniesSelectBox() {
        StringBuilder res = new StringBuilder();
        res.append("<select id=\"selCompID\" name=\"selCompID\">\n");
        for (Company comp : companies) {
            res.append("<option value=\"" + comp.getId() + "\">" + comp.getName() + "</option>");
        }
        res.append("</select>");
        return res.toString();
    }

    private String getPromoCatsSelectBox() {
        StringBuilder res = new StringBuilder();
        res.append("<select id=\"selCatID\" name=\"selCatID\">\n");
        for (PromotionCategory cat : categories) {
            res.append("<option value=\"" + cat.getId() + "\">" + cat.getName() + "</option>");
        }
        res.append("</select>");
        return res.toString();
    }

}
