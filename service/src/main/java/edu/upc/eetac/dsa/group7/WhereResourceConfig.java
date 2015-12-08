package edu.upc.eetac.dsa.group7;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by Alex on 30/11/15.
 */
public class WhereResourceConfig extends ResourceConfig {
    public WhereResourceConfig() {
        packages("edu.upc.eetac.dsa.group7");
        packages("edu.upc.eetac.dsa.group7.auth");
        register(RolesAllowedDynamicFeature.class);
    }
}