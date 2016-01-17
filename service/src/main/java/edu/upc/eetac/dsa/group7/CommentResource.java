package edu.upc.eetac.dsa.group7;
import edu.upc.eetac.dsa.group7.dao.*;
import edu.upc.eetac.dsa.group7.entity.AuthToken;
import edu.upc.eetac.dsa.group7.entity.Comment;
import edu.upc.eetac.dsa.group7.entity.CommentCollection;
import edu.upc.eetac.dsa.group7.entity.Restaurant;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
/**
 * Created by Alex on 7/12/15.
 */
//Methods related to comments
//Path with the commented restaurant identifier
@Path("/restaurant/{restaurantid}/comments")
public class CommentResource {
    @Context
    private SecurityContext securityContext;
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(WhereMediaType.WHERE_COMMENT)
    //Create a restaurant giving title, comment and like/dislike (+1/-1)
    public Response createComment(@PathParam("restaurantid") String restaurantid, @FormParam("title") String title, @FormParam("comment") String commentBody, @FormParam("likes") int likes, @Context UriInfo uriInfo) throws URISyntaxException {
        if (title == null || commentBody == null) {
            //check if all the parameters aren't null
            throw new BadRequestException("all parameters are mandatory");
        }
        if (likes != 1 && likes != -1 && likes != 0) {
            //check if the likes have a correct value
            throw new BadRequestException("Likes must be 1, -1 or 0");
        }
        //initiates all the variables
        CommentDAO commentDAO = new CommentDAOImpl();
        Comment comment = null;
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        Restaurant restaurant = null;
        AuthToken authenticationToken = null;
        try {
            //call the method to create a restaurant
            //using the securityContext we can extract the id of the creator
            comment = commentDAO.createComment(securityContext.getUserPrincipal().getName(), restaurantid, title, commentBody, likes);
            restaurant = restaurantDAO.voteRestaurant(restaurantid, likes);
        } catch (SQLException e) {
            //send an error if the insertion fails
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + comment.getId());
        //send back the created comment
        return Response.created(uri).type(WhereMediaType.WHERE_COMMENT).entity(comment).build();
    }
    @GET
    @Produces(WhereMediaType.WHERE_COMMENT_COLLECTION)
    //method to get all the comments
    public CommentCollection getComments(@PathParam("restaurantid") String restaurantid) {
        //initiates variables and call the function to print all the comments of the restaurant
        CommentCollection commentCollection = null;
        CommentDAO commentDAO = new CommentDAOImpl();
        try {
            commentCollection = commentDAO.getComments(restaurantid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        //return all the comments of this restaurant
        return commentCollection;
    }
    @Path("/{id}")
    @GET
    @Produces(WhereMediaType.WHERE_COMMENT)
    //method to get the only one comment giving its id
    public Comment getComment(@PathParam("id") String id) {
        Comment comment = null;
        CommentDAO commentDAO = new CommentDAOImpl();
        try {
            //try to search a commment with this id
            comment = commentDAO.getCommentById(id);
            if (comment == null)
                //return an error if not exists
                throw new NotFoundException("Comment with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            //return an error if the sql query fails
            throw new InternalServerErrorException();
        }
        return comment;
    }
    @Path("/{id}")
    @DELETE
    //method to delete a comment giving its id
    public void deleteComment(@PathParam("id") String id) {
        //store the id from the user, using securityContext
        boolean allow = false;
        String userid = securityContext.getUserPrincipal().getName();
        CommentDAO commentDAO = new CommentDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        try {

            //check if the comment was created by the same user, or if you are the admin
            String creator = commentDAO.getCommentById(id).getCreator();
            if (userid.equals(creator)){
                allow = true;
            }
            if (allow == false && userDAO.admin(securityContext.getUserPrincipal().getName()) == false) {
                throw new ForbiddenException("operation not allowed");
                //throw an error if any condition isn't acomplished
            }
            if (!commentDAO.deleteComment(id))
                throw new NotFoundException("Comment with id = " + id + " doesn't exist");
            //throw an error if the comment doesn't exists
        } catch (SQLException e) {
            //Throw an error if the SQL fails
            throw new InternalServerErrorException();
        }
    }
    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(WhereMediaType.WHERE_COMMENT)
    //only the creator of the resturant can response a comment
    //response a comment giving its id and the response
    public void responseComment(@PathParam("restaurantid") String restaurantid, @PathParam("id") String id, @FormParam("response") String response) {
        //check if the response is valid
        if (response == null)
            throw new BadRequestException("response cannot be null");
        //initiates all the objects needed
        UserDAO userDAO = new UserDAOImpl();
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        CommentDAO commentDAO = new CommentDAOImpl();
        Comment comment;
        try {
            //get the user id and the owner of the restaurant
            String ownerid = restaurantDAO.getRestaurantById(restaurantid).getOwner();
            String userid = securityContext.getUserPrincipal().getName();
            if (ownerid.equals(userid)) {
                //update the comment setting the response
                comment = commentDAO.getCommentById(id);
                if (comment == null) {
                    //throw exception if the comment doesn't exists
                    throw new NotFoundException("Comment with id =" + id + " doesn't exists");
                }
                commentDAO.responseComment(id, response);
            } else {
                //throw a bad request if you aren't the owner of the restaurant
                throw new ForbiddenException("You must be the owner of the restaurant to write a response");
            }
        } catch (SQLException e) {
            //throw error if any SQL fails
            throw new InternalServerErrorException();
        }
        //return the full comment, now with the response
        return;
    }
}