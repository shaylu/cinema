/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mysql.jdbc.exceptions.MySQLDataException;
import static db.MovieCategoriesManager.REDIS_KEY;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import models.HomeRandomMovie;
import models.Movie;
import models.MovieCategory;
import models.MovieSearchDetails;
import org.apache.jasper.tagplugins.jstl.core.Catch;
import redis.clients.jedis.Jedis;

/**
 *
 * @author shay.lugasi
 */
public class MoviesManager extends DbManagerEntity {

    public static final String INSERT_QUERY = "INSERT INTO movies (name, release_date, mov_length, cat_id,"
            + "plot, poster,trailer,is_recommended) values(?,?,?,?,?,?,?,?)";
    public static final String SELECT_ALL = "SELECT * FROM movies M inner join movie_categories C on M.cat_id = C.cat_id ";
    public static final String SELECT_MOVIE_BY_ID = "SELECT * FROM movies M WHERE M.movie_id = ?";
    public static final String SELECT_MOVIE_DETAILS_BY_ID = "SELECT * FROM movies_search_details WHERE movie_id = ?";
    public static final String SELECT_MOVIE_BY_KEYWORD = "SELECT * FROM movies M WHERE M.name like ?";
    public final static String DELET_MOVIE = "DELETE from movies M WHERE movie_id = (?)";
    public final static String SELECT_RAND_10 = "SELECT * FROM movies ORDER BY RAND() LIMIT 10";
    //new
//    public final static String FILTER_QUERY_FILTERED_BY_ALL_NO_TRAILER = "SELECT * FROM movies M inner join shows S on M.movie_id = S.movie_id where M.is_recommended = ? and S.num_of_seats_left < 10 and M.name like ? ";
//    public final static String FILTER_QUERY_FILTERED_BY_ALL = "SELECT * FROM movies M inner join shows S on M.movie_id = S.movie_id where M.trailer = ? and M.is_recommended = ? and S.num_of_seats_left < 10 and M.name like ? ";
//    public final static String FILTER_QUERY_FILTERED_BY_ALL_NO_RECOMMENDED = "SELECT * FROM movies M inner join shows S on M.movie_id = S.movie_id where M.trailer = ? and S.num_of_seats_left < 10 and M.name like ? ";
//    public final static String FILTER_QUERY_FILTERED_BY_ALL_NO_LAST_TICKETS = "SELECT * FROM movies M where M.trailer = ? and M.is_recommended = ? and M.name like ? ";
//    public final static String FILTER_QUERY_FILTERED_BY_ALL_NO_KEYWORD = "SELECT * FROM movies M inner join shows S on M.movie_id = S.movie_id where M.trailer = ? and M.is_recommended = ? and S.num_of_seats_left < 10 and M.name like ?  ";
//    public final static String FILTER_QUERY_FILTERED_BY_TRAILER_AND_KEYWORD = "SELECT * FROM movies M inner join shows S on M.movie_id = S.movie_id where M.trailer = ? and M.name like ? ";
//    public final static String FILTER_QUERY_FILTERED_BY_RECOROMMENDED_AND_KEYWORD = "SELECT * FROM movies M inner join shows S on M.movie_id = S.movie_id where M.is_recommended = ? and M.name like ? ";
//    public final static String FILTER_QUERY_FILTERED_BY_LAST_TICKET_AND_KEYWORD = "SELECT * FROM movies M inner join shows S on M.movie_id = S.movie_id where and S.num_of_seats_left < 10 and M.name like ? ";

    public final static String RECOMMENDED_QUERY = "SELECT * FROM movies M WHERE M.is_recommended = true;";
    public static final String SELECT_MOVIE_BY_NAME = "SELECT * FROM movies M WHERE name = (?)";
    public static final String SELECT_MOVIE_BY_CATID = "SELECT * FROM movies M WHERE cat_id = (?)";
    public static final String GET_RANK = "SELECT s.movie_id AS movie_id, AVG(r.rank) AS rank\n"
            + "FROM reviews r\n"
            + "	LEFT JOIN orders o ON r.order_id = o.order_id\n"
            + "	LEFT JOIN shows s ON o.show_id = s.show_id\n"
            + "    WHERE s.movie_id = ?\n"
            + "    GROUP BY s.movie_id";
    public final static String REDIS_KEY = "recommended";
    Jedis jdisMovie;

