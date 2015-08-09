package nl.mdtvs.models;

import lombok.Getter;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

@Getter
public class WsDevice {

    private final String sessionId;
    private final String deviceId;
    private final String clientIp;
    private Map<String, String> properties = new HashMap<>();

    public WsDevice(Session session, Map<String, String> properties) {
        this.sessionId = session.getId();
        this.deviceId = properties.remove("DEVICE_ID");
        this.clientIp = properties.remove("IP");
        this.properties = properties;
    }
}