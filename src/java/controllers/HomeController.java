/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import models.MovieCategory;
import models.Promotion;
import views.HomeView;

/**
 *
 * @author Dell
 */
@Path("home")
public class HomeController {

    @GET
    @Path("")
    public Response home() {
        try {
            List<MovieCategory> categories = ControllerHelper.getDb().getMovieCategoriesManager().getAllFromRedis();
            Promotion randPromo = ControllerHelper.getDb().getPromosManager().getRand();
            HomeView view = new HomeView(categories, randPromo);
            return Response.status(Response.Status.OK).entity(view.getView()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
