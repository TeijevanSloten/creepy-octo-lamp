package nl.mdtvs.models;

import lombok.Getter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

@Getter
@JsonIgnoreProperties(value = {"sessionObject"})
public class WsDevice {

    private final String sessionId;
    private final String deviceId;
    private final String clientIp;
    private final Session sessionObject;
    private Map<String, String> properties = new HashMap<>();

    public WsDevice(Session session, Map<String, String> properties) {
        this.sessionObject = session;
        this.sessionId = session.getId();
        this.deviceId = properties.get("DEVICE_ID");
        this.clientIp = properties.get("IP");
        this.properties = properties;
    }
}