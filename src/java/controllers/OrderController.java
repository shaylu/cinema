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
                    .entity("Failed to get all orders, " + e.getMessage()).build();
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
                    .entity("Failed to get order, " + e.getMessage()).build();
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
                    .entity("Failed to get order details, " + e.getMessage()).build();
        }
    }

    @POST
    @Path("add")
    public Response addNewOrder(@Context HttpServletRequest request, @FormParam("client_id") String client_id, @FormParam("fname") String fname,
            @FormParam("lname") String lname, @FormParam("email") String email, @FormParam("credit_num") String credit_num,
            @FormParam("num_of_seats") int num_of_seats, @FormParam("month") int month, @FormParam("year") int year, @FormParam("show_id") int show_id,
            @FormParam("phone") String phone) throws ClassNotFoundException, SQLException {
        try {
            // making order
            if (client_id == null || client_id.isEmpty()) {
                throw new Exception("Client ID is invalid.");
            }
            
            if (fname == null || fname.isEmpty() || lname == null || lname.isEmpty()) {
                throw new Exception("Name is invalid.");
            }
            
            if (email == null || email.isEmpty()) {
                throw new Exception("Email is invalid.");
            }
            
            if (phone == null || phone.isEmpty()) {
                throw new Exception("Phone is invalid.");
            }
            
            if (credit_num == null || credit_num.isEmpty() || credit_num.length() < 4) {
                throw new Exception("Credit card is invalid.");
            }
            
            if (month < 1 && month > 12) {
                throw new Exception("Invalid credit card expiration month.");
            }
            
            if (year < 2016 && month > 2025) {
                throw new Exception("Invalid credit card expiration year.");
            }
            
            Show show = controllers.ControllerHelper.getDb().getShowsManager().getShowById(show_id);
            if (num_of_seats > show.getNumOfSeatsLeft())
            {
                throw new Exception("Not enought seats left for this order.");
            }
            
            String credit_card_last_digit = credit_num.substring(Math.max(0, credit_num.length() - 4));
            double total_payment = show.getPricePerSeat() * num_of_seats;
            Integer new_id = ControllerHelper.getDb().getOrdersManager().add(client_id, fname, lname, email, phone, show_id, num_of_seats, total_payment, credit_card_last_digit, month, year);
            return Response.status(Response.Status.OK).entity(new_id.toString()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("validate-order-code")
    public Response validateOrderCode(@FormParam("order_id") int order_id, @FormParam("last_digits") String last_digits) {
        try {
            Order order = ControllerHelper.getDb().getOrdersManager().getOrderById(order_id);
            String creditCardLastDigit = order.getCreditCardLastDigit();
            if (creditCardLastDigit.equals(last_digits)) {
                return Response.status(Response.Status.OK).build();
            }
            else throw new Exception("invalid data.");
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
