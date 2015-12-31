package edu.upc.eetac.dsa.group7.entity;

import edu.upc.eetac.dsa.group7.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Alex on 29/11/15.
 */
public class Restaurant {
    @InjectLinks({
            @InjectLink(resource = WhereRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Where Root API"),
            @InjectLink(resource = RestaurantResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-restaurants", title = "Current restaurants"),
            @InjectLink(resource = RestaurantResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-restaurant", title = "Create restaurant", type = MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = RestaurantResource.class, method = "getRestaurant", style = InjectLink.Style.ABSOLUTE, rel = "self restaurant", title = "Restaurant", bindings = @Binding(name = "id", value = "${instance.id}")),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", bindings = @Binding(name = "id", value = "${instance.owner}")),
            @InjectLink(resource = RestaurantResource.class, method = "getRestaurants", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "More Likes", bindings = {@Binding(name = "likes", value = "${instance.likes}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = RestaurantResource.class, method = "getRestaurants", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Less Likes", bindings = {@Binding(name = "likes", value = "${instance.likes}"), @Binding(name = "before", value = "true")})
    })
    private List<Link> links;
    private String id;
    private String name;
    private String description;
    private Float avgprice;
    private String owner;
    private int likes;
    private String address;
    private String phone;
    private float lat;
    private float lng;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAvgprice() {
        return avgprice;
    }

    public void setAvgprice(Float avgprice) {
        this.avgprice = avgprice;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
