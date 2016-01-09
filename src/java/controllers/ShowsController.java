/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    
}