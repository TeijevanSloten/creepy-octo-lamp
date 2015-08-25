package nl.mdtvs.rest;

import nl.mdtvs.util.ObservedObjectManager;
import nl.mdtvs.websocket.SessionHandler;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.util.function.Function;
import javax.ws.rs.PathParam;
import nl.mdtvs.models.WsDevice;

@Path("/sse")
public class ServerSentEventBoundary {

    @Inject
    private SessionHandler sh;

    @Inject
    private ObservedObjectManager obsManager;

    private String generateEvent(String event) {
        return generateEvent(event, "");
    }

    private String generateEvent(String event, String data) {
        return "event:" + event + "\n" + "data:" + data + "\n\n";
    }

    @GET
    @Path("serverscoped")
    public void serverEventPusher(@Context HttpServletRequest request, @Context HttpServletResponse response) throws Throwable {
        AsyncSSERunner ar = new AsyncSSERunner(request, response);
        
        obsManager.addInitialObserveObject("DEVICES", sh::getDevices);
        obsManager.onValueChange("DEVICES", changedListEventHandler());
        ar.addListener("DEVICES", obsManager);
        
        ar.start(500);
    }

    private Function<Object,String> changedListEventHandler() {
        return (o) -> {
            return generateEvent("updateClients");
        };
    }

    @GET
    @Path("sessionscoped/sessionid")
    public void sessionEventPusher(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("sessionid") String sessionid) throws Throwable {
        AsyncSSERunner ar = new AsyncSSERunner(request, response);
        
        WsDevice device;
        device = sh.getDevice(sessionid);
        
        obsManager.addInitialObserveObject(device.getSessionId(), () -> sh.getDevice(sessionid));
        obsManager.onValueChange(device.getSessionId(), disconnectedEventHandler());
        ar.addListener(device.getSessionId(), obsManager);
        
        obsManager.addInitialObserveObject(device.getSessionId() + "terminalResponse", () -> sh.getDevice(sessionid).getTerminalResponse());
        obsManager.onValueChange(device.getSessionId() + "terminalResponse", terminalResponseEventHandler());        
        ar.addListener(device.getSessionId() + "terminalResponse", obsManager);
        
        ar.start(500);
    }

    private Function<Object,String> disconnectedEventHandler() {
        return (o) -> {
            return generateEvent("clientAlive", "false");
            };
    }    
 
    private Function<Object,String> terminalResponseEventHandler() {
        return (o) -> {
            if(o != null){
                return generateEvent("clientTerminalResponse", o.toString());
            } else {
                return generateEvent("clientTerminalResponse", "Error could not build event data");
            }
        };
    }
}
