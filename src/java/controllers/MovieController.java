/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import db.mysql.DbManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Movie;
import models.MovieCategory;

/**
 *
 * @author Dell
 */
@Path("movies")
public class MovieController {

    @GET
    @Path("search")
    public Response getMovieByFilter(@PathParam("keyword") String keyword, @PathParam("cat_id") int cat_id, @PathParam("has_trailer") boolean has_trailer, @PathParam("is_recommended") boolean is_recommended) {

        Gson gson = new Gson();
        String json = null;
        try {
            keyword = "j";
            cat_id = 1;
            has_trailer = true;
            is_recommended = true;

            List<Movie> movies = ControllerHelper.getDb().getMoviesManager().getAllByFilter(keyword, cat_id, has_trailer, is_recommended);
            json = gson.toJson(movies);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Failed to get al orders, " + e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

//    @GET
//    @Path("liraz")
//    @Produces(MediaType.TEXT_HTML)
//    public String liraz() throws ServletException, IOException, SQLException, Exception {
//        System.out.println("Shay you are my bitch");
//        ControllerHelper.getDb().getMovieCategoriesManager().addDefaultValues();
//        ControllerHelper.getDb().getMoviesManager().addDefaultValues();
//       List <Movie> movies = ControllerHelper.getDb().getMoviesManager().getRecommendedFromRedis();
//
//        return "<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n"
//                + "<HTML>\n"
//                + "   <HEAD>\n"
//                + "      <TITLE>\n"
//                + "         A Small Hello \n"
//                + "      </TITLE>\n"
//                + "   </HEAD>\n"
//                + "<BODY>\n"
//                + "   <H1>Hi</H1>\n"
//                + "   <P>This is very minimal \"Shay you are my bitch\" HTML document.</P> \n"
//                + "</BODY>\n"
//                + "</HTML>";
//
//    }

    @GET
    @Path("{id}")
    public Response getMovieById(@PathParam("movie_id") int movie_id) {
        Gson gson = new Gson();
        String json = null;
        try {
            Movie movie = ControllerHelper.getDb().getMoviesManager().getMovieById(movie_id);
            json = gson.toJson(movie);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get promotion, " + e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @GET
    @Path("all")
    public Response getAllMovies(@Context HttpServletRequest request) {
        Gson gson = new Gson();
        String json = null;
        try {
            List<Movie> movies = ControllerHelper.getDb().getMoviesManager().getAll();
            json = gson.toJson(movies);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get al orders, " + e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }
}
