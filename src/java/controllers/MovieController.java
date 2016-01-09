/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import db.DbManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Movie;
import models.MovieCategory;
import views.MoviePageView;
import views.MoviesSearchView;

/**
 *
 * @author Dell
 */
@Path("movies")
public class MovieController {

    @GET
    @Path("search_view")
    public Response searchView() {
        try {
            List<MovieCategory> categories = ControllerHelper.getDb().getMovieCategoriesManager().getAllFromRedis();
            MoviesSearchView view = new MoviesSearchView(categories);
            return Response.status(Response.Status.OK).entity(view.getView()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("{id}")
    public Response moviePage(@PathParam("id") int id) {
        try {
            MoviePageView view = new MoviePageView(id);
            return Response.status(Response.Status.OK).entity(view.getView()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("search")
    public Response getMovieByFilter(@Context HttpServletRequest request, @QueryParam("keyword") String keyword, @QueryParam("cat_id") int cat_id, @QueryParam("has_trailer") boolean has_trailer, @QueryParam("is_recommended") boolean is_recommended) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Gson gson = new Gson();
        String json = null;

        try {
            List<Movie> movies = null;
            if (parameterMap.containsKey("keyword") && !parameterMap.containsKey("has_trailer")) {
                // used by keyword search
                movies = ControllerHelper.getDb().getMoviesManager().getByKeyword(keyword);
            } else if (parameterMap.containsKey("cat_id") && !parameterMap.containsKey("has_trailer")) {
                // used by category search
                movies = ControllerHelper.getDb().getMoviesManager().getByCategory(cat_id);
            } else if (!parameterMap.containsKey("has_trailer")) {
                movies = ControllerHelper.getDb().getMoviesManager().getAll();
            } else {
                movies = ControllerHelper.getDb().getMoviesManager().getAllByFilter(keyword, cat_id, has_trailer, is_recommended);
            }
            json = gson.toJson(movies);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Failed to get all movies, " + e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

//    @GET
//    @Path("liraz")
//    @Produces(MediaType.TEXT_HTML)
//    public String liraz() throws ServletException, IOException, SQLException, Exception {
//        System.out.println("Shay you are my bitch");
//        //ControllerHelper.getDb().getMovieCategoriesManager().addDefaultValues();
//        //ControllerHelper.getDb().getPromoCategoriesManager().addDefaultValues();
//       // ControllerHelper.getDb().getPromoCompaniesManager().addDefaultValues();
//      //  ControllerHelper.getDb().getPromosManager().addDefaultValues();
//        ControllerHelper.getDb().getPromosManager().delete(1);
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
    @Path("get/{id}")
    public Response getMovieById(@PathParam("id") int movie_id) {
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

    @GET
    @Path("home_recommended")
    public Response getRecommandedForHome(@Context HttpServletRequest request) {
        try {
            String recommendedFromRedis = ControllerHelper.getDb().getMoviesManager().getRecommendedFromRedis();
            return Response.status(Response.Status.OK).entity(recommendedFromRedis).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }
}
