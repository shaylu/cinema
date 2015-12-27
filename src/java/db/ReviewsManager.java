/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.logging.Logger;
import models.Review;

/**
 *
 * @author Liraz
 */
public class ReviewsManager implements DBEntityManager<Review>{

      private static final Logger LOGGER = Logger.getLogger(ReviewsManager.class.getName());
    private final static String CREATE_TABLE = " CREATE TABLE IF NOT EXISTS reviews (hall_id INT NOT NULL AUTO_INCREMENT, num_of_seats INT ZEROFILL NOT NULL, PRIMARY KEY (hall_id))";
    private final static String INSERT_TABLE = "INSERT INTO hall (hall_id, num_of_seats) values(?,?)";
    private final static String DELET_HALL = "DELET from reviews WHERE hall_id = (?)";

    @Override
    public void createTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addEntity(Review entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Review entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Review entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
