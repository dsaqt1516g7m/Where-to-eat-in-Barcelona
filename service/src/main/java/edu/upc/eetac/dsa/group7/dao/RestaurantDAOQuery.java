package edu.upc.eetac.dsa.group7.dao;

/**
 * Created by Alex on 29/11/15.
 */
public interface RestaurantDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_RESTAURANT = "insert into restaurants (id, name, description, avgprice, owner, likes, address, phone, lat, lng) values (UNHEX(?), ?, ?, ?, UNHEX(?), ?, ?, ?, ?, ?)";
    public final static String GET_RESTAURANT_BY_ID = "select hex(r.id) as id, hex(r.owner) as owner, r.name, r.description, r.avgprice, r.likes, r.address, r.phone, r.lat, r.lng, u.fullname from restaurants r, users u where r.id=unhex(?) and u.id=r.owner";
    public final static String GET_RESTAURANTS = "select hex(id) as id, hex(owner) as owner, name, description, avgprice, likes, address, phone, lat, lng from restaurants";
    public final static String UPDATE_RESTAURANT = "update restaurants set description=?, avgprice=?, phone=? where id=unhex(?) ";
    public final static String DELETE_RESTAURANT = "delete from restaurants where id=unhex(?)";
    public final static String VOTE_RESTAURANT = "update restaurants set likes=? where id=unhex(?)";

}
