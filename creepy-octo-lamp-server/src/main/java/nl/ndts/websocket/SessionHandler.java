package nl.ndts.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;
import javax.xml.bind.JAXBException;
import nl.ndts.models.WsAction;
import nl.ndts.models.WsDevice;
import nl.ndts.util.ConvertObject;

@ApplicationScoped
public class SessionHandler {

    @Inject
    private ConvertObject convertObject;

    private final Map<String, Session> sessions = new HashMap<>();
    private final Map<String, WsDevice> devices = new HashMap<>();

    public void addSession(Session session) {
        sessions.put(session.getId(), session);
        System.out.println("add: " + session);
    }

    public void removeSession(Session session) {
        sessions.remove(session.getId());

        if (devices.containsKey(session.getId())) {
            devices.remove(session.getId());
        }
        System.out.println("remove: " + session);
    }

    public void sendAction(WsAction wsAction) throws JAXBException, IOException {
        String actionMessage = ConvertObject.wsActionToJson(wsAction);
        sessions.values().forEach(session -> session.getAsyncRemote().sendText(actionMessage));
    }

    public void handleMessage(String jsonString, Session session) throws JAXBException, IOException {
        WsDevice wsd = new WsDevice(session);
        wsd.setProperties(ConvertObject.jsonStringToMap(jsonString));
        devices.put(session.getId(), wsd);
    }

    public Map<String, WsDevice> getDevices() {
        return devices;
    }
}