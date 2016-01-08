/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author shay.lugasi
 */
public class DbManager implements AutoCloseable {

    private static final String MySqlClassName = "com.mysql.jdbc.Driver";
    private static final String MySqlHost = "jdbc:mysql://localhost:3306/";
    private static final String DbName = "CinemaCity";

    private static final String MySqlUsername = "root";
    private static final String MySqlPassword = "1234";

    private MovieCategoriesManager movieCategoriesManager;
    private MoviesManager moviesManager;
    private HallsManager hallsManager;
    private ShowsManager showsManager;
    private OrdersManager ordersManager;
    private CompaniesManager promoCompaniesManager;
    private PromoCategoriesManager promoCategoriesManager;
    private PromosManager promosManager;
    private ReviewsManager reviewsManager;
    private UsersManager usersManager;

    public DbManager() throws ClassNotFoundException, SQLException, Exception {
        initDb();
        usersManager = new UsersManager(this);
        movieCategoriesManager = new MovieCategoriesManager(this);
        moviesManager = new MoviesManager(this);
        hallsManager = new HallsManager(this);
        showsManager = new ShowsManager(this);
        ordersManager = new OrdersManager(this);
        promoCompaniesManager = new CompaniesManager(this);
        promoCategoriesManager = new PromoCategoriesManager(this);
        promosManager = new PromosManager(this);
        reviewsManager = new ReviewsManager(this);

        //promoCategoriesManager.addDefaultValues();
        //movieCategoriesManager.addDefaultValues();
        //moviesManager.addDefaultValues();
        //  hallsManager.addDefaultValues();
    }

    public MovieCategoriesManager getMovieCategoriesManager() {
        return movieCategoriesManager;
    }

    public MoviesManager getMoviesManager() {
        return moviesManager;
    }

    public HallsManager getHallsManager() {
        return hallsManager;
    }

    public ShowsManager getShowsManager() {
        return showsManager;
    }

    public OrdersManager getOrdersManager() {
        return ordersManager;
    }

    public CompaniesManager getPromoCompaniesManager() {
        return promoCompaniesManager;
    }

    public PromoCategoriesManager getPromoCategoriesManager() {
        return promoCategoriesManager;
    }

    public PromosManager getPromosManager() {
        return promosManager;
    }

    public ReviewsManager getReviewsManager() {
        return reviewsManager;
    }

    public UsersManager getUsersManager() {
        return usersManager;
    }

    private void initDb() throws Exception {
        createSchema();
    }

    @Override
    public void close() throws Exception {
    }

    private Connection getConnection(String db_name) throws ClassNotFoundException, SQLException {
        Class.forName(MySqlClassName);
        return DriverManager.getConnection(MySqlHost + db_name, MySqlUsername, MySqlPassword);
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(MySqlClassName);
        return DriverManager.getConnection(MySqlHost + DbName, MySqlUsername, MySqlPassword);
    }

    private boolean executeSql(String db_name, String sql) throws SQLException, ClassNotFoundException {
        Connection conn = getConnection(db_name);
        Statement statement = conn.createStatement();
        boolean result = statement.execute(sql);
        conn.close();

        return result;
    }

