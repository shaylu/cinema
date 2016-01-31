/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author shay.lugasi
 */
public class ReviewOnly {
    public int rev_id;
    public int rank;
    public Date review_date;
    public String review_text;

    public ReviewOnly(int rev_id, int rank, Date review_date, String review_text) {
        this.rev_id = rev_id;
        this.rank = rank;
        this.review_date = review_date;
        this.review_text = review_text;
    }
    
    public ReviewOnly(Review review) {
        this.rev_id = review.getId();
        this.rank = (int)review.getRank();
        this.review_date = review.getDate();
        this.review_text = review.getText();
    }
}
