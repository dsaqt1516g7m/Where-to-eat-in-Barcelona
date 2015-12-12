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
public class Comment {
    @InjectLinks({
            @InjectLink(resource = WhereRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Where Root API"),
            @InjectLink(resource = CommentResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-comments", title = "Current comments"),
            @InjectLink(resource = CommentResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-comment", title = "Create comment", type = MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = CommentResource.class, method = "getComment", style = InjectLink.Style.ABSOLUTE, rel = "self comment", title = "Comment", bindings = @Binding(name = "id", value = "${instance.id}")),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", bindings = @Binding(name = "creator", value = "${instance.creator}")),
            @InjectLink(resource = RestaurantResource.class, method = "getRestaurant", style = InjectLink.Style.ABSOLUTE, rel = "restaurant-profile", title = "Restaurant profile", bindings = @Binding(name = "restaurant", value = "${instance.restaurant}")),
            @InjectLink(resource = CommentResource.class, method = "getComments", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer comments", bindings = {@Binding(name = "timestamp", value = "${instance.creation_timestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = CommentResource.class, method = "getComments", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older comments", bindings = {@Binding(name = "timestamp", value = "${instance.creation_timestamp}"), @Binding(name = "before", value = "true")}),
    })
    private List<Link> links;
    private String id;
    private String creator;
    private String restaurant;
    private String title;
    private int likes;
    private String comment;
    private String response;
    private long creation_timestamp;

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

    public String getCreator() {
        return creator;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public long getCreation_timestamp() {
        return creation_timestamp;
    }

    public void setCreation_timestamp(long creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }
}