    private void createSchema() throws SQLException, InterruptedException, ClassNotFoundException {
        executeSql("", "CREATE SCHEMA IF NOT EXISTS CinemaCity;");
        executeSql(DbName, "CREATE TABLE IF NOT EXISTS movie_categories (cat_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50) NOT NULL UNIQUE);");
        executeSql(DbName, "CREATE TABLE IF NOT EXISTS movies (movie_id INT AUTO_INCREMENT ,\n"
                + "  name VARCHAR(45) NOT NULL,\n"
                + "  release_date DATE NULL,\n"
                + "  mov_length DOUBLE ZEROFILL NULL,\n"
                + "  cat_id INT NOT NULL,\n"
                + "  plot VARCHAR(255) NULL,\n"
                + "  poster VARCHAR(255) NULL,\n"
                + "  trailer VARCHAR(255) NULL,\n"
                + "  is_recommended TINYINT(1) NOT NULL,\n"
                + "  PRIMARY KEY (movie_id),\n"
                + "  INDEX cat_id_idx (cat_id ASC),\n"
                + "  CONSTRAINT cat_id FOREIGN KEY (cat_id) REFERENCES movie_categories (cat_id))");
        executeSql(DbName, "CREATE TABLE IF NOT EXISTS halls ("
                + "hall_id INT NOT NULL UNIQUE,"
                + "num_of_seats INT ZEROFILL NOT NULL,"
                + "PRIMARY KEY (hall_id));");
        executeSql(DbName, "CREATE TABLE IF NOT EXISTS shows(\n"
                + "  show_id INT NOT NULL INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "  movie_id INT NOT NULL,\n"
                + "  hall_id INT NOT NULL,\n"
                + "  show_date DATE NOT NULL,\n"
                + "  show_time TIME NOT NULL,\n"
                + "  num_of_seats_left INT ZEROFILL NOT NULL,\n"
                + "  price_per_seat DOUBLE ZEROFILL NOT NULL,\n"
                + "  PRIMARY KEY (show_id),\n"
                + "  INDEX movie_id_idx (movie_id ASC),\n"
                + "  INDEX hall_id_idx (hall_id ASC),\n"
                + "  CONSTRAINT movie_id FOREIGN KEY (movie_id) REFERENCES movies (movie_id),\n"
                + "  CONSTRAINT hall_id FOREIGN KEY (hall_id) REFERENCES halls (hall_id)\n)");
        executeSql(DbName, "CREATE TABLE IF NOT EXISTS orders(order_id INT AUTO_INCREMENT,\n"
                + "  client_id VARCHAR(9) NOT NULL,\n"
                + "  fname VARCHAR(45) NOT NULL,\n"
                + "  lname VARCHAR(45) NOT NULL,\n"
                + "  email VARCHAR(45) NOT NULL,\n"
                + "  phone VARCHAR(45) NOT NULL,\n"
                + "  show_id INT NOT NULL,\n"
                + "  num_of_seats INT ZEROFILL NOT NULL,\n"
                + "  total_payment DOUBLE ZEROFILL NOT NULL,\n"
                + "  credit_card_last_digit VARCHAR(4) NOT NULL,\n"
                + "  exp_date_month INT NOT NULL,\n"
                + "  exp_date_year INT NOT NULL,\n"
                + "  order_date DATE NOT NULL,\n"
                + "  PRIMARY KEY (order_id),\n"
                + "  INDEX show_id_idx (show_id ASC),\n"
                + "  CONSTRAINT show_id\n"
                + "    FOREIGN KEY (show_id)\n"
                + "    REFERENCES shows (show_id)\n"
                + "    ON DELETE NO ACTION\n"
                + "    ON UPDATE NO ACTION)");
        executeSql(DbName, "CREATE TABLE IF NOT EXISTS companies (\n"
                + "  comp_id INT AUTO_INCREMENT ,\n"
                + "  name VARCHAR(50) NOT NULL,\n"
                + "  address VARCHAR(250) NOT NULL,\n"
                + "  about_text VARCHAR(500) NULL,\n"
                + "  PRIMARY KEY (comp_id),\n"
                + "  UNIQUE INDEX comp_id_UNIQUE (comp_id ASC))");
        executeSql(DbName, "CREATE TABLE IF NOT EXISTS promotion_categories(\n"
                + "  promo_cat_id INT AUTO_INCREMENT,\n"
                + "  name VARCHAR(100) NOT NULL,\n"
                + "  PRIMARY KEY (promo_cat_id));");
        executeSql(DbName, "CREATE TABLE IF NOT EXISTS promotions(\n"
                + "  promo_id INT AUTO_INCREMENT,\n"
                + "  comp_id INT NOT NULL,\n"
                + "  promo_cat_id INT NOT NULL,\n"
                + "  description VARCHAR(500) NULL,\n"
                + "  exp_date DATE NULL,\n"
                + "  promo_code VARCHAR(50) NULL,\n"
                + "  image VARCHAR(50) NULL,\n"
                + "  PRIMARY KEY (promo_id),\n"
                + "  INDEX promo_comp_idx (comp_id ASC),\n"
                + "  INDEX promo_cat_id_idx (promo_cat_id ASC),\n"
                + "  CONSTRAINT promo_comp\n"
                + "    FOREIGN KEY (comp_id)\n"
                + "    REFERENCES companies (comp_id)\n"
                + "    ON DELETE NO ACTION\n"
                + "    ON UPDATE NO ACTION,\n"
                + "  CONSTRAINT promo_cat_id\n"
                + "    FOREIGN KEY(promo_cat_id)\n"
                + "    REFERENCES promotion_categories (promo_cat_id)\n"
                + "    ON DELETE NO ACTION\n"
                + "    ON UPDATE NO ACTION)");
        executeSql(DbName, "CREATE TABLE IF NOT EXISTS reviews (\n"
                + "  rev_id INT NOT NULL AUTO_INCREMENT,\n"
                + "  order_id INT NOT NULL,\n"
                + "  rank DOUBLE ZEROFILL NULL,\n"
                + "  review_text VARCHAR(500) NULL,\n"
                + "  review_date DATE NOT NULL,\n"
                + "  PRIMARY KEY (rev_id), INDEX order_id_idx (order_id ASC),\n"
                + "  CONSTRAINT order_id FOREIGN KEY (order_id) REFERENCES orders (Order_id))");
        executeSql(DbName, "CREATE TABLE IF NOT EXISTS users (\n"
                + "  fldUserId INT NOT NULL AUTO_INCREMENT,\n"
                + "  fldPassword VARCHAR(50) NOT NULL,\n"
                + "  fldUserName VARCHAR(50) NOT NULL UNIQUE,\n"
                + "  fldFname VARCHAR(50) NOT NULL,\n"
                + "  fldLname VARCHAR(50) NOT NULL,\n"
                + "  PRIMARY KEY (fldUserId))");
        executeSql(DbName, "CREATE OR REPLACE VIEW next_three_hours AS SELECT m.movie_id FROM movies as m INNER JOIN shows as s ON s.movie_id = m.movie_id WHERE s.show_date >= NOW() AND s.show_date <= NOW() + INTERVAL 3 HOUR;");
    }

}
