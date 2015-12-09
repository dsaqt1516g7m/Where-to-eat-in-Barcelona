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
@Path("/restaurant/{restaurantid}/comments")
public class CommentResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(WhereMediaType.WHERE_COMMENT)
    public Response createComment(@PathParam("restaurantid") String restaurantid, @FormParam("title") String title, @FormParam("comment") String commentBody, @FormParam("likes") int likes, @Context UriInfo uriInfo) throws URISyntaxException {
        if (title == null || commentBody == null)
            throw new BadRequestException("all parameters are mandatory");
        if (likes != 1 && likes != -1 && likes != 0)
            throw new BadRequestException("Likes must be 1, -1 or 0");
        CommentDAO commentDAO = new CommentDAOImpl();
        Comment comment = null;
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        Restaurant restaurant = null;
        AuthToken authenticationToken = null;
        try {
            comment = commentDAO.createComment(securityContext.getUserPrincipal().getName(), restaurantid, title, commentBody, likes);
            restaurant = restaurantDAO.voteRestaurant(restaurantid, likes);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + comment.getId());
        return Response.created(uri).type(WhereMediaType.WHERE_COMMENT).entity(comment).build();
    }

    @GET
    @Produces(WhereMediaType.WHERE_COMMENT_COLLECTION)
    public CommentCollection getComments() {
        CommentCollection commentCollection = null;
        CommentDAO commentDAO = new CommentDAOImpl();
        try {
            commentCollection = commentDAO.getComemnts();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return commentCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(WhereMediaType.WHERE_COMMENT)
    public Comment getComment(@PathParam("id") String id) {
        Comment comment = null;
        CommentDAO commentDAO = new CommentDAOImpl();
        try {
            comment = commentDAO.getCommentById(id);
            if (comment == null)
                throw new NotFoundException("Sting with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return comment;
    }

    @Path("/{id}")
    @DELETE
    public void deleteComment(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        CommentDAO commentDAO = new CommentDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        try {
            String creator = commentDAO.getCommentById(id).getCreator();
            if (!userid.equals(creator) || userDAO.admin(securityContext.getUserPrincipal().getName()) == true)
                throw new ForbiddenException("operation not allowed");
            if (!commentDAO.deleteComment(id))
                throw new NotFoundException("Comment with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(WhereMediaType.WHERE_COMMENT)
    public Comment responseComment(@PathParam("restaurantid") String restaurantid, @PathParam("id") String id, @FormParam("response") String response) {
        if (response == null)
            throw new BadRequestException("response cannot be null");
        UserDAO userDAO = new UserDAOImpl();
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        CommentDAO commentDAO = new CommentDAOImpl();
        Comment comment;
        try {
            String ownerid= restaurantDAO.getRestaurantById(restaurantid).getOwner();
            String userid = securityContext.getUserPrincipal().getName();
            if (ownerid.equals(userid)) {
                comment = commentDAO.responseComment(id, response);
                if (comment == null)
                    throw new NotFoundException("Comment with id =" + id + " doesn't exists");
            } else {
                throw new BadRequestException("You must be the owner of the restaurant to write a response");
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return comment;
    }
}