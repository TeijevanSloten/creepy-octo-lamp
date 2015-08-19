package nl.mdtvs.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import nl.mdtvs.models.WsDevice;
import nl.mdtvs.util.ObservedObjectManager;
import nl.mdtvs.websocket.SessionHandler;

@Path("/sse")
public class ServerSentEventBoundary {

    @Inject
    private SessionHandler sh;

    @Inject
    private ObservedObjectManager evtPush;

    private PrintWriter out;

//        final AsyncContext asyncContext = request.startAsync(request, response);
//        asyncContext.addListener(new Handler(asyncContext));
//        asyncContext.start(new Task(asyncContext, evtPush, sh));
    @GET
    @Path("serverscoped")
    public void serverEventPusher(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException {
        setHeader(response);

        evtPush.addInitialObserveObject("DEVICES", () -> sh.getDevices());
        evtPush.onValueChange("DEVICES", out, changedListEventHandler());
    }

    private Function<PrintWriter, Function<Object, Void>> changedListEventHandler() {
        return w -> o -> {
            String e = generateEvent("updateClients");
            w.print(e);
            w.flush();
            return null;
        };
    }

    @GET
    @Path("sessionscoped/sessionid")
    public void sessionEventPusher(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("sessionid") String sessionid) throws IOException {
        setHeader(response);
        WsDevice device;
        try {
            device = sh.getDevice(sessionid);
            evtPush.addInitialObserveObject(device.getSessionId(), () -> sh.getDevice(sessionid));
            evtPush.onValueChange(device.getSessionId(), out, DisconnectedEventHandler());

            evtPush.addInitialObserveObject(device.getSessionId() + "terminalResponse", () -> sh.getDevice(sessionid).getTerminalResponse());
            evtPush.onValueChange(device.getSessionId() + "terminalResponse", out, TerminalResponseEventHandler());
        } catch (Exception e) {
        }
    }

    private Function<PrintWriter, Function<Object, Void>> TerminalResponseEventHandler() {
        return w -> o -> {
            w.print(generateEvent("clientTerminalResponse", o.toString()));
            w.flush();
            return null;
        };
    }

    private Function<PrintWriter, Function<Object, Void>> DisconnectedEventHandler() {
        return w -> o -> {
            if (o == null) {
                w.print(generateEvent("clientAlive", "false"));
                w.flush();
            }
            return null;
        };
    }

    private String generateEvent(String event) {
        return generateEvent(event, "");
    }

    private String generateEvent(String event, String data) {
        return "event:" + event + "\n"
                + "data:" + data + "\n\n";
    }

    private void setHeader(HttpServletResponse response) throws IOException {
        out = response.getWriter();

        response.setContentType("text/event-stream, charset=UTF-8");

        out.write("retry:300\n");
        out.flush();
    }
}
