/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.exceptions.MySQLDataException;
import static db.MovieCategoryManager.getMovieCategoryByName;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Movie;
import models.MovieCategory;
import org.apache.jasper.tagplugins.jstl.core.ForEach;

/**
 *
 * @author Dell
 */
public class MovieManager implements DBEntityManager<Movie> {

    private static final Logger LOGGER = Logger.getLogger(MovieManager.class.getName());
    public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS movies (movie_id INT AUTO_INCREMENT ,\n"
            + "  name VARCHAR(45) NOT NULL,\n"
            + "  realse_date DATE NULL,\n"
            + "  mov_length DOUBLE ZEROFILL NULL,\n"
            + "  cat_id INT NOT NULL,\n"
            + "  plot VARCHAR(255) NULL,\n"
            + "  poster VARCHAR(255) NULL,\n"
            + "  trailer VARCHAR(255) NULL,\n"
            + "  is_recommended TINYINT(1) NOT NULL,\n"
            + "  PRIMARY KEY (movie_id),\n"
            + "  INDEX cat_id_idx (cat_id ASC),\n"
            + "  CONSTRAINT cat_id FOREIGN KEY (cat_id) REFERENCES movies_categories (cat_id))";

    private final static String INSERT_TABLE = "INSERT INTO movies (name, realse_date, mov_length, cat_id,"
            + " plot, poster,trailer,is_recommended) values(?,?,?,?,?,?,?,?)";
    private final static String DELET_MOVIE = "DELET from movies WHERE name = (?)";
    private final static String UPDATE_MOVIE = "UPDATE movies SET name = ?, realse_date = ? , mov_length = ?,"
            + " cat_id = ? , plot = ?, poster = ? ,trailer,is_recommended = ? WHERE movie_id = ?";
    private final static String SELECT_ALL_MOVIES = "SELECT * FROM movies M inner "
            + "Join movies_categories C on M.cat_id = C.id ";

    public static String getCREATE_TABLE() {
        return CREATE_TABLE;
    }

    @Override
    public void createTable() {
        DBHelper.executeUpdateStatment(CREATE_TABLE);
    }

    public void addMoviesToDataBase() throws SQLException{

        ArrayList<Movie> moviesToInsert = new ArrayList<Movie>();
        boolean isAddToDB = true;
        // Creating Star Wars: The Force Awakens
        Date release_date = new Date(17/12/2015);
        String plot = "Three decades after the defeat of the Galactic Empire, a new threat arises. The First Order attempts to rule the galaxy and only a rag-tag group of heroes can stop them, along with the help of the Resistance.";
        String posterLink = "http://ia.media-imdb.com/images/M/MV5BOTAzODEzNDAzMl5BMl5BanBnXkFtZTgwMDU1MTgzNzE@._V1_UX182_CR0,0,182,268_AL_.jpg";
        String trailer = "https://www.youtube.com/watch?v=sGbxmsDFVnE";
        MovieCategory category = getMovieCategoryByName("Action");
        
        Movie starWarsTheForceAwakens = new Movie("Star Wars: The Force Awakens", release_date, 131, plot, posterLink, trailer, category, true);
        moviesToInsert.add(starWarsTheForceAwakens);
       
        //Creating Krampus
        release_date = new Date(04/12/2105);
        plot = "A boy who has a bad Christmas ends up accidentally summoning a Christmas demon to his family home.";
        posterLink = "http://ia.media-imdb.com/images/M/MV5BMjk0MjMzMTI3NV5BMl5BanBnXkFtZTgwODEyODkxNzE@._V1_UX182_CR0,0,182,268_AL_.jpg";
        trailer = "https://www.youtube.com/watch?v=h6cVyoMH4QE";
        category = getMovieCategoryByName("Comedy");
        
        Movie Krampus = new Movie("Krampus", release_date, 98, plot, posterLink, trailer, category, true);
        moviesToInsert.add(Krampus);

        
        for (Movie p : moviesToInsert) {
	    isAddToDB = addEntity(p);
            if (isAddToDB == false)
            {
                throw new MySQLDataException();
            }
	}
    
        
    }
    
    @Override
    public boolean addEntity(Movie entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_TABLE);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("dd-MM-yyyy");

            statement.setString(1, entity.getName());
            statement.setString(2, dateformatSql.format(entity.getRelease_date()));
            statement.setDouble(3, (entity.getMovie_length()));
            statement.setInt(4, entity.getCategory().getId());
            statement.setString(5, entity.getPlot());
            statement.setString(6, entity.getPoster());
            statement.setString(7, entity.getTrailer());
            statement.setString(8, Boolean.toString(entity.is_recomanded()));
            statement.execute();
            result = true;
        } catch (ClassNotFoundException | SQLException ex) {
            result = false;
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Movie entity) {
        Connection conn = null;
        boolean result;

        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(UPDATE_MOVIE);
            SimpleDateFormat dateformatSql = new SimpleDateFormat("dd-MM-yyyy");

            statement.setString(1, entity.getName());
            statement.setString(2, dateformatSql.format(entity.getRelease_date()));
            statement.setDouble(3, (entity.getMovie_length()));
            statement.setInt(4, entity.getCategory().getId());
            statement.setString(5, entity.getPlot());
            statement.setString(6, entity.getPoster());
            statement.setString(7, entity.getTrailer());
            statement.setString(8, Boolean.toString(entity.is_recomanded()));
            statement.executeUpdate();
            result = true;
        } catch (ClassNotFoundException | SQLException ex) {
            result = false;
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;

        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Movie entity) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(DELET_MOVIE);
            statement.setString(1, entity.getName());
            statement.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            result = false;
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Movie> allMovies() {
        ArrayList<Movie> allMovies = new ArrayList<Movie>();
        ResultSet rs = null;
        try {
            rs = DBHelper.executeQueryStatment(SELECT_ALL_MOVIES);
            while (rs.next()) {
                Movie movie = getMovieByResultSetLine(rs);
                allMovies.add(movie);
            }
        } catch (Exception e) {
        }
        return allMovies;
    }

    public static Movie getMovieByResultSetLine(ResultSet rs) {
        Movie movie = new Movie();
        try {
            movie.setId(rs.getInt("movie_id"));
            movie.setName(rs.getString("name"));
            movie.setRelease_date(rs.getDate("realse_date"));
            movie.setMovie_length(rs.getDouble("mov_length"));
//            movie.setCategory(MovieCategory.getMovieCategory(rs));
            movie.setPlot(rs.getString("plot"));
            movie.setPoster(rs.getString("poster"));
            movie.setTrailer(rs.getString("trailer"));
            movie.setIs_recomanded(rs.getBoolean("is_recommended"));

        } catch (Exception e) {
        }
        return movie;
    }

}
