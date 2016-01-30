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
import models.MovieSearchDetails;
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
    public Response searchView(@QueryParam("keyword") String keyword, @QueryParam("cat_id") int cat_id, @QueryParam("has_trailer") boolean has_trailer, @QueryParam("last_tickets") boolean last_tickets, @QueryParam("recomended") boolean recomended) {
        try {
            List<MovieCategory> categories = ControllerHelper.getDb().getMovieCategoriesManager().getAllFromRedis();
            MoviesSearchView view = new MoviesSearchView(categories, keyword, cat_id, has_trailer, last_tickets, recomended);
            return Response.status(Response.Status.OK).entity(view.getView()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("search2")
    public Response getMovieByFilter2(@Context HttpServletRequest request, @QueryParam("keyword") String keyword, @QueryParam("cat_id") int cat_id, @QueryParam("has_trailer") boolean has_trailer, @QueryParam("is_recommended") boolean is_recommended, @QueryParam("last") boolean num_of_seat_left) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Gson gson = new Gson();
        String json = null;

        try {
            List<MovieSearchDetails> movies = ControllerHelper.getDb().getMoviesManager().getAllByFilter2(keyword, cat_id, has_trailer, is_recommended, num_of_seat_left);
            json = gson.toJson(movies);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Failed to get movies, " + e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }
    
    @GET
    @Path("{id}")
    public Response moviePage(@PathParam("id") int id) {
        try {
            Movie movie = ControllerHelper.getDb().getMoviesManager().getMovieById(id);
            MoviePageView view = new MoviePageView(movie);
            return Response.status(Response.Status.OK).entity(view.getView()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("search")
    public Response getMovieByFilter(@Context HttpServletRequest request, @QueryParam("keyword") String keyword, @QueryParam("cat_id") int cat_id, @QueryParam("has_trailer") boolean has_trailer, @QueryParam("is_recommended") boolean is_recommended, @QueryParam("last") boolean num_of_seat_left) {

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
            }
            else {
                //String keyword, int cat_id, boolean has_trailer, boolean is_recommended, boolean num_of_seat_left)
                movies = ControllerHelper.getDb().getMoviesManager().getAllByFilter(keyword, cat_id, has_trailer, is_recommended, num_of_seat_left);
            }
            json = gson.toJson(movies);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Failed to get movies, " + e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

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
                    .entity("Failed to get movie, " + e.getMessage()).build();
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
                    .entity("Failed to get all movies, " + e.getMessage()).build();
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
