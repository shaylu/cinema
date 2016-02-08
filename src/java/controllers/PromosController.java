/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Promotion;
import models.PromotionCategory;
import views.PromotionsView;

/**
 *
 * @author Liraz
 */
@Path("promos")
public class PromosController {

    @GET
    @Path("")
    public Response home() {
        try {
            List<PromotionCategory> categories = ControllerHelper.getDb().getPromoCategoriesManager().getAll();
            PromotionsView view = new PromotionsView(categories);
            return Response.status(Response.Status.OK).entity(view.getView()).type(MediaType.TEXT_HTML).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("all")
    public Response getAllPromos(@Context HttpServletRequest request) {
        Gson gson = new Gson();
        String json = null;
        try {
            List<Promotion> promotions = ControllerHelper.getDb().getPromosManager().getAll();
            json = gson.toJson(promotions);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get all promotions, " + e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @GET
    @Path("{promo_id}")
    public Response getPromo(@PathParam("promo_id") int promoId) {
        Gson gson = new Gson();
        String json = null;
        try {
            Promotion promo = ControllerHelper.getDb().getPromosManager().getPromotionById(promoId);
            json = gson.toJson(promo);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get promotion, " + e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }
    
    @GET
    @Path("rand")
    public Response getRandPromo() {
        Gson gson = new Gson();
        String json = null;
        try {
            Promotion promo = ControllerHelper.getDb().getPromosManager().getRand();
            json = gson.toJson(promo);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get promotion, " + e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }
    
    
}
