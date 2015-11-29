package edu.upc.eetac.dsa.group7.entity;

import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Alex on 29/11/15.
 */
public class Comment {
    @InjectLinks({})
    private List<Link> links;
    private String id;
    private String creator;
    private String restaurant;
    private String title;
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
