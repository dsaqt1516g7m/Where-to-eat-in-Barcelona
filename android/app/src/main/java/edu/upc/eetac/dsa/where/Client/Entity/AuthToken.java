package edu.upc.eetac.dsa.where.Client.Entity;

import java.util.List;

/**
 * Created by Carolina on 17/01/16.
 */
public class AuthToken {

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
