package nl.mdtvs.rest;

import nl.mdtvs.models.WsAction;
import nl.mdtvs.util.ConvertObject;
import nl.mdtvs.websocket.SessionHandler;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@Path("/action")
public class ActionBoundary {

    @Inject
    private SessionHandler sh;

    @GET
    @Produces("application/json")
    public String getActionMessage() throws JAXBException, IOException {
        sh.sendAction(new WsAction("dos", "https://www.google.nl"));
        return "Message send";
    }

    @GET
    @Path("terminal")
    @Produces("application/json")
    public String sendTerminalMessage() throws JAXBException, IOException {
        sh.sendAction(new WsAction("terminal", "ls -ap"));
        return "TerminalMessage send";
    }

    @GET
    @Path("terminalresponse")
    @Produces("application/json")
    public String getTerminalResponse(@QueryParam("session") String sessionId) throws JAXBException, IOException {
        return sh.getDevice(sessionId).getTerminalResponse();
    }

    @GET
    @Path("/sessions")
    @Produces("application/json")
    public String getSessionsMessage() throws IOException {
        return ConvertObject.devicesToJsonString(sh.getDevices());
    }
}
