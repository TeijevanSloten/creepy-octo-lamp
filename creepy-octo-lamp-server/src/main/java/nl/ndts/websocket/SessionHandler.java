package nl.ndts.websocket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.websocket.Session;
import nl.ndts.models.Device;

@ApplicationScoped
public class SessionHandler {
    private final Set<Session> sessions = new HashSet<>();
    private final Set<Device> devices = new HashSet<>();

    public void addSession(Session session) {
        sessions.add(session);
        System.out.println("add: "+ session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
        System.out.println("remove: "+ session);
    }
    
    public List<Device> getDevices() {
        return new ArrayList<>(devices);
    }

    public void addDevice(Device device) {
    }

    public void removeDevice(int id) {
    }

    public void toggleDevice(int id) {
    }

    private Device getDeviceById(int id) {
        return null;
    }

    private JsonObject createAddMessage(Device device) {
        return null;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
    }

    private void sendToSession(Session session, JsonObject message) {
    }
    
    public void sendMessage(String message){
        this.sessions.stream().forEach(session -> session.getAsyncRemote().sendText(message));
    }
}
