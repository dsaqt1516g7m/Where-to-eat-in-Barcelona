package edu.upc.eetac.dsa.group7;

import edu.upc.eetac.dsa.group7.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.group7.dao.UserDAO;
import edu.upc.eetac.dsa.group7.dao.UserDAOImpl;
import edu.upc.eetac.dsa.group7.entity.AuthToken;
import edu.upc.eetac.dsa.group7.entity.User;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Main class.
 *
 */
public class Main {


    // Base URI the Grizzly HTTP server will listen on
    private static String baseURI;

    public final static String getBaseURI() {
        if (baseURI == null) {
            PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("where");
            baseURI = prb.getString("where.context");
        }
        return baseURI;
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in edu.upc.eetac.dsa.grouptalk package
        final ResourceConfig rc = new WhereResourceConfig();

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(getBaseURI()), rc);
    }
    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, SQLException {
        final HttpServer server = startServer();
/*
        Methods for creating a admin and owner users (actually not in use)

        //Admin user creator
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        AuthToken authenticationToken = null;
        user = userDAO.createAdmin();
        authenticationToken = (new AuthTokenDAOImpl()).createAuthToken(user.getId());
        System.out.println("Admin User created");

        //Owner user creator
        UserDAO userDAO2 = new UserDAOImpl();
        User user2 = null;
        AuthToken authenticationToken2 = null;
        user2 = userDAO2.createOwner();
        authenticationToken2 = (new AuthTokenDAOImpl()).createAuthToken(user.getId());
        System.out.println("Owner User created");
*/
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", baseURI));
        System.in.read();
        server.shutdownNow();
    }
}

