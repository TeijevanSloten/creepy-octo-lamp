package nl.mdtvs.models;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.ObjectStreamField;
import java.io.Serializable;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

@Getter
@JsonIgnoreProperties(value = {"sessionObject", "terminalResponse"})
public class WsDevice implements Serializable {

    private String sessionId;
    private Session sessionObject;
    private Map<String, String> properties = new HashMap<>();
    private String deviceId;
    private String clientIp;
    private String terminalResponse;

    public final static ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("sessionObject", Session.class)
    };

    public WsDevice(Session session) {
        this.sessionId = session.getId();
        this.sessionObject = session;
    }

    public void setProperties(Map<String, String> properties) {
        this.deviceId = properties.get("DEVICE_ID");
        this.clientIp = properties.get("IP");
        this.properties = properties;
        this.terminalResponse = "";
    }

    public void setTerminalResponse(String terminalResponse) {
        this.terminalResponse = terminalResponse;
    }

    public void sendText(String message) {
        sessionObject.getAsyncRemote().sendText(message);
    }

    private void writeObject(final ObjectOutputStream out) throws IOException {
        out.writeUTF(this.sessionId);
        out.writeObject(this.properties);
        out.writeUTF(this.deviceId);
        out.writeUTF(this.clientIp);
        out.writeUTF(this.terminalResponse);
    }

    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.sessionId = in.readUTF();
        this.properties = (Map<String, String>) in.readObject();
        this.deviceId = in.readUTF();
        this.clientIp = in.readUTF();
        this.terminalResponse = in.readUTF();
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream data required");
    }

}
