package nl.ndts.websocket;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/actions")
public class WebSocketServer {

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
            Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
        public void handleMessage(String message, Session session) {
    }
    
}
