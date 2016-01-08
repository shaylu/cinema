/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import db.MovieCategoryManager;
import db.UsersManager;
import db.mysql.DbManager;
import html.LayoutHelper;
import java.io.Console;
import static java.lang.System.console;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import models.Company;
import models.Hall;
import models.Movie;
import models.MovieCategory;
import models.Promotion;
import models.PromotionCategory;
import models.Show;
import models.User;

/**
 *
 * @author Dell
 */
@Path("admin")
public class AdminController {

//    private static db.mysql.DbManager db;
//    static {
//        try {
//            db = new DbManager();
//        } catch (Exception e) {
//            String txt;
//            txt = e.getMessage();
//        }
//    }
    public AdminController() {

    }

    // ========================================================
    // ADMIN
    // ========================================================
    @GET
    @Path("")
    public Response admin(@QueryParam("user") String user, @QueryParam("pass") String pass, @Context HttpServletResponse response, @Context HttpServletRequest request) throws URISyntaxException {
        if (request.getAttribute("UserID") != null) {
            // already logged in
            // jump to admin home screen
            URI targetURIForRedirection = new URI("home");
            return Response.seeOther(targetURIForRedirection).build();
        } else {
            // not logged in
            StringBuilder res = new StringBuilder();
            res.append(LayoutHelper.getHeader());
            res.append("<h1>Admin > Login</h1>\n"
                    + "<form id=\"frmLogin\" name=\"frmLogin\" method=\"post\">\n"
                    + "  <label for=\"txtUsername\">Username</label>\n"
                    + "  <input type=\"text\" id=\"txtUsername\" name=\"txtUsername\">\n"
                    + "  <label for=\"txtPassword\">Password</label>\n"
                    + "  <input type=\"password\" id=\"txtPassword\" name=\"txtPassword\">\n"
                    + "  <div id=\"frmLoginMessage\" style=\"disply: hidden\"></div>\n"
                    + "  <input type=\"submit\" value=\"Login\" />\n"
                    + "</form>");
            res.append("<button id=\"createDefault\" name=\"createDefault\">Create Defualt Users</button>");

            res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../scripts/login.js"));
            res.append(LayoutHelper.getFooter());

            return Response.status(Response.Status.OK).entity(res.toString()).build();
        }
    }

    @GET
    @Path("home")
    public Response home(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append(LayoutHelper.getAdminMenu());
        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js"));
        res.append(LayoutHelper.getFooter());

        return Response.status(Response.Status.OK).type(MediaType.TEXT_HTML).entity(res.toString()).build();
    }

