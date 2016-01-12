package edu.upc.eetac.dsa.group7.dao;
import edu.upc.eetac.dsa.group7.entity.Comment;
import edu.upc.eetac.dsa.group7.entity.CommentCollection;
import java.sql.SQLException;
/**
 * Created by Alex on 29/11/15.
 */
public interface CommentDAO {
    public Comment createComment(String creator, String restaurant, String title, String comment, int likes) throws SQLException;
    public Comment getCommentById(String id) throws SQLException;
    public CommentCollection getComments(String restaurantid) throws SQLException;
    public Comment responseComment(String id, String response) throws SQLException;
    public boolean deleteComment(String id) throws SQLException;
}