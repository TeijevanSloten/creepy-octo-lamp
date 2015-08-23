package nl.mdtvs.rest;

import nl.mdtvs.models.WsDevice;
import nl.mdtvs.rest.AsyncSSERunner.ThrowingRunnable;
import nl.mdtvs.util.ObservedObjectManager;
import nl.mdtvs.websocket.SessionHandler;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import java.io.IOException;

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
    @Path("test")
    public void test(@Context HttpServletRequest request, @Context HttpServletResponse response) throws Exception {
        new AsyncSSERunner(request, response).start(sseTestTask(response), 1000);
    }

    public ThrowingRunnable sseTestTask(HttpServletResponse response) throws IOException {
        ServletOutputStream os = response.getOutputStream();
        return () -> {
            os.print(generateEvent("test"));
            os.flush();
        };
    }

    @GET
    @Path("serverscoped")
    public void serverEventPusher(@Context HttpServletRequest request, @Context HttpServletResponse response) throws Exception {
        AsyncSSERunner ar = new AsyncSSERunner(request, response);
        ar.start(sseServerScopedTask(response), 1000);
    }

    public ThrowingRunnable sseServerScopedTask(HttpServletResponse response) throws IOException {
        ServletOutputStream os = response.getOutputStream();
        return () -> {
            obsManager.addInitialObserveObject("DEVICES", sh::getDevices);
            obsManager.onValueChange("DEVICES", os, changedListEventHandler());
        };
    }

    private ThrowableFunction<ServletOutputStream, ThrowableFunction<Object, Void>> changedListEventHandler() {
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
        AsyncSSERunner ar = new AsyncSSERunner(request, response);
        ar.start(sseSessionScopedTask(response, sessionid), 1000);
    }

    public ThrowingRunnable sseSessionScopedTask(HttpServletResponse response, String sessionid) throws IOException {
        ServletOutputStream os = response.getOutputStream();
        return () -> {
            WsDevice device;
            device = sh.getDevice(sessionid);
            obsManager.addInitialObserveObject(device.getSessionId(), () -> sh.getDevice(sessionid));
            obsManager.onValueChange(device.getSessionId(), os, disconnectedEventHandler());

            obsManager.addInitialObserveObject(device.getSessionId() + "terminalResponse", () -> sh.getDevice(sessionid).getTerminalResponse());
            obsManager.onValueChange(device.getSessionId() + "terminalResponse", os, terminalResponseEventHandler());
        };
    }

    private ThrowableFunction<ServletOutputStream, ThrowableFunction<Object, Void>> terminalResponseEventHandler() {
        return w -> o -> {
            w.print(generateEvent("clientTerminalResponse", o.toString()));
            w.flush();
            return null;
        };
    }

    private ThrowableFunction<ServletOutputStream, ThrowableFunction<Object, Void>> disconnectedEventHandler() {
        return w -> o -> {
            if (o == null) {
                w.print(generateEvent("clientAlive", "false"));
                w.flush();
            }
            return null;
        };
    }

    @FunctionalInterface
    public interface ThrowableFunction<T, R> {

        R apply(T t) throws Exception;
    }
}
