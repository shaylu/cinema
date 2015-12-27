/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import html.LayoutHelper;
import java.net.URISyntaxException;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author Dell
 */
@Path("admin")
public class LoginController {

    @GET
    @Path("")
    public String getRedirect(@Context ServletContext context) {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<h1>Admin > Login</h1>\n"
                + "<form name=\"frmLogin\" method=\"post\">\n"
                + "  <label for=\"txtUsername\">Username</label>\n"
                + "  <input type=\"text\" id=\"txtUsername\" name=\"txtUsername\">\n"
                + "  <label for=\"txtPassword\">Password</label>\n"
                + "  <input type=\"password\" id=\"txtPassword\" name=\"txtPassword\">\n"
                + "  <div id=\"frmLoginMessage\" style=\"disply: hidden\"></div>\n"
                + "  <input type=\"submit\" value=\"Login\" />\n"
                + "</form>");
        return res.toString();
    }
}
