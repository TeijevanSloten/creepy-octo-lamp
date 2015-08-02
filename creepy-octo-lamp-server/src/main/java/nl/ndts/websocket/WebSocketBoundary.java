package nl.ndts.websocket;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/actions")
public class WebSocketBoundary {

    @Inject
    private SessionHandler sessionHandler;

    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(WebSocketBoundary.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String jsonString, Session session) throws JAXBException, IOException {
        sessionHandler.handleMessage(jsonString, session);
    }
}
