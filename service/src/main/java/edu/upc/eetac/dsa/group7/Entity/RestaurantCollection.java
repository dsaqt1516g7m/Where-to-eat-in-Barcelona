package edu.upc.eetac.dsa.group7.entity;

import edu.upc.eetac.dsa.group7.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 29/11/15.
 */
public class RestaurantCollection {
    @InjectLinks({
            @InjectLink(resource = WhereRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Where Root API"),
            @InjectLink(resource = RestaurantResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-restaurants", title = "Current restaurants"),
            @InjectLink(resource = RestaurantResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-restaurants", title = "Current restaurants"),
            @InjectLink(resource = RestaurantResource.class, method = "getRestaurants", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "More likes", bindings = {@Binding(name = "likes", value = "${instance.likes}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = RestaurantResource.class, method = "getRestaurants", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Less likes", bindings = {@Binding(name = "likes", value = "${instance.likes}"), @Binding(name = "before", value = "true")}),
            @InjectLink(resource = CommentResource.class, style = InjectLink.Style.ABSOLUTE, rel = "comments", title = "comments", type= WhereMediaType.WHERE_COMMENT_COLLECTION),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout")
    })
    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Restaurant> restaurants = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public long getNewestTimestamp() {
        return newestTimestamp;
    }

    public void setNewestTimestamp(long newestTimestamp) {
        this.newestTimestamp = newestTimestamp;
    }

    public long getOldestTimestamp() {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(long oldestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
