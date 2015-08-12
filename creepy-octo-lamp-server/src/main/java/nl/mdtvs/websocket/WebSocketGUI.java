package nl.mdtvs.websocket;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


@ServerEndpoint("/wsGUI")
public class WebSocketGUI {

    @Inject
    private SessionHandler sessionHandler;

    @OnOpen
    public void open(Session session) {
//        session.getAsyncRemote().sendText("oh hi!");
//        sessionHandler.setServerGui(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.setServerGui(null);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(WebSocketBoundary.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void onMessage(String jsonString, Session session) throws JAXBException, IOException {
        System.out.println(jsonString);
    }
}
