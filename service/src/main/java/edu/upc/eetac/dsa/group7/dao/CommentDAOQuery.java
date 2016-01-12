package edu.upc.eetac.dsa.group7.dao;
/**
 * Created by Alex on 29/11/15.
 */
public interface CommentDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_COMMENT = "insert into comments (id, creator, restaurant, title, comment, likes, response) values (UNHEX(?), UNHEX(?), UNHEX(?), ?, ?, ?, ?)";
    public final static String GET_COMMENT_BY_ID = "select hex(id) as id, hex(creator) as creator, hex(restaurant) as restaurant, title, comment, likes, response, creation_timestamp from comments where id=unhex(?)";
    public final static String GET_COMMENTS = "select hex(id) as id, hex(creator) as creator, hex(restaurant) as restaurant, title, comment, likes, response, creation_timestamp from comments where restaurant=unhex(?)order by creation_timestamp limit 5";
    public final static String RESPONSE_COMMENT = "update comments set response=? where id=unhex(?) ";
    public final static String DELETE_COMMENT = "delete from comments where id=unhex(?)";
    public final static String VALORATION = "insert into like_restaurant (restaurant, user) values (UNHEX(?), UNHEX(?))";
    public final static String CHECK_VALORATION = "select hex(restaurant) from like_restaurant where user=unhex(?)";
    public final static String REMOVE_VALORATION = "delete from like_restaurant where restaurant=unhex(?) and user=unhex(?)";
}