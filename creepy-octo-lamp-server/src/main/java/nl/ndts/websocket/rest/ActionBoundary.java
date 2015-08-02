package nl.ndts.websocket.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import nl.ndts.websocket.WebSocketServer;

@Path("/action")
public class ActionBoundary {

    @Inject
    private WebSocketServer server;
    
    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        server.handleMessage("SDFj;lasdkjf;lkjsdlf");
        return "Hello World";
    }
}
