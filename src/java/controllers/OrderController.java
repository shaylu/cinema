/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Order;
import models.Show;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import views.PostOrderView;

/**
 *
 * @author efrat
 */
@Path("orders")
public class OrderController {

    @GET
    @Path("all")
    public Response getAllOrders(@Context HttpServletRequest request) {
        Gson gson = new Gson();
        String json = null;
        try {
            List<Order> orders = ControllerHelper.getDb().getOrdersManager().getAll();
            json = gson.toJson(orders);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get al orders, " + e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @GET
    @Path("get/{order_id}")
    public Response getAllOrders(@PathParam("order_id") int order_id) {
        Gson gson = new Gson();
        String json = null;
        try {
            Order order = ControllerHelper.getDb().getOrdersManager().get(order_id);
            json = gson.toJson(order);
            return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get al orders, " + e.getMessage()).build();
        }
    }

    @GET
    @Path("thankyou/{order_id}")
    public Response thankyou(@PathParam("order_id") int order_id) {
        Gson gson = new Gson();
        String json = null;
        try {
            PostOrderView view = new PostOrderView(order_id);
            return Response.status(Response.Status.OK).entity(view.getView()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get al orders, " + e.getMessage()).build();
        }
    }

//    @GET
//    @Path("add_review/{order_id}")
//    public Response addReview(@PathParam("order_id") int order_id, @FormParam("rank") double rank, @FormParam("review") String review) {
//        Gson gson = new Gson();
//        try {
//            ControllerHelper.getDb().getReviewsManager().add(order_id, rank, review);
//        } catch (Exception e) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
//                    .entity("Failed to add review, " + e.getMessage()).build();
//        }
//        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity("Sucess").build();
//    }
    @POST
    @Path("add")
    public Response addNewOrder(@Context HttpServletRequest request, @FormParam("client_id") String client_id, @FormParam("fname") String fname,
            @FormParam("lname") String lname, @FormParam("email") String email, @FormParam("credit_num") String credit_num,
            @FormParam("num_of_seats") int num_of_seats, @FormParam("month") int month, @FormParam("year") int year, @FormParam("show_id") int show_id,
            @FormParam("phone") String phone) throws ClassNotFoundException, SQLException {

        try {
            // making order
            String credit_card_last_digit = credit_num.substring(Math.max(0, credit_num.length() - 4));
            Show show = controllers.ControllerHelper.getDb().getShowsManager().getShow(show_id);
            double total_payment = show.getPricePerSeat() * num_of_seats;
            ControllerHelper.getDb().getOrdersManager().add(client_id, fname, lname, email, phone, show_id, num_of_seats, total_payment, credit_card_last_digit, month, year);
            

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity("Success.").build();

    }
}
