package edu.upc.eetac.dsa.group7.entity;

import edu.upc.eetac.dsa.group7.CommentResource;
import edu.upc.eetac.dsa.group7.LoginResource;
import edu.upc.eetac.dsa.group7.WhereRootAPIResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 29/11/15.
 */
public class CommentCollection {
    @InjectLinks({
            @InjectLink(resource = WhereRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Where Root API"),
            @InjectLink(resource = CommentResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-comments", title = "Current comments"),
            @InjectLink(resource = CommentResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-comments", title = "Current comments"),
            @InjectLink(resource = CommentResource.class, method = "getComments", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer comments", bindings = {@Binding(name = "timestamp", value = "${instance.newestTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = CommentResource.class, method = "getComments", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older comments", bindings = {@Binding(name = "timestamp", value = "${instance.oldestTimestamp}"), @Binding(name = "before", value = "true")}),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout")
    })
    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Comment> comments = new ArrayList<>();

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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
