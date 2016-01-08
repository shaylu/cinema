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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Promotion;

/**
 *
 * @author Liraz
 */
@Path("promos")
public class PromosController {

    @GET
    @Path("all")
    public Response getAllPromos(@Context HttpServletRequest request) {
        Gson gson = new Gson();
        String json = null;
        try {
            List<Promotion> promotions = ControllerHelper.db.getPromosManager().getAll();
            json = gson.toJson(promotions);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get al promotions, " + e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }
}
