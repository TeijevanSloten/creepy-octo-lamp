package nl.mdtvs.rest;

import nl.mdtvs.models.WsAction;
import nl.mdtvs.util.ConvertObject;
import nl.mdtvs.websocket.SessionHandler;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@Path("/action")
public class ActionBoundary {

    @Inject
    private SessionHandler sh;

    @GET
    @Produces("application/json")
    public String getActionMessage() throws JAXBException, IOException {
        sh.sendAction(new WsAction("bla-Action", "bla"));
        return "Message send";
    }

    @Path("/sessions")
    @GET
    @Produces("application/json")
    public String getSessionsMessage() throws IOException {
        return ConvertObject.devicesToJsonString(sh.getDevices());
    }
}
