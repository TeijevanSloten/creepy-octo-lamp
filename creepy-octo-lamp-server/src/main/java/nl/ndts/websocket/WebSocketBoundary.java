package nl.ndts.websocket;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.xml.bind.JAXBException;

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
    public void onMessage(String jsonString, Session session) throws JAXBException, IOException {
        sessionHandler.handleMessage(jsonString, session);
    }
}
