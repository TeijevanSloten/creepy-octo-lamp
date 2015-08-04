package nl.ndts.models;

import java.util.HashMap;
import java.util.Map;
import javax.websocket.Session;

public class WsDevice {

    private String id;
    private String ip;
    private Map<String, String> properties = new HashMap<>();

    public WsDevice(Session session) {
        this.id = session.getId();
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getId() {
        return id;
    }
}
