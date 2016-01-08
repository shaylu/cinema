/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.DBManager;
import db.mysql.DbManager;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Dell
 */
@Path("movies")
public class MovieController {

    @GET
    @Path("search")
    @Produces(MediaType.TEXT_HTML)
    public String search() throws ServletException, IOException {
//        try {
//            Jedis jedis = new Jedis("localhost");
//            jedis.set("a", "aaaaaaaaaaa");
//            return jedis.get("a");
//        } catch (Exception ex) {
//            return "Hello";
//        }

        return "Liraz";
    }

    @GET
    @Path("liraz")
    @Produces(MediaType.TEXT_HTML)
    public String liraz() throws ServletException, IOException, SQLException, Exception {
        System.out.println("Shay you are my bitch");
        //DbManager db = new DbManager();
        return "<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n"
                + "<HTML>\n"
                + "   <HEAD>\n"
                + "      <TITLE>\n"
                + "         A Small Hello \n"
                + "      </TITLE>\n"
                + "   </HEAD>\n"
                + "<BODY>\n"
                + "   <H1>Hi</H1>\n"
                + "   <P>This is very minimal \"Shay you are my bitch\" HTML document.</P> \n"
                + "</BODY>\n"
                + "</HTML>";

    }
}
