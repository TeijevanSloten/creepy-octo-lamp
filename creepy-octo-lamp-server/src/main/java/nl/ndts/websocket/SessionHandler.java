package nl.ndts.websocket;

import nl.ndts.models.WsAction;
import nl.ndts.models.WsSession;
import nl.ndts.util.ConvertObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class SessionHandler {

    @Inject
    private ConvertObject convertObject;

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
        this.sessions.values().stream().forEach(session -> session.session.getAsyncRemote().sendText(actionMessage));
    }

    public void handleMessage(String jsonString, Session session) throws JAXBException, IOException {
        System.out.println(jsonString);
        sessions.get(session.getId()).properties = ConvertObject.jsonStringToMap(jsonString);
    }

    public Map<String, WsSession> getSessions() {
        return sessions;
    }
}
