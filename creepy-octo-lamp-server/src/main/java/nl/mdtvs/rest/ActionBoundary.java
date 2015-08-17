package nl.mdtvs.rest;

import nl.mdtvs.models.Message;
import nl.mdtvs.util.ConvertObject;
import nl.mdtvs.util.EventPusher;
import nl.mdtvs.websocket.SessionHandler;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.PrintWriter;

@Path("/action")
public class ActionBoundary {

    @Inject
    EventPusher evtPush;

    @Inject
    private SessionHandler sh;

    @POST
    public void sendActionMessage() throws JAXBException, IOException {
        sh.sendAction(new Message("dos", "https://www.google.nl"));
    }

    @POST
    @Path("/terminal/{session}")
    public void sendTerminalMessage(@PathParam("session") String session, @FormParam("command") String command) throws JAXBException, IOException {
        sh.sendAction(session, new Message("terminal", command));
    }

    @GET
    @Path("terminal/{session}")
    public String getTerminalResponse(@PathParam("session") String session) throws JAXBException, IOException {
        String response = sh.getDevice(session).getTerminalResponse();
        sh.getDevice(session).setTerminalResponse("");
        return response;
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

    @GET
    @Path("serverevent")
    public void ServerEventPusher(@Context HttpServletResponse response) throws IOException, InterruptedException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/event-stream, charset=UTF-8");

        evtPush.addInitialObserveObject(0, sh.getDevices());
        if (evtPush.hasChanged(0, sh.getDevices())) {
            out.print(evtPush.generateEvent("updateClients"));
        }
        out.print("retry: 300\n");
        out.flush();
    }
}