    @POST
    @Path("login")
    public Response login(@Context HttpServletRequest request, @FormParam("user") String user, @FormParam("pass") String pass) throws Exception {
        if (user != null && !user.equals("") && pass != null && !pass.equals("")) {
            // login
            try {
                boolean userExist = ControllerHelper.getDb().getUsersManager().userExist(user, pass);

                if (!userExist) {
                    throw new Exception("User or Password is invalid.");
                }

                int userId = ControllerHelper.getDb().getUsersManager().getUserId(user);

                // login, create session
                HttpSession session = request.getSession(true);
                session.setAttribute("Username", user);
                session.setAttribute("UserID", userId);

                return Response.status(Response.Status.OK).entity("Success.").build();
            } catch (Exception ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Required paramas are missing.").build();
        }
    }

//    @POST
//    @Path("filldb")
//    public Response fillDb(@Context HttpServletRequest request, @FormParam("user") String user, @FormParam("pass") String pass) throws Exception {
//        DbManager db = ControllerHelper.getDb();
//        db.getMovieCategoriesManager().addDefaultValues();
//        db.getMoviesManager().addDefaultValues();
//        db.getHallsManager().addDefaultValues();
//        db.getShowsManager().addDefaultValues();
//    }
    // ========================================================
    // USERS
    // ========================================================
    @POST
    @Path("users/add_default")
    public Response getLoginView(@Context HttpServletRequest request) throws URISyntaxException {
        int result;
        try {
            result = ControllerHelper.getDb().getUsersManager().addDefaultValues();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to add default users, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added " + result + " new users to db.").build();
    }

    // ========================================================
    // CATEGORIES
    // ========================================================
    @GET
    @Path("categories/all")
    public Response getAllCategories(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Gson gson = new Gson();
        String json = null;

        try {
            List<MovieCategory> categories = ControllerHelper.getDb().getMovieCategoriesManager().getAll();
            json = gson.toJson(categories);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Failed to get all categories, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @POST
    @Path("categories/add_default")
    public Response addNewCategory(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        int result;

        try {
            result = ControllerHelper.getDb().getMovieCategoriesManager().addDefaultValues();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add new category, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added " + result + " new categories to db.").build();
    }

    @POST
    @Path("categories/add")
    public Response addNewCategory(@Context HttpServletRequest request, @FormParam("name") String name) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            ControllerHelper.getDb().getMovieCategoriesManager().add(name);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add new category, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added new categories to db.").build();
    }

    @GET
    @Path("categories")
    public Response adminCategories(@Context HttpServletRequest request) throws URISyntaxException {
        if (!isLogin(request)) {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
            URI targetURIForRedirection = new URI("/admin");
            return Response.seeOther(targetURIForRedirection).build();
        }

        views.admin.AdminCategoriesView view = new views.admin.AdminCategoriesView();
        return Response.status(Response.Status.OK).type(MediaType.TEXT_HTML).entity(view.getView()).build();
    }

    // ========================================================
    // MOVIES
    // ========================================================
    @GET
    @Path("movies")
    public Response adminMovies(@Context HttpServletRequest request) {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        List<MovieCategory> categories = null;

        try {
            categories = ControllerHelper.getDb().getMovieCategoriesManager().getAll();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        views.admin.AdminMoviesView view = new views.admin.AdminMoviesView(categories);
        return Response.status(Response.Status.OK).entity(view.getView()).build();
    }

    @GET
    @Path("movies/all")
    public Response getAllMovies(@Context HttpServletRequest request) {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Gson gson = new Gson();
        List<Movie> movies = null;
        String json = null;

        try {
            movies = ControllerHelper.getDb().getMoviesManager().getAll();
            json = gson.toJson(movies);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity(json).build();
    }

    private boolean isLogin(HttpServletRequest request) {
        return request.getSession().getAttribute("UserID") != null;
    }

    @POST
    @Path("movies/add")
    public Response addNewMovie(
            @Context HttpServletRequest request,
            @FormParam("name") String name,
            @FormParam("release_date") String release_date,
            @FormParam("mov_length") int mov_length,
            @FormParam("cat_id") int cat_id,
            @FormParam("plot") String plot,
            @FormParam("poster") String poster,
            @FormParam("trailer") String trailer,
            @FormParam("is_recommanded") String is_recommanded
    ) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(release_date);
            boolean is_reco = is_recommanded.toLowerCase().equals("true") ? true : false;

            ControllerHelper.getDb().getMoviesManager().add(name, date, mov_length, cat_id, plot, poster, trailer, is_reco);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Failed to add movie, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("movies!!!!!").build();
    }

    // ========================================================
    // HALLS
    // ========================================================
    @GET
    @Path("halls")
    public Response adminHalls(@Context HttpServletRequest request) throws URISyntaxException {
        if (!isLogin(request)) {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
            URI targetURIForRedirection = new URI("/admin");
            return Response.seeOther(targetURIForRedirection).build();
        }

        views.admin.AdminHallsView view = new views.admin.AdminHallsView();
        return Response.status(Response.Status.OK).type(MediaType.TEXT_HTML).entity(view.getView()).build();
    }

    @POST
    @Path("halls/add")
    public Response addNewHall(@Context HttpServletRequest request, @FormParam("hall_id") int hall_id, @FormParam("num_of_seats") int num_of_seats) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            ControllerHelper.getDb().getHallsManager().add(hall_id, num_of_seats);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add new hall, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added new hall to db.").build();
    }

    @GET
    @Path("halls/all")
    public Response getAllHalls(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Gson gson = new Gson();
        String json = null;

        try {
            List<Hall> halls = ControllerHelper.getDb().getHallsManager().getaAll();
            json = gson.toJson(halls);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Failed to get all halls, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @POST
    @Path("halls/add_default")
    public Response addDefaultHalls(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        int result;

        try {
            result = ControllerHelper.getDb().getHallsManager().addDefaultValues();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add halls, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added " + result + " new halls to db.").build();
    }

    // ========================================================
    // SHOWS
    // ========================================================
    @GET
    @Path("shows")
    public Response adminShows(@Context HttpServletRequest request) throws URISyntaxException {
        if (!isLogin(request)) {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
            URI targetURIForRedirection = new URI("/admin");
            return Response.seeOther(targetURIForRedirection).build();
        }

        List<Movie> movies = null;
        List<Integer> halls = new ArrayList<>();

        try {
            movies = ControllerHelper.getDb().getMoviesManager().getAll();
            for (Hall hall : ControllerHelper.getDb().getHallsManager().getaAll()) {
                halls.add(hall.getId());
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        views.admin.AdminShowsView view = new views.admin.AdminShowsView(movies, halls);
        return Response.status(Response.Status.OK).type(MediaType.TEXT_HTML).entity(view.getView()).build();
    }

    @POST
    @Path("shows/add")
    public Response addShow(@Context HttpServletRequest request, @FormParam("hall_id") int hall_id, @FormParam("movie_id") int movie_id, @FormParam("price") double price, @FormParam("date") String date_str, @FormParam("time") String time_str) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(date_str);

            Hall hall = ControllerHelper.getDb().getHallsManager().getHallById(hall_id);
            int seats = hall.getNumOfSeats();
            ControllerHelper.getDb().getShowsManager().add(movie_id, hall_id, seats, date_str, time_str, price);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add new hall, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added new hall to db.").build();
    }

    @GET
    @Path("shows/all")
    public Response getAllShows(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Gson gson = new Gson();
        String json = null;

        try {
            List<Show> shows = ControllerHelper.getDb().getShowsManager().getAllShows();
            json = gson.toJson(shows);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Failed to get all shows, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @POST
    @Path("shows/add_default")
    public Response addDefaultShows(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        int result;

        try {
            result = ControllerHelper.getDb().getShowsManager().addDefaultValues();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add halls, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added " + result + " new halls to db.").build();
    }

    // ========================================================
    // COMPANIES
    // ========================================================
    @GET
    @Path("companies")
    public Response adminCompanies(@Context HttpServletRequest request) throws URISyntaxException {
        if (!isLogin(request)) {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
            URI targetURIForRedirection = new URI("/admin");
            return Response.seeOther(targetURIForRedirection).build();
        }

        views.admin.AdminCompaniesView view = new views.admin.AdminCompaniesView();
        return Response.status(Response.Status.OK).type(MediaType.TEXT_HTML).entity(view.getView()).build();
    }

    @POST
    @Path("companies/add")
    public Response addCompany(@Context HttpServletRequest request, @FormParam("name") String name, @FormParam("address") String address, @FormParam("about") String about) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            ControllerHelper.getDb().getPromoCompaniesManager().add(name, address, about);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add new company, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added new company to db.").build();
    }

    @GET
    @Path("companies/all")
    public Response getAllCompanies(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Gson gson = new Gson();
        String json = null;

        try {
            List<Company> companies = ControllerHelper.getDb().getPromoCompaniesManager().getAll();
            json = gson.toJson(companies);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Failed to get all companies, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @POST
    @Path("companies/add_default")
    public Response compAddDefault(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        int result;

        try {
            result = ControllerHelper.getDb().getPromoCompaniesManager().addDefaultValues();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add companies, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added " + result + " new companies to db.").build();
    }

    // ========================================================
    // PROMO CATEGORIES
    // ========================================================
    @GET
    @Path("promotions/categories")
    public Response adminPromoCompanies(@Context HttpServletRequest request) throws URISyntaxException {
        if (!isLogin(request)) {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
            URI targetURIForRedirection = new URI("/admin");
            return Response.seeOther(targetURIForRedirection).build();
        }

        views.admin.AdminPromoCategoriesView view = new views.admin.AdminPromoCategoriesView();
        return Response.status(Response.Status.OK).type(MediaType.TEXT_HTML).entity(view.getView()).build();
    }

    @POST
    @Path("promotions/categories/add")
    public Response addPromoCompany(@Context HttpServletRequest request, @FormParam("name") String name) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            ControllerHelper.getDb().getPromoCategoriesManager().add(name);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add new category, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added new category to db.").build();
    }

    @GET
    @Path("promotions/categories/all")
    public Response getAllPromoCategories(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Gson gson = new Gson();
        String json = null;

        try {
            List<PromotionCategory> categories = ControllerHelper.getDb().getPromoCategoriesManager().getAll();
            json = gson.toJson(categories);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Failed to get all companies, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @POST
    @Path("promotions/categories/add_default")
    public Response promoCategoriesAddDefault(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        int result;

        try {
            result = ControllerHelper.getDb().getPromoCategoriesManager().addDefaultValues();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add categories, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added " + result + " new categories to db.").build();
    }

    // ========================================================
    // PROMOS
    // ========================================================
    @GET
    @Path("promotions")
    public Response adminPromos(@Context HttpServletRequest request) throws URISyntaxException {
        if (!isLogin(request)) {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
            URI targetURIForRedirection = new URI("/admin");
            return Response.seeOther(targetURIForRedirection).build();
        }

        List<Company> companies = null;
        List<PromotionCategory> categories = null;
        try {
            companies = ControllerHelper.getDb().getPromoCompaniesManager().getAll();
            categories = ControllerHelper.getDb().getPromoCategoriesManager().getAll();

        } catch (Exception e) {
        }
        views.admin.AdminPromosView view = new views.admin.AdminPromosView(companies, categories);
        return Response.status(Response.Status.OK).type(MediaType.TEXT_HTML).entity(view.getView()).build();
    }

    @POST
    @Path("promotions/add")
    public Response addPromo(@Context HttpServletRequest request, @FormParam("cat_id") int cat_id, @FormParam("comp_id") int comp_id, @FormParam("description") String description, @FormParam("exp_date") String exp_date_str, @FormParam("promo_code") String promo_code, @FormParam("image") String image) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date exp_date = formatter.parse(exp_date_str);
            ControllerHelper.getDb().getPromosManager().add(comp_id, cat_id, description, exp_date, promo_code, image);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add new promo, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added new promo to db.").build();
    }

    @GET
    @Path("promotions/all")
    public Response getAllPromos(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Gson gson = new Gson();
        String json = null;

        try {
            List<Promotion> promotions = ControllerHelper.getDb().getPromosManager().getAll();
            json = gson.toJson(promotions);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Failed to get all promos, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @POST
    @Path("promotions/add_default")
    public Response promoAddDefault(@Context HttpServletRequest request) throws Exception {
        if (!isLogin(request)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        int result;

        try {
            result = ControllerHelper.getDb().getPromosManager().addDefaultValues();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add promos, " + e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity("Added " + result + " new promos to db.").build();
    }

    @POST
    @Path("fildb")
    public Response fildb() {
        try {
            ControllerHelper.getDb().getMovieCategoriesManager().addDefaultValues();
            ControllerHelper.getDb().getMoviesManager().addDefaultValues();
            ControllerHelper.getDb().getHallsManager().addDefaultValues();
            ControllerHelper.getDb().getShowsManager().addDefaultValues();
            ControllerHelper.getDb().getOrdersManager().addDefaultValues();
            ControllerHelper.getDb().getReviewsManager().addDefaultValues();
            ControllerHelper.getDb().getPromoCompaniesManager().addDefaultValues();
            ControllerHelper.getDb().getPromoCategoriesManager().addDefaultValues();
            ControllerHelper.getDb().getPromosManager().addDefaultValues();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity("Success.").build();
    }
}