    public MoviesManager(DbManager manager) {
        this.manager = manager;
    }

    public int add(String name, Date release_date, int mov_length, int cat_id, String plot, String poster_url, String trailer_url, boolean is_recommended) throws SQLException, ClassNotFoundException {

        this.jdisMovie = new Jedis("localhost");
        int result = 0;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);

            statement.setString(1, name);
            statement.setDate(2, new java.sql.Date(release_date.getTime()));
            statement.setDouble(3, mov_length);
            statement.setInt(4, cat_id);
            statement.setString(5, plot);
            statement.setString(6, poster_url);
            statement.setString(7, trailer_url);
            statement.setBoolean(8, is_recommended);
            result = statement.executeUpdate();

            if (is_recommended) {
                Movie movie = getMovieByName(name);
                String moviesJson = movie.toRedisRecommanded();
                jdisMovie.sadd(REDIS_KEY, moviesJson);
            }

            return result;
        } finally {
            jdisMovie.disconnect();
        }

    }

    public Movie getMovieById(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_MOVIE_BY_ID);
            //for check :id = 1;
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            Movie result = createMovieFromMySql(rs);
            return result;
        }
    }
    
    public MovieSearchDetails getMovieDetailsById(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_MOVIE_DETAILS_BY_ID);
            //for check :id = 1;
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            MovieSearchDetails result = getMovieSearchDetailsFromRS(rs);
            return result;
        }
    }

    public int getMovieRank(int movie_id) throws SQLException, ClassNotFoundException, Exception {
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_RANK);
            statement.setInt(1, movie_id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("rank");
            }
            else 
                throw new Exception("no rank for movie");
        }
    }

    public Movie createMovieFromMySql(ResultSet rs) throws SQLException, ClassNotFoundException {

        Movie result = new Movie();
        result.setId(rs.getInt("M.movie_id"));
        result.setName(rs.getString("M.name"));
        result.setRelease_date(rs.getDate("M.release_date"));
        result.setMovie_length(rs.getDouble("M.mov_length"));
        result.setCategory(manager.getMovieCategoriesManager().getMovieCategoryById(rs.getInt("M.cat_id")));
        result.setTrailer(rs.getString("M.trailer"));
        result.setPlot(rs.getString("M.plot"));
        result.setPoster(rs.getString("M.poster"));
        result.setIs_recomanded(rs.getBoolean("M.is_recommended"));
        return result;
    }

    public List<Movie> getAll() throws ClassNotFoundException, SQLException {
        ArrayList<Movie> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL);
            while (rs.next()) {
                Movie mc = createMovieFromMySql(rs);
                result.add(mc);
            }
        }
        return result;
    }
//TODO

    public boolean delete(int mov_id) throws SQLException, ClassNotFoundException {
        boolean result = false;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELET_MOVIE);
            statement.setInt(1, mov_id);
            statement.executeUpdate();
            result = true;
        }
        return result;
    }
