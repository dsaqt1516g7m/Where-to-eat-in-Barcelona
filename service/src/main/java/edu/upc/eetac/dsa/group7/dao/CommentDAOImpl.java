package edu.upc.eetac.dsa.group7.dao;

import edu.upc.eetac.dsa.group7.entity.Comment;
import edu.upc.eetac.dsa.group7.entity.CommentCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alex on 29/11/15.
 */
public class CommentDAOImpl implements CommentDAO {

    @Override
    public Comment createComment(String creator, String restaurant, String title, String comment) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(CommentDAOQuery.CREATE_COMMENT);
            stmt.setString(1, id);
            stmt.setString(2, creator);
            stmt.setString(3, restaurant);
            stmt.setString(4, title);
            stmt.setString(5, comment);
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
    public Comment responseComment(String id, String response) throws SQLException {
        Comment comment = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(CommentDAOQuery.RESPONSE_COMMENT);
            stmt.setString(1, response);
            stmt.setString(3, id);

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
        try {
            connection = Database.getConnection();

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