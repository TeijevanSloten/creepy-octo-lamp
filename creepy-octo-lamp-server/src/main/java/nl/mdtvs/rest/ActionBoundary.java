package nl.mdtvs.rest;

import nl.mdtvs.models.Message;
import nl.mdtvs.models.WsDevice;
import nl.mdtvs.util.ConvertObject;
import nl.mdtvs.websocket.SessionHandler;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Map;

@Path("/action")
public class ActionBoundary {

    @Inject
    private SessionHandler sh;

    @POST
    public void sendActionMessage() throws JAXBException, IOException {
        sh.sendAction(new Message("dos", "https://www.google.nl"));
    }

    @POST
    @Path("terminal")
    public void sendTerminalMessage() throws JAXBException, IOException {
        sh.sendAction(new Message("terminal", "ls -ap"));
    }

    @GET
    @Path("terminalresponse")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTerminalResponse(@QueryParam("session") String sessionId) throws JAXBException, IOException {
        return sh.getDevices().entrySet().stream()
                .map(Map.Entry::getValue).map(WsDevice::getTerminalResponse)
                .reduce((s, s2) -> s + s2).get();
    }

    @GET
    @Path("/devices")
    @Produces(MediaType.APPLICATION_JSON)
    public String getConnectedDevices() throws IOException {
        return ConvertObject.devicesToJson(sh.getDevices());
    }

    @GET
    @Path("/devices/{session}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getConnectedDevice(@PathParam("session") String sessionid) throws IOException {
        return ConvertObject.deviceToJson(sh.getDevice(sessionid));
    }
}
