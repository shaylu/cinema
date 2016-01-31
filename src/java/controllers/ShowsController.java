/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Show;
import views.ShowsNewOrderView;

/**
 *
 * @author Dell
 */
@Path("shows")
public class ShowsController {
    
    @GET
    @Path("{show_id}/order")
    public Response newOrder(@PathParam("show_id") int show_id) {
        views.ShowsNewOrderView view = new ShowsNewOrderView(show_id);
        return Response.status(Response.Status.OK).type(MediaType.TEXT_HTML).entity(view.getView()).build();
    }
    
    @GET
    @Path("bymovie/{movie_id}")
    public Response byMovie(@PathParam("movie_id") int movie_id) {
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy' '").create();
        String json = null;
        try {
            List<Show> shows = ControllerHelper.getDb().getShowsManager().getAllShowsForMovie(movie_id);
            json = gson.toJson(shows);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN)
                    .entity("Failed to get shows, " + e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }
    
}
