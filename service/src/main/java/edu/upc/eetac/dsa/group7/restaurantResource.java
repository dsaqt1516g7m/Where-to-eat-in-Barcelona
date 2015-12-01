package edu.upc.eetac.dsa.group7;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by Alex on 1/12/15.
 */
@Path("stings")
public class restaurantResource {
    @Context
    private SecurityContext securityContext;



}
