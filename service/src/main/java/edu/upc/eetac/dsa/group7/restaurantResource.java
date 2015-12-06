package edu.upc.eetac.dsa.group7;

import edu.upc.eetac.dsa.group7.dao.RestaurantDAO;
import edu.upc.eetac.dsa.group7.dao.RestaurantDAOImpl;
import edu.upc.eetac.dsa.group7.dao.UserDAO;
import edu.upc.eetac.dsa.group7.dao.UserDAOImpl;
import edu.upc.eetac.dsa.group7.entity.AuthToken;
import edu.upc.eetac.dsa.group7.entity.Restaurant;
import edu.upc.eetac.dsa.group7.entity.RestaurantCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Alex on 1/12/15.
 */
@Path("restaurant")
public class restaurantResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(WhereMediaType.WHERE_RESTAURANT)
    public Response createRestaurant(@FormParam("name") String name, @FormParam("description") String description, @FormParam("avgprice") Float avgprice, @FormParam("address") String address, @FormParam("phone") String phone, @FormParam("lat") Float lat, @FormParam("lng") Float lng, @Context UriInfo uriInfo) throws URISyntaxException {
        if(name==null || description == null || avgprice == null || address == null || phone == null || lat == null || lng == null)
            throw new BadRequestException("all parameters are mandatory");
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        Restaurant restaurant;
        AuthToken authenticationToken = null;
        try {
            if (userDAO.owner(securityContext.getUserPrincipal().getName())==true) {
                restaurant = restaurantDAO.createRestaurant(name, description, avgprice, securityContext.getUserPrincipal().getName(), address, phone, lat, lng);
            }else{
                throw new BadRequestException("You must be the owner to create a restaurant!");
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + restaurant.getId());
        return Response.created(uri).type(WhereMediaType.WHERE_RESTAURANT).entity(restaurant).build();
    }

    @GET
    @Produces(WhereMediaType.WHERE_RESTAURANT_COLLECTION)
    public RestaurantCollection getRestaurants(){
        RestaurantCollection restaurantCollection = null;
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        try {
            restaurantCollection = restaurantDAO.getRestaurants();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return restaurantCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(WhereMediaType.WHERE_RESTAURANT)
    public Restaurant getRetaurants(@PathParam("id") String id){
        Restaurant restaurant = null;
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        try {
            restaurant = restaurantDAO.getRestaurantById(id);
            if(restaurant == null)
                throw new NotFoundException("Restaurant with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return restaurant;
    }

    @Path("/{id}")
    @PUT
    @Consumes(WhereMediaType.WHERE_RESTAURANT)
    @Produces(WhereMediaType.WHERE_RESTAURANT)
    public Restaurant updateRestaurant(@PathParam("id") String id, Restaurant restaurant) {
        if(restaurant == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(restaurant.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");
        UserDAO userDAO = new UserDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        try {
            if(userid.equals(restaurant.getOwner()) || userDAO.admin(securityContext.getUserPrincipal().getName())==true) {
                RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
                restaurant = restaurantDAO.updateRestaurant(restaurant.getId(), restaurant.getDescription(), restaurant.getAvgprice(), restaurant.getPhone());
                if (restaurant == null)
                    throw new NotFoundException("Restaurant with id = " + id + " doesn't exist");
            }else
                throw new ForbiddenException("operation not allowed");
            } catch (SQLException e) {
                throw new InternalServerErrorException();
            }
            return restaurant;
    }

    @Path("/{id}")
    @DELETE
    public void deleteRestaurant(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        try {
            UserDAO userDAO = new UserDAOImpl();
            String ownerid = restaurantDAO.getRestaurantById(id).getOwner();
            if(userid.equals(restaurantDAO.getRestaurantById(id).getOwner()) || userDAO.admin(securityContext.getUserPrincipal().getName())==true)
            {
                if(!restaurantDAO.deleteRestaurant(id))
                    throw new NotFoundException("Restaurant with id = "+id+" doesn't exist");
            }else
                throw new ForbiddenException("operation not allowed");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}
