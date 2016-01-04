/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import db.UsersManager;
import html.LayoutHelper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import models.User;

/**
 *
 * @author Dell
 */
@Path("admin")
public class AdminController {

    @GET
    @Path("")
    public Response getLoginView(@QueryParam("user") String user, @QueryParam("pass") String pass, @Context HttpServletResponse response, @Context HttpServletRequest request) throws URISyntaxException {
        if (request.getAttribute("UserID") != null) {
            // already logged in
            // jump to admin home screen
            URI targetURIForRedirection = new URI("home");
            return Response.seeOther(targetURIForRedirection).build();
        } else {
            // not logged in
            StringBuilder res = new StringBuilder();
            res.append(LayoutHelper.getHeader());
            res.append("<h1>Admin > Login</h1>\n"
                    + "<form id=\"frmLogin\" name=\"frmLogin\" method=\"post\">\n"
                    + "  <label for=\"txtUsername\">Username</label>\n"
                    + "  <input type=\"text\" id=\"txtUsername\" name=\"txtUsername\">\n"
                    + "  <label for=\"txtPassword\">Password</label>\n"
                    + "  <input type=\"password\" id=\"txtPassword\" name=\"txtPassword\">\n"
                    + "  <div id=\"frmLoginMessage\" style=\"disply: hidden\"></div>\n"
                    + "  <input type=\"submit\" value=\"Login\" />\n"
                    + "</form>");

            res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../scripts/login.js"));
            res.append(LayoutHelper.getFooter());

            return Response.status(Response.Status.OK).entity(res.toString()).build();
        }
    }

    @POST
    @Path("login")
    public Response login(@Context HttpServletRequest request, @FormParam("user") String user, @FormParam("pass") String pass) throws Exception {
        if (user != null && !user.equals("") && pass != null && !pass.equals("")) {
            // login
            try {
                User userFromDB = UsersManager.checkUserAndPassword(user, pass);
//                User userFromDB = new User(1, "pass", "user", user, pass);

                // login, create session
                HttpSession session = request.getSession(true);
                session.setAttribute("Username", user);
                session.setAttribute("UserID", userFromDB.getFldUserId());
                
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("UserID", userFromDB.getFldUserId());
                jsonObject.addProperty("Username", userFromDB.getFldUserName());
                
                return Response.status(Response.Status.OK).entity(jsonObject.toString()).build();
                
            } catch (Exception ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Username or password is invalid.").build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Required paramas are missing.").build();
        }

    }
    
    @GET
    @Path("categories")
    public Response showCategories(@Context HttpServletRequest request) throws Exception{
        if (!isLogin(request))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        
        throw new Exception();
    }

    private boolean isLogin(HttpServletRequest request) {
        if (request.getSession().getAttribute("UserID") != null)
            return true;
        else
            return false;
    }
}
