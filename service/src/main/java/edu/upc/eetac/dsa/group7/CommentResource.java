package edu.upc.eetac.dsa.group7;

import edu.upc.eetac.dsa.group7.dao.CommentDAO;
import edu.upc.eetac.dsa.group7.dao.CommentDAOImpl;
import edu.upc.eetac.dsa.group7.dao.RestaurantDAO;
import edu.upc.eetac.dsa.group7.dao.RestaurantDAOImpl;
import edu.upc.eetac.dsa.group7.entity.AuthToken;
import edu.upc.eetac.dsa.group7.entity.Comment;
import edu.upc.eetac.dsa.group7.entity.Restaurant;

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
        if(title==null || commentBody == null)
            throw new BadRequestException("all parameters are mandatory");
        if (likes!=1 && likes!=-1 && likes!=0)
            throw new BadRequestException("Likes must be 1, -1 or 0");
        CommentDAO commentDAO = new CommentDAOImpl();
        Comment comment = null;
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        Restaurant restaurant = null;
        AuthToken authenticationToken = null;
        try {
            comment = commentDAO.createComment(securityContext.getUserPrincipal().getName(), restaurantid, title, commentBody);
            restaurant = restaurantDAO.voteRestaurant(restaurantid, likes);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + comment.getId());
        return Response.created(uri).type(WhereMediaType.WHERE_COMMENT).entity(comment).build();
    }
}