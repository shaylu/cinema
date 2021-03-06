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
import models.PromotionCategoryPresentation;
import views.PromotionCategoryPageView;

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
            //List<Promotion> promotion = ControllerHelper.getDb().getPromosManager().getAll();
            List<PromotionCategoryPresentation> presnted = ControllerHelper.getDb().getPromosManager().getPromoCatByRandPicList(categories);
            PromotionsView view = new PromotionsView(presnted);
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

    @GET
    @Path("/category/{promo_cat_id}")
    public Response promotionsCategoryPage(@PathParam("promo_cat_id") int id) {
        try {
            PromotionCategory category = ControllerHelper.getDb().getPromoCategoriesManager().getPromotionCategoryById(id);
            PromotionCategoryPageView view = new PromotionCategoryPageView(category);
            return Response.status(Response.Status.OK).entity(view.getView()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get promotion, " + e.getMessage()).build();
        }
    }

    @GET
    @Path("get-by-cat/{id}")
    public Response getByCat(@PathParam("id") int cat_id) {
        try {
            Gson gson = new Gson();
            List<Promotion> promos = ControllerHelper.getDb().getPromosManager().getPromosByCatId(cat_id);
            return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(gson.toJson(promos)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get promotion, " + e.getMessage()).build();
        }
    }
}
