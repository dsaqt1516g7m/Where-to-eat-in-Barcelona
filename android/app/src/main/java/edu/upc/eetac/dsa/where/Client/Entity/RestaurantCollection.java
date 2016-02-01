package edu.upc.eetac.dsa.where.Client.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carol on 17/01/16.
 */
public class RestaurantCollection {

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