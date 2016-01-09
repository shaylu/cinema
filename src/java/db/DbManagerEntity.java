/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.ResultSet;

/**
 *
 * @author shay.lugasi
 */
public abstract class DbManagerEntity {
    DbManager manager;
    
    public int getNumberRows(ResultSet rs){
    try{
       if(rs.last()){
          return rs.getRow();
       } else {
           return 0; //just cus I like to always do some kinda else statement.
       }
    } catch (Exception e){
       System.out.println("Error getting row count");
       e.printStackTrace();
    }
    return 0;
}
    
}
