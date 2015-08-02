package nl.ndts.models;

import javax.websocket.Session;

public class WsSession {

    private String id;
    private Session session;

    public WsSession(Session session) {
        this.id = session.getId();
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public String getId() {
        return id;
    }
}
