package nl.ndts.models;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class WsSession {

    public String id;
    public Session session;
    public Map<String, String> properties = new HashMap<>();

    public WsSession(Session session) {
        this.id = session.getId();
        this.session = session;
    }
}
