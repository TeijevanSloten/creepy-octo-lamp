package nl.ndts.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import nl.ndts.websocket.SessionHandler;

@Path("/action")
public class ActionBoundary {

    @Inject
    private SessionHandler sh;
    
    @GET
    @Produces("application/json")
    public String getClichedMessage() {
        sh.sendMessage("A message");
        return "Message send";
    }
}
