package edu.upc.eetac.dsa.group7.dao;

import edu.upc.eetac.dsa.group7.entity.Comment;
import edu.upc.eetac.dsa.group7.entity.CommentCollection;
import edu.upc.eetac.dsa.group7.entity.Restaurant;

import javax.ws.rs.BadRequestException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alex on 29/11/15.
 */
public class CommentDAOImpl implements CommentDAO {

    @Override
    public Comment createComment(String creator, String restaurant, String title, String comment, int likes) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        String restaurantid = null;
        String response="";
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(CommentDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();
            stmt=null;

            stmt = connection.prepareStatement(CommentDAOQuery.CHECK_VALORATION);
            stmt.setString(1, creator);
            ResultSet rs2 = stmt.executeQuery();
            if (rs2.next()) {
                restaurantid = rs2.getString(1);
                if (restaurantid.equals(restaurant))
                    throw new BadRequestException("You cannot comment a restaurant twice");
            }
                stmt = null;

                stmt = connection.prepareStatement(CommentDAOQuery.CREATE_COMMENT);
                stmt.setString(1, id);
                stmt.setString(2, creator);
                stmt.setString(3, restaurant);
                stmt.setString(4, title);
                stmt.setString(5, comment);
                stmt.setInt(6, likes);
                stmt.setString(7, response);
                stmt.executeUpdate();

            stmt = null;

            stmt = connection.prepareStatement(CommentDAOQuery.VALORATION);
            stmt.setString(1, restaurant);
            stmt.setString(2, creator);
            stmt.executeUpdate();
            stmt = null;

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getCommentById(id);
    }

    @Override
    public Comment getCommentById(String id) throws SQLException {
        Comment comment = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(CommentDAOQuery.GET_COMMENT_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                comment = new Comment();
                comment.setId(rs.getString("id"));
                comment.setCreator(rs.getString("creator"));
                comment.setRestaurant(rs.getString("restaurant"));
                comment.setTitle(rs.getString("title"));
                comment.setComment(rs.getString("comment"));
                comment.setLikes(rs.getInt("likes"));
                comment.setResponse(rs.getString("response"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return comment;
    }

    @Override
    public CommentCollection getComemnts() throws SQLException {
        CommentCollection commentCollection = new CommentCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(CommentDAOQuery.GET_COMMENTS);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getString("id"));
                comment.setCreator(rs.getString("creator"));
                comment.setRestaurant(rs.getString("restaurant"));
                comment.setTitle(rs.getString("title"));
                comment.setComment(rs.getString("comment"));
                comment.setResponse(rs.getString("response"));
                comment.setLikes(rs.getInt("likes"));
                comment.setCreation_timestamp(rs.getTimestamp("creation_timestamp").getTime());

                commentCollection.getComments().add(comment);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return commentCollection;
    }

    @Override
    public Comment responseComment(String id, String response) throws SQLException{
        Comment comment = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(CommentDAOQuery.RESPONSE_COMMENT);
            stmt.setString(1, response);
            stmt.setString(2, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                comment = getCommentById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return comment;
    }

    @Override
    public boolean deleteComment(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        int likes=0;
        RestaurantDAO restaurantDao = new RestaurantDAOImpl();
        Comment comment=getCommentById(id);
        Restaurant restaurant=restaurantDao.getRestaurantById(comment.getRestaurant());
        int restaurantLikes=restaurant.getLikes();
        int likesComment= comment.getLikes();
        likes = restaurantLikes - likesComment;

        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(RestaurantDAOQuery.VOTE_RESTAURANT);
            stmt.setInt(1, likes);
            stmt.setString(2, comment.getRestaurant());
            stmt.executeUpdate();
            stmt=null;

            stmt = connection.prepareStatement(CommentDAOQuery.REMOVE_VALORATION);
            stmt.setString(1, comment.getRestaurant());
            stmt.setString(2, comment.getCreator());
            stmt.executeUpdate();
            stmt=null;

            stmt = connection.prepareStatement(CommentDAOQuery.DELETE_COMMENT);
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