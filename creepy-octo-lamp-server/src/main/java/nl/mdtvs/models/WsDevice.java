package nl.mdtvs.models;

import java.util.HashMap;
import java.util.Map;
import javax.websocket.Session;

public class WsDevice {

    private final String sessionId;
    private String deviceId = "";
    private String clientIp = "";
    private Map<String, String> properties = new HashMap<>();

    public WsDevice(Session session) {
        this.sessionId = session.getId();
    }

    public void setProperties(Map<String, String> properties) {
        this.deviceId = properties.remove("DEVICE_ID");
        this.clientIp = properties.remove("IP");
        this.properties = properties;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getDeviceId() {
        return deviceId;
    }
    
    public String getClientIp() {
        return clientIp;
    }
    
    public Map<String, String> getProperties() {
        return properties;
    }
}