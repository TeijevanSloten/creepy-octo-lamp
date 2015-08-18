package nl.mdtvs.models;

import lombok.Getter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

@Getter
@JsonIgnoreProperties(value = {"sessionObject", "terminalResponse"})
public class WsDevice {

    private final String sessionId;
    private final Session sessionObject;
    private Map<String, String> properties = new HashMap<>();
    private String deviceId;
    private String clientIp;

    private String terminalResponse;

    public WsDevice(Session session) {
        this.sessionId = session.getId();
        this.sessionObject = session;
    }

    public void setProperties(Map<String, String> properties){
        this.deviceId = properties.get("DEVICE_ID");
        this.clientIp = properties.get("IP");
        this.properties = properties;
        this.terminalResponse = "";
    }

    public void setTerminalResponse(String terminalResponse) {
        this.terminalResponse = terminalResponse;
    }

    public void sendText(String message){
        sessionObject.getAsyncRemote().sendText(message);
    }
}