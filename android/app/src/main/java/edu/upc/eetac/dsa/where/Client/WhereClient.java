package edu.upc.eetac.dsa.where.Client;

import android.util.Log;

import com.google.gson.Gson;

import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import edu.upc.eetac.dsa.where.Client.Entity.AuthToken;
import edu.upc.eetac.dsa.where.Client.Entity.Link;
import edu.upc.eetac.dsa.where.Client.Entity.Root;

/**
 * Created by  Carolina on 17/01/16.
 */
public class WhereClient {

    private final static String BASE_URI = "http://147.83.7.206:8080/where";
    private static WhereClient instance;
    private Root root;
    private ClientConfig clientConfig = null;
    private Client client = null;
    private AuthToken authToken = null;
    private final static String TAG = WhereClient.class.toString();

    private WhereClient() {
        clientConfig = new ClientConfig();
        client = ClientBuilder.newClient(clientConfig);
        loadRoot();
    }

    public boolean login(String userid, String password) {
        String loginUri = getLink(root.getLinks(), "login").getUri().toString();
        WebTarget target = client.target(loginUri);
        Form form = new Form();
        form.param("login", "carol");
        form.param("password", "carol");
        String json = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
        authToken = (new Gson()).fromJson(json, AuthToken.class);
        Log.d(TAG, json);
        return true;
    }

    public static WhereClient getInstance() {
        if (instance == null)
            instance = new WhereClient();
        return instance;
    }

    private void loadRoot() {
        WebTarget target = client.target(BASE_URI);
        Response response = target.request().get();

        String json = response.readEntity(String.class);
        root = (new Gson()).fromJson(json, Root.class);
    }

    public Root getRoot() {
        return root;
    }

    public final static Link getLink(List<Link> links, String rel){
        for(Link link : links){
            if(link.getRels().contains(rel)) {
                return link;
            }
        }
        return null;
    }

    public String getRestaurants(String uri) throws WhereClientException {
        /*
        if(uri==null){
            uri = getLink(authToken.getLinks(), "current-stings").getUri().toString();
        }
        */
        uri = BASE_URI+"/restaurant";
        WebTarget target = client.target(uri);
        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode())
            return response.readEntity(String.class);
        else
            throw new WhereClientException(response.readEntity(String.class));
    }
    public String getComments(String uri) throws WhereClientException {
        /*
        if(uri==null){
            uri = getLink(authToken.getLinks(), "current-stings").getUri().toString();
        }
        */
        uri = BASE_URI+"/restaurant"+ "/comments";
        WebTarget target = client.target(uri);
        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode())
            return response.readEntity(String.class);
        else
            throw new WhereClientException(response.readEntity(String.class));
    }

    public String getRestaurant(String uri) throws WhereClientException {
        WebTarget target = client.target(uri);
        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode())
            return response.readEntity(String.class);
        else
            throw new WhereClientException(response.readEntity(String.class));
    }


}
