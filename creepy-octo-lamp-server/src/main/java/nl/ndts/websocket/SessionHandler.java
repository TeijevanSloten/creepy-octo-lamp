package nl.ndts.websocket;

import nl.ndts.models.WsSession;
import nl.ndts.util.ConvertObject;
import nl.ndts.models.WsAction;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class SessionHandler {
    private final Map<String, WsSession> sessions = new HashMap<>();

    public void addSession(Session session) {
        sessions.put(session.getId(), new WsSession(session));
        System.out.println("add: " + session);
    }

    public void removeSession(Session session) {
        sessions.remove(session.getId());
        System.out.println("remove: " + session);
    }

    public void sendAction(WsAction wsAction) throws JAXBException, IOException {
        String actionMessage = ConvertObject.wsActionToJson(wsAction);
        this.sessions.values().stream().forEach(session -> session.getSession().getAsyncRemote().sendText(actionMessage));
    }

    private void sendToAllConnectedSessions(WsAction message) {
    }

    private void sendToSession(Session session, WsAction message) {
    }
}