//TODO

    public ArrayList<Movie> getRecommended() throws SQLException, ClassNotFoundException {

        ArrayList<Movie> moviesResult = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(RECOMMENDED_QUERY);
            while (rs.next()) {
                Movie mc = createMovieFromMySql(rs);
                moviesResult.add(mc);
            }
        }
        return moviesResult;
    }

    public String getRecommendedFromRedis() throws SQLException, ClassNotFoundException {
        //redis.connectToRedis();
        this.jdisMovie = new Jedis("localhost");
        Set<String> smembers = jdisMovie.smembers(REDIS_KEY);
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (String member : smembers) {
            str.append(member);
            str.append(",");
        }

        String res = str.toString();
        if (smembers.size() > 0) {
            res = res.substring(0, res.length() - 1);
        }
        res += "]";

        this.jdisMovie.disconnect();
        return res;
    }

    public List<Movie> getAllByFilter(String keyword, int cat_id, boolean has_trailer, boolean is_recommended, boolean num_of_seat_left) throws SQLException, ClassNotFoundException {

        ArrayList<Movie> listToReturn = new ArrayList<>();
        StringBuilder queryToreturn = new StringBuilder();
        String key = keyword;
        int index = 0;
        if (keyword == null) {
            key = " ";
        }
        //M.trailer = ? and M.is_recommended = ? and S.num_of_seats_left < 10 and M.cat_id = ?
        queryToreturn.append("SELECT * FROM movies M where M.name like ? ");
        PreparedStatement statement = null;
        try (Connection conn = manager.getConnection()) {
            if (cat_id == 0) {
                if (has_trailer) {
                    queryToreturn.append("and M.trailer is not null");
                    statement = conn.prepareStatement(queryToreturn.toString());
                    statement.setString(1, "%" + keyword + "%");
                } else if (is_recommended) {
                    queryToreturn.append("and M.is_recommended is not null");
                    statement = conn.prepareStatement(queryToreturn.toString());
                    statement.setString(1, "%" + keyword + "%");
                } else if (num_of_seat_left) {
                    index = queryToreturn.indexOf(" M ");
                    queryToreturn.insert(index + 3, " inner join shows S on M.movie_id = S.movie_id ");
                    queryToreturn.append("and S.num_of_seats_left < 10 ");
                    statement = conn.prepareStatement(queryToreturn.toString());
                    statement.setString(1, "%" + keyword + "%");
                } else if (has_trailer && is_recommended) {
                    queryToreturn.append("and M.trailer is not null and M.is_recommended is not null");
                    statement = conn.prepareStatement(queryToreturn.toString());
                    statement.setString(1, "%" + keyword + "%");
                } else if (num_of_seat_left && is_recommended) {
                    index = queryToreturn.indexOf(" M ");
                    queryToreturn.insert(index + 3, " inner join shows S on M.movie_id = S.movie_id ");
                    queryToreturn.append("and M.is_recommended is not null and S.num_of_seats_left < 10 ");
                    statement = conn.prepareStatement(queryToreturn.toString());
                    statement.setString(1, "%" + keyword + "%");
                } else if (has_trailer && num_of_seat_left) {
                    index = queryToreturn.indexOf(" M ");
                    queryToreturn.insert(index + 3, " inner join shows S on M.movie_id = S.movie_id ");
                    queryToreturn.append("and M.trailer is not null and S.num_of_seats_left < 10 ");
                    statement = conn.prepareStatement(queryToreturn.toString());
                    statement.setString(1, "%" + keyword + "%");
                } else {
                    statement = conn.prepareStatement(queryToreturn.toString());
                    statement.setString(1, "%" + keyword + "%");
                }
            } else if (has_trailer) {
                queryToreturn.append("and M.trailer is not null and M.cat_id = ?");
                statement = conn.prepareStatement(queryToreturn.toString());
                statement.setString(1, "%" + keyword + "%");
                statement.setInt(2, cat_id);

            } else if (is_recommended) {
                queryToreturn.append("and M.is_recommended is not null and M.cat_id = ?");
                statement = conn.prepareStatement(queryToreturn.toString());
                statement.setString(1, "%" + keyword + "%");
                statement.setInt(2, cat_id);
            } else if (num_of_seat_left) {
                index = queryToreturn.indexOf(" M ");
                queryToreturn.insert(index + 3, " inner join shows S on M.movie_id = S.movie_id ");
                queryToreturn.append("and S.num_of_seats_left < 10 and M.cat_id = ?");
                statement = conn.prepareStatement(queryToreturn.toString());
                statement.setString(1, "%" + keyword + "%");
                statement.setInt(2, cat_id);
            } else if (has_trailer && is_recommended) {
                queryToreturn.append("and M.trailer is not null and M.is_recommended is not null and M.cat_id = ?");
                statement = conn.prepareStatement(queryToreturn.toString());
                statement.setString(1, "%" + keyword + "%");
                statement.setInt(2, cat_id);
            } else if (num_of_seat_left && is_recommended) {
                index = queryToreturn.indexOf(" M ");
                queryToreturn.insert(index + 3, " inner join shows S on M.movie_id = S.movie_id ");
                queryToreturn.append("and M.is_recommended is not null and S.num_of_seats_left < 10 and M.cat_id = ?");
                statement = conn.prepareStatement(queryToreturn.toString());
                statement.setString(1, "%" + keyword + "%");
                statement.setInt(2, cat_id);
            } else if (has_trailer && num_of_seat_left) {
                index = queryToreturn.indexOf(" M ");
                queryToreturn.insert(index + 3, " inner join shows S on M.movie_id = S.movie_id ");
                queryToreturn.append("and M.trailer is not null and S.num_of_seats_left < 10 and M.cat_id = ?");
                statement = conn.prepareStatement(queryToreturn.toString());
                statement.setString(1, "%" + keyword + "%");
                statement.setInt(2, cat_id);
            } else {
                queryToreturn.append("and M.cat_id = ?");
                statement = conn.prepareStatement(queryToreturn.toString());
                statement.setString(1, "%" + keyword + "%");
                statement.setInt(2, cat_id);
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Movie movie = createMovieFromMySql(rs);
                listToReturn.add(movie);
            }
        }

        return listToReturn;
    }

    public List<MovieSearchDetails> getAllByFilter2(String keyword, int cat_id, boolean has_trailer, boolean is_recommended, boolean num_of_seat_left) throws SQLException, ClassNotFoundException {
        String base = "SELECT * FROM movies_search_details WHERE name LIKE ?";
        String query = "";
        String func_keyword = null;
        ArrayList<Object> params = new ArrayList<>();

        try (Connection conn = manager.getConnection()) {
            query += base;

            // keyword
            func_keyword = (!keyword.isEmpty() && keyword != null) ? "%" + keyword + "%" : "%%";
            params.add(func_keyword);

            // movie category
            if (cat_id != 0) {
                query += " AND cat_id = ?";
                params.add(cat_id);
            }

            // is recomended
            if (is_recommended == true) {
                query += " AND is_recommended = 1";
            }

            // has trailer
            if (has_trailer == true) {
                query += " AND trailer IS NOT NULL AND trailer <> ''";
            }

            // last tickets
            if (num_of_seat_left == true) {
                query += " AND num_of_seats_left <= 10";
            }

            PreparedStatement statment = conn.prepareStatement(query);
            for (int i = 1; i <= params.size(); i++) {
                statment.setObject(i, params.get(i - 1));
            }

            ArrayList<MovieSearchDetails> result = new ArrayList<>();
            ResultSet rs = statment.executeQuery();

            while (rs.next()) {
                result.add(getMovieSearchDetailsFromRS(rs));
            }

            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    public int addDefaultValues() throws SQLException, ClassNotFoundException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        int result = 0;
        // Creating Star Wars: The Force Awakens
        Date release_date = formatter.parse("2016-05-01");
        String plot = "Three decades after the defeat of the Galactic Empire, a new threat arises. The First Order attempts to rule the galaxy and only a rag-tag group of heroes can stop them, along with the help of the Resistance.";
        String posterLink = "/cinema_app/images/posters/star.jpg";
        String trailer = "https://www.youtube.com/embed/sGbxmsDFVnE";
        MovieCategory category = controllers.ControllerHelper.getDb().getMovieCategoriesManager().getMovieCategoryByName("Action");
        result += add("Star Wars: The Force Awakens", release_date, 131, category.getId(), plot, posterLink, trailer, true);

        //Creating Krampus
        release_date = formatter.parse("2016-04-23");
        plot = "A boy who has a bad Christmas ends up accidentally summoning a Christmas demon to his family home.";
        posterLink = "/cinema_app/images/posters/krampus.jpg";
        trailer = "https://www.youtube.com/embed/h6cVyoMH4QE";
        category = controllers.ControllerHelper.getDb().getMovieCategoriesManager().getMovieCategoryByName("Comedy");

        result += add("Krampus", release_date, 98, category.getId(), plot, posterLink, trailer, true);

        //Creating In the Heart of the Sea
        release_date = formatter.parse("2015-10-12");
        plot = "A recounting of a whaling ship's sinking by a giant whale in 1820 that would inspire the great novel, Moby Dick.";
        posterLink = "/cinema_app/images/posters/heart.jpg";
        trailer = "https://www.youtube.com/embed/h6cVyoMH4QE";
        category = controllers.ControllerHelper.getDb().getMovieCategoriesManager().getMovieCategoryByName("Action");
        result += add("In the Heart of the Sea", release_date, 122, category.getId(), plot, posterLink, trailer, true);

        //Creating Creed 
        release_date = formatter.parse("2015-05-25");
        plot = "The former World Heavyweight Champion Rocky Balboa serves as a trainer and mentor to Adonis Johnson, the son of his late friend and former rival Apollo Creed.";
        posterLink = "/cinema_app/images/posters/creed.jpg";
        trailer = "https://www.youtube.com/embed/Uv554B7YHk4";
        category = controllers.ControllerHelper.getDb().getMovieCategoriesManager().getMovieCategoryByName("Drama");
        result += add("Creed", release_date, 133, category.getId(), plot, posterLink, trailer, false);

        //Creating The Hunger Games: Mockingjay - Part 2 
        release_date = formatter.parse("2015-11-19");
        plot = "As the war of Panem escalates to the destruction of other districts by the Capitol, Katniss Everdeen, the reluctant leader of the rebellion, must bring together an army against President Snow, while all she holds dear hangs in the balance.";
        posterLink = "/cinema_app/images/posters/hunger.jpg";
        trailer = "https://www.youtube.com/embed/n-7K_OjsDCQ";
        category = controllers.ControllerHelper.getDb().getMovieCategoriesManager().getMovieCategoryByName("Sci-Fi");
        result += add("The Hunger Games: Mockingjay - Part 2", release_date, 137, category.getId(), plot, posterLink, trailer, true);

        //Creating Spectre  
        release_date = formatter.parse("2015-05-11");
        plot = "A cryptic message from Bond's past sends him on a trail to uncover a sinister organization. While M battles political forces to keep the secret service alive, Bond peels back the layers of deceit to reveal the terrible truth behind SPECTRE.";
        posterLink = "/cinema_app/images/posters/spectre.jpg";
        trailer = "https://www.youtube.com/embed/Uv554B7YHk4";
        category = controllers.ControllerHelper.getDb().getMovieCategoriesManager().getMovieCategoryByName("Action");
        result += add("Spectre", release_date, 148, category.getId(), plot, posterLink, trailer, false);

        // Creating Fifty Shades of Grey
        release_date = formatter.parse("2015-02-13");
        plot = "Literature student Anastasia Steele's life changes forever when she meets handsome, yet tormented, billionaire Christian Grey.";
        posterLink = "/cinema_app/images/posters/50_Shades.jpg";
        trailer = "https://youtu.be/SfZWFDs0LxA";
        category = controllers.ControllerHelper.getDb().getMovieCategoriesManager().getMovieCategoryByName("Drama");
        result += add("Fifty Shades of Grey", release_date, 125, category.getId(), plot, posterLink, trailer, true);
        
        // Creating The Boxtrolls
        release_date = formatter.parse("2014-09-26");
        plot = "A young orphaned boy raised by underground cave-dwelling trash collectors tries to save his friends from an evil exterminator.";
        posterLink = "/cinema_app/images/posters/boxtrolls.jpg";
        trailer = "https://youtu.be/Q2dFVnp5K0o";
        category = controllers.ControllerHelper.getDb().getMovieCategoriesManager().getMovieCategoryByName("Comedy");
        result += add("The Boxtrolls", release_date, 97, category.getId(), plot, posterLink, trailer, true);
      
        // Creating Let's Be Cops
        release_date = formatter.parse("2015-08-13");
        plot = "Two struggling pals dress as police officers for a costume party and become neighborhood sensations. But when these newly-minted \"heroes\" get tangled in a real life web of mobsters and dirty detectives, they must put their fake badges on the line.";
        posterLink = "/cinema_app/images/posters/Let's_Be_Cops.jpg";
        trailer = "https://youtu.be/ExciLtpHp74";
        category = controllers.ControllerHelper.getDb().getMovieCategoriesManager().getMovieCategoryByName("Comedy");
        result += add("Let's Be Cops", release_date, 104, category.getId(), plot, posterLink, trailer, true);
        
        return result;

    }

    public Movie getMovieByName(String name) throws SQLException, ClassNotFoundException {
        Movie result;
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_MOVIE_BY_NAME);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            rs.next();
            result = createMovieFromMySql(rs);
        }
        return result;
    }

    public List<Movie> getByKeyword(String keyword) throws SQLException, ClassNotFoundException {
        ArrayList<Movie> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_MOVIE_BY_KEYWORD);
            statement.setString(1, "%" + keyword + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Movie mc = createMovieFromMySql(rs);
                result.add(mc);
            }
        }
        return result;
    }

    public List<Movie> getByCategory(int cat_id) throws ClassNotFoundException, SQLException {
        ArrayList<Movie> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_MOVIE_BY_CATID);
            statement.setInt(1, cat_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Movie mc = createMovieFromMySql(rs);
                result.add(mc);
            }
        }
        return result;
    }

    public void deletKeyFromRedis() {
        this.jdisMovie = new Jedis("localhost");
        this.jdisMovie.del(REDIS_KEY);
        this.jdisMovie.disconnect();
    }

    public MovieSearchDetails getMovieSearchDetailsFromRS(ResultSet rs) throws SQLException {
        int movie_id = rs.getInt("movie_id");
        String name = rs.getString("name");
        Date release_date = rs.getDate("release_date");
        Integer movie_length = rs.getInt("mov_length");
        String plot = rs.getString("plot");
        String poster = rs.getString("poster");
        String trailer = rs.getString("trailer");
        boolean is_recommended = rs.getBoolean("is_recommended");
        Integer cat_id = rs.getInt("cat_id");
        String cat_name = rs.getString("cat_name");
        Integer rank = rs.getInt("rank");
        Date show_date = rs.getDate("show_date");
        String show_time = (rs.getTime("show_time") != null) ? rs.getTime("show_time").toString() : null;
        Integer show_id = rs.getInt("show_id");
        Integer num_of_seats_left = rs.getInt("num_of_seats_left");

        MovieSearchDetails.MovieSearchDetailsCategory cat = new MovieSearchDetails.MovieSearchDetailsCategory(cat_id, cat_name);
        MovieSearchDetails.MovieSearchDetailsShow show = new MovieSearchDetails.MovieSearchDetailsShow(show_id, show_date, show_time, num_of_seats_left);
        MovieSearchDetails result = new MovieSearchDetails(movie_id, name, release_date, movie_length, cat, plot, poster, trailer, is_recommended, rank, show);
        return result;
    }
    
    public HomeRandomMovie getRandomMovieFromRS(ResultSet rs) throws SQLException {
        int movie_id = rs.getInt("movie_id");
        String name = rs.getString("name");
        String poster = rs.getString("poster");
        return new HomeRandomMovie(movie_id, poster, name);
    }

    public List<HomeRandomMovie> getRandom10() throws ClassNotFoundException, SQLException {
        ArrayList<HomeRandomMovie> result = new ArrayList<>();
        try (Connection conn = manager.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_RAND_10);
            while (rs.next()) {
                HomeRandomMovie movieSearchDetailsFromRS = getRandomMovieFromRS(rs);
                result.add(movieSearchDetailsFromRS);
            }
        }
        return result;
    }

}
