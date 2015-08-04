package nl.ndts.rest;

import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBException;
import nl.ndts.models.WsAction;
import nl.ndts.websocket.SessionHandler;
import org.codehaus.jackson.map.ObjectMapper;

@Path("/action")
public class ActionBoundary {

    @Inject
    private SessionHandler sh;

    @GET
    @Produces("application/json")
    public String getActionMessage() throws JAXBException, IOException {
        WsAction action = new WsAction();
        action.actionMessage = "bla";
        action.actionName = "bla-Action";

        sh.sendAction(action);
        return "Message send";
    }

    @Path("/sessions")
    @GET
    @Produces("application/json")
    public String getSessionsMessage() throws IOException {
        return new ObjectMapper().writeValueAsString(sh.getSessions());
    }
}
