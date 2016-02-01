/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Movie;
import models.Order;
import models.Review;
import models.ReviewOnly;
import views.PostNewReviewView;
import views.ReviewAddView;

/**
 *
 * @author Liraz TODO: checck
 */
@Path("reviews")
public class ReviewsController {

    @GET
    @Path("code/{order_id}")
    public Response getMovieByOrderId(@PathParam("order_id") int orderId) throws SQLException, ClassNotFoundException {
        Gson gson = new Gson();
        String json = null;
        try {
            Order order = ControllerHelper.getDb().getOrdersManager().getOrderById(orderId);
            Movie movieRes = order.getShow().getMovie();
            json = gson.toJson(movieRes);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get add review page, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @POST
    @Path("add")
    public Response addNewReview(@FormParam("order_id") int order_id, @FormParam("rank") double rank, @FormParam("text") String text, @FormParam("last_digits") String last_digits) {
        try {
            Order order = ControllerHelper.getDb().getOrdersManager().getOrderById(order_id);
            
            if (rank < 1 || rank > 5) {
                throw new Exception("Invalid rank!");
            }
            
            String creditCardLastDigit = order.getCreditCardLastDigit();
            
            if (!creditCardLastDigit.equals(last_digits)) {
                throw new Exception("Invalid order data.");
            }
            
            ControllerHelper.getDb().getReviewsManager().add(order_id, rank, text);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity("Successfully added new review.").build();

    }

    @GET
    @Path("bymovie/{movie_id}")
    public Response getReviewByMovie(@PathParam("movie_id") int movie_id) throws SQLException, ClassNotFoundException {
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy' '").create();
        String json = null;
        try {
            List<Review> reviews = ControllerHelper.getDb().getReviewsManager().getByMovieId(movie_id);
            List<ReviewOnly> reviews_only = new ArrayList<>();
            for (Review review : reviews) {
                reviews_only.add(new ReviewOnly(review));
            }
            json = gson.toJson(reviews_only);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get reviews by movie id, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @GET
    @Path("add-review/{order_id}/{last_digits}")
    public Response addReview(@PathParam("order_id") int order_id, @PathParam("last_digits") String last_digits) {
        try {
            Order order = ControllerHelper.getDb().getOrdersManager().getOrderById(order_id);
            String creditCardLastDigit = order.getCreditCardLastDigit();
            if (!creditCardLastDigit.equals(last_digits)) {
                throw new Exception("Invalid order data.");
            }
            boolean reviewExistForOrder = ControllerHelper.getDb().getReviewsManager().isReviewExistForOrder(order_id);
            Movie movie = ControllerHelper.getDb().getMoviesManager().getMovieById(order_id);
            views.ReviewAddView view = new ReviewAddView(order_id, creditCardLastDigit, movie, reviewExistForOrder);
            return Response.status(Response.Status.OK).type(MediaType.TEXT_HTML).entity(view.getView()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get add review page, " + e.getMessage()).build();
        }

    }
    
    @GET
    @Path("add-review") 
    public Response addReviewView() {
        PostNewReviewView view = new PostNewReviewView();
        return Response.status(Response.Status.OK).type(MediaType.TEXT_HTML).entity(view.getView()).build();
    }

}
