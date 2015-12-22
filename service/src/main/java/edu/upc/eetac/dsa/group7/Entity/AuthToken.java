package edu.upc.eetac.dsa.group7.entity;

import edu.upc.eetac.dsa.group7.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Alex on 29/11/15.
 */
public class AuthToken {
    @InjectLinks({
            @InjectLink(resource = WhereRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Beeter Root API"),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "self login", title = "Login", type= WhereMediaType.WHERE_AUTH_TOKEN),
            @InjectLink(resource = RestaurantResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-restaurants", title = "hola Current restaurants", type= WhereMediaType.WHERE_RESTAURANT_COLLECTION),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = RestaurantResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-restaurant", title = "Create restaurant", type=WhereMediaType.WHERE_RESTAURANT),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", type= WhereMediaType.WHERE_USER, bindings = @Binding(name = "id", value = "${instance.userid}"))
    })
    private List<Link> links;

    private String userid;

    private String token;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
