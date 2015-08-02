package nl.ndts.websocket;

import nl.ndts.util.ConvertObject;
import nl.ndts.models.WsAction;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class SessionHandler {
    private final Set<Session> sessions = new HashSet<>();

    public void addSession(Session session) {
        sessions.add(session);
        System.out.println("add: " + session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
        System.out.println("remove: " + session);
    }

    private void sendToAllConnectedSessions(WsAction message) {
    }

    private void sendToSession(Session session, WsAction message) {
    }

    public void sendAction(WsAction wsAction) throws JAXBException, IOException {
        String actionMessage = ConvertObject.wsActionToJson(wsAction);
        this.sessions.stream().forEach(session -> session.getAsyncRemote().sendText(actionMessage));
    }
}
