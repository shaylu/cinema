/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.mysql;

import java.sql.SQLException;

/**
 *
 * @author shay.lugasi
 */
public class ReviewsManager extends DbManagerEntity {
    public ReviewsManager(DbManager manager) {
        this.manager = manager;
    }
    
    public int add(int order_id, int rank, String review_text)  throws ClassNotFoundException, SQLException{
        // dont forget to set the review date using the now() function
        throw new UnsupportedOperationException();
    }
}
