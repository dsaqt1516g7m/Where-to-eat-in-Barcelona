package edu.upc.eetac.dsa.group7;

import edu.upc.eetac.dsa.group7.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.group7.dao.UserAlreadyExistsException;
import edu.upc.eetac.dsa.group7.dao.UserDAO;
import edu.upc.eetac.dsa.group7.dao.UserDAOImpl;
import edu.upc.eetac.dsa.group7.entity.AuthToken;
import edu.upc.eetac.dsa.group7.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Alex on 30/11/15.
 */

@Path("users")
public class UserResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(WhereMediaType.WHERE_AUTH_TOKEN)
    //method to create a user: login id, password, email and fullname needed
    public Response registerUser(@FormParam("loginid") String loginid, @FormParam("password") String password, @FormParam("email") String email, @FormParam("fullname") String fullname, @Context UriInfo uriInfo) throws URISyntaxException {
        if (loginid == null || password == null || email == null || fullname == null) {
            throw new BadRequestException("all parameters are mandatory");
            //check parameters
        }
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        AuthToken authenticationToken = null;
        try {
            //call the function to create the user
            user = userDAO.createUser(loginid, password, email, fullname);
            authenticationToken = (new AuthTokenDAOImpl()).createAuthToken(user.getId());
        } catch (UserAlreadyExistsException e) {
            //Catch an error if the user exists
            throw new WebApplicationException("loginid already exists", Response.Status.CONFLICT);
        } catch (SQLException e) {
            //catch sql fails
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + user.getId());
        return Response.created(uri).type(WhereMediaType.WHERE_AUTH_TOKEN).entity(authenticationToken).build();
    }

    @Path("/{id}")
    @GET
    @Produces(WhereMediaType.WHERE_USER)
    //method to print one user, by id
    public User getUser(@PathParam("id") String id) {
        User user = null;
        try {
            user = (new UserDAOImpl()).getUserById(id);
        } catch (SQLException e) {
            //catch sql fails
            throw new InternalServerErrorException(e.getMessage());
        }
        if (user == null) {
            //throw an error if the user doesn't exists
            throw new NotFoundException("User with id = " + id + " doesn't exist");
        }
        return user;
        //print the returned user
    }

    @Path("/{id}")
    @PUT
    @Consumes(WhereMediaType.WHERE_USER)
    @Produces(WhereMediaType.WHERE_USER)
    //method to update user. Only email and fullname can be updated
    //id and full user are needed
    public User updateUser(@PathParam("id") String id, User user) {
        if (user == null) {
            throw new BadRequestException("entity is null");
            //check if user is null
        }
        if (!id.equals(user.getId())) {
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");
            //check if path id and userid matchs
        }

        String userid = securityContext.getUserPrincipal().getName();
        //store userid using securityContext, from securityContext
        if (!userid.equals(id)) {
            throw new ForbiddenException("operation not allowed");
            //throw error if userid and id doesn't match
        }

        UserDAO userDAO = new UserDAOImpl();
        try {
            //call the function to update
            user = userDAO.updateProfile(userid, user.getEmail(), user.getFullname());
            if (user == null) {
                //throw error if user with this id doen't exists
                throw new NotFoundException("User with id = " + id + " doesn't exist");
            }
        } catch (SQLException e) {
            //catch sql exceptions
            throw new InternalServerErrorException();
        }
        //return user to print it
        return user;
    }

    @Path("/{id}")
    @DELETE
    //method to delete user. Only id is needed
    public void deleteUser(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        //get userid using securityContext from authtoken
        if (!userid.equals(id)) {
            //if userid and id doesn't match, throw error
            throw new ForbiddenException("operation not allowed");
        }
        UserDAO userDAO = new UserDAOImpl();
        try {
            if (!userDAO.deleteUser(id)) {
                //try to delete, if not found, throw error
                throw new NotFoundException("User with id = " + id + " doesn't exist");
            }
        } catch (SQLException e) {
            //catch SQL Exception
            throw new InternalServerErrorException();
        }
    }
}