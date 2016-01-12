package edu.upc.eetac.dsa.group7.dao;

import edu.upc.eetac.dsa.group7.db.Database;
import edu.upc.eetac.dsa.group7.entity.Restaurant;
import edu.upc.eetac.dsa.group7.entity.RestaurantCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alex on 29/11/15.
 */
public class RestaurantDAOImpl implements RestaurantDAO {

    @Override
    public Restaurant createRestaurant(String name, String description, Float avgprice, String owner, String address, String phone, float lat, float lng) throws SQLException {
        //Creation of the restaurant
        //Initialization of the variables and the database connection
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        int likes = 0;
        //set likes to 0
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RestaurantDAOQuery.UUID);
            //creation of the restaurant id
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(RestaurantDAOQuery.CREATE_RESTAURANT);
            //store data in database
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, description);
            stmt.setFloat(4, avgprice);
            stmt.setString(5, owner);
            stmt.setInt(6, likes);
            stmt.setString(7, address);
            stmt.setString(8, phone);
            stmt.setFloat(9, lat);
            stmt.setFloat(10, lng);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
            //throw error if sql fails
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                //close the connection
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        //return the restaurant to print it
        return getRestaurantById(id);
    }

    @Override
    public Restaurant getRestaurantById(String id) throws SQLException {
        Restaurant restaurant = null;
        //Initialization of parameters

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RestaurantDAOQuery.GET_RESTAURANT_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            //store data from database
            if (rs.next()) {
                restaurant = new Restaurant();
                restaurant.setId(rs.getString("id"));
                restaurant.setName(rs.getString("name"));
                restaurant.setDescription(rs.getString("description"));
                restaurant.setAvgprice(rs.getFloat("avgprice"));
                restaurant.setOwner(rs.getString("owner"));
                restaurant.setLikes(rs.getInt("likes"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setLat(rs.getFloat("lat"));
                restaurant.setLng(rs.getFloat("lng"));
            }
        } catch (SQLException e) {
            throw e;
            //catch errors
        } finally {
            //close connection
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        //return data for printing
        return restaurant;
    }

    @Override
    public RestaurantCollection getRestaurants() throws SQLException {
        RestaurantCollection restaurantCollection = new RestaurantCollection();
        //Method to return the full collection of restaurants
        //Initialization of the database collection

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(RestaurantDAOQuery.GET_RESTAURANTS);

            //Using a while to store all the data from database
            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(rs.getString("id"));
                restaurant.setName(rs.getString("name"));
                restaurant.setDescription(rs.getString("description"));
                restaurant.setAvgprice(rs.getFloat("avgprice"));
                restaurant.setOwner(rs.getString("owner"));
                restaurant.setLikes(rs.getInt("likes"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setLat(rs.getFloat("lat"));
                restaurant.setLng(rs.getFloat("lng"));

                restaurantCollection.getRestaurants().add(restaurant);
            }
        } catch (SQLException e) {
            throw e;
            //Catching errors
        } finally {
            //closing connection
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        //return the collection to print it
        return restaurantCollection;
    }

    @Override
    //method to update parameters, needed: id, description, avg price and phone. Owner or admin permissions needed.
    public Restaurant updateRestaurant(String id, String description, Float avgprice, String phone) throws SQLException {
        Restaurant restaurant = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        //Initialization of parameters for connection
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RestaurantDAOQuery.UPDATE_RESTAURANT);
            //Update parameters from database
            stmt.setString(1, description);
            stmt.setFloat(2, avgprice);
            stmt.setString(3, phone);
            stmt.setString(4, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                restaurant = getRestaurantById(id);
            //try to get the restaurant
        } catch (SQLException e) {
            throw e;
            //catching errors
        } finally {
            if (stmt != null) stmt.close();
            //Closing connection
            if (connection != null) connection.close();
        }
        return restaurant;
        //returning restaurant for printing
    }

    @Override
    //function to delete restaurants. Owner or admin permissions needed.
    public boolean deleteRestaurant(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        //initialiaztion of database
        try {
            connection = Database.getConnection();

            //delete restaurant
            stmt = connection.prepareStatement(RestaurantDAOQuery.DELETE_RESTAURANT);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            //throw error if fails
            throw e;
        } finally {
            //close connection
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    //method to add a entry in relational database table comment-restaurat-user
    public Restaurant voteRestaurant(String id, int likes) throws SQLException {
        Restaurant restaurant = null;
        //Initialization of database

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            //get the restaurant voted

            stmt = connection.prepareStatement(RestaurantDAOQuery.GET_RESTAURANT_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                restaurant = new Restaurant();
                restaurant.setLikes(rs.getInt("likes"));
            }
        } catch (SQLException e) {
            throw e;
            //catch sql errors
        } finally {
            //close the connection
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        int likesResult = restaurant.getLikes() + likes;
        //calculate the current likes
        restaurant = null;
        connection = null;
        stmt = null;

        try {
            connection = Database.getConnection();
            //reinitialize the connection with database

            stmt = connection.prepareStatement(RestaurantDAOQuery.VOTE_RESTAURANT);

            stmt.setInt(1, likesResult);
            //set the likes calculated
            stmt.setString(2, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                restaurant = getRestaurantById(id);
        } catch (SQLException e) {
            //catch sql exception again
            throw e;
        } finally {
            //close the connection
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return restaurant;
        //return the restaurant to print
    }

    @Override
    public RestaurantCollection searchRestaurant(String word) throws SQLException {
        RestaurantCollection restaurantCollection = new RestaurantCollection();
        //Initialization of parameters

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RestaurantDAOQuery.SEARCH_RESTAURANT);
            stmt.setString(1, word);

            ResultSet rs = stmt.executeQuery();
            //store data from database
            boolean first = true;
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(rs.getString("id"));
                restaurant.setName(rs.getString("name"));
                restaurant.setDescription(rs.getString("description"));
                restaurant.setAvgprice(rs.getFloat("avgprice"));
                restaurant.setOwner(rs.getString("owner"));
                restaurant.setLikes(rs.getInt("likes"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setLat(rs.getFloat("lat"));
                restaurant.setLng(rs.getFloat("lng"));

                restaurantCollection.getRestaurants().add(restaurant);
            }
        } catch (SQLException e) {
            throw e;
            //catch errors
        } finally {
            //close connection
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        //return data for printing
        return restaurantCollection;
    }
}