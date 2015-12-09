package edu.upc.eetac.dsa.group7;

import edu.upc.eetac.dsa.group7.entity.WhereRootAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by Alex on 9/12/15.
 */

@Path("/")
public class WhereRootAPIResource {
    @Context
    private SecurityContext securityContext;

    private String userid;

    @GET
    @Produces(WhereMediaType.WHERE_ROOT)
    public WhereRootAPI getRootAPI() {
        if(securityContext.getUserPrincipal()!=null)
            userid = securityContext.getUserPrincipal().getName();
        WhereRootAPI whereRootAPI = new WhereRootAPI();

        return whereRootAPI;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}