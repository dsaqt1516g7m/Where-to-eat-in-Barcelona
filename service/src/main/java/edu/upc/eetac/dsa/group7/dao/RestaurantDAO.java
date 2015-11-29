package edu.upc.eetac.dsa.group7.dao;

import edu.upc.eetac.dsa.group7.entity.Restaurant;
import edu.upc.eetac.dsa.group7.entity.RestaurantCollection;

import java.sql.SQLException;

/**
 * Created by Alex on 29/11/15.
 */
public interface RestaurantDAO {
    public Restaurant createRestaurant(String name, String description, Float avgprice, String owner, int likes, String address, String phone, float lat, float lng) throws SQLException;
    public Restaurant getRestaurantById(String id) throws SQLException;
    public RestaurantCollection getRestaurants() throws SQLException;
    public Restaurant updateRestaurant(String id, String phone, String description, Float avgprice) throws SQLException;
    public boolean deleteRestaurant(String id) throws SQLException;
}