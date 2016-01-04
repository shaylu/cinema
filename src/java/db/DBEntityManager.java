/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * @author Dell
 */
public interface DBEntityManager<T extends DBEntity> {
    
    void createTable();
    boolean addEntity(T entity);
    boolean update(T entity);
    void delete(T entity);
    
}