package edu.upc.eetac.dsa.group7.dao;

import edu.upc.eetac.dsa.group7.entity.Restaurant;
import edu.upc.eetac.dsa.group7.entity.RestaurantCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 * Created by Alex on 29/11/15.
 */
public class RestaurantDAOImpl implements RestaurantDAO {

    @Override
    public Restaurant createRestaurant(String name, String description, Float avgprice, String owner, String address, String phone, float lat, float lng) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        int likes=0;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(RestaurantDAOQuery.CREATE_RESTAURANT);
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
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getRestaurantById(id);
    }

    @Override
    public Restaurant getRestaurantById(String id) throws SQLException {
        Restaurant restaurant = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RestaurantDAOQuery.GET_RESTAURANT_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
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
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return restaurant;
    }

    @Override
    public RestaurantCollection getRestaurants() throws SQLException {
        RestaurantCollection restaurantCollection = new RestaurantCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(RestaurantDAOQuery.GET_RESTAURANTS);

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
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return restaurantCollection;
    }

    @Override
    public Restaurant updateRestaurant(String id, String phone, String description, Float avgprice) throws SQLException {
        Restaurant restaurant = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RestaurantDAOQuery.UPDATE_RESTAURANT);
            stmt.setString(1, phone);
            stmt.setString(2, description);
            stmt.setFloat(3, avgprice);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                restaurant = getRestaurantById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return restaurant;
    }

    @Override
    public boolean deleteRestaurant(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RestaurantDAOQuery.DELETE_RESTAURANT);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

}