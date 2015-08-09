package nl.mdtvs;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import nl.mdtvs.modules.PropertiesRequest;
import org.codehaus.jackson.map.ObjectMapper;

@ClientEndpoint
public class ActionEndpoint {

    Session userSession = null;
    private MessageHandler messageHandler;
    private URI endpointURI;

    public ActionEndpoint(URI uri) {
        try {
            endpointURI = uri;
            connect(endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) throws IOException {
        System.out.println("opening websocket");
        this.userSession = userSession;
        sendAction("REGISTER_DEVICE", PropertiesRequest.getJsonSystemProperties());
    }

    @OnClose
    public void onClose(CloseReason reason) {
        System.out.println("closing websocket" + reason.getReasonPhrase());
        this.userSession = null;
        reconnect();
    }

    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    public void sendAction(String actionName, String actionMessage) throws IOException {
        HashMap<String, String> action = new HashMap();
        action.put("action", actionName);
        action.put("actionmessage", actionMessage);
        this.userSession.getAsyncRemote().sendText(new ObjectMapper().writeValueAsString(action));
    }

    public void connect(URI uri) throws DeploymentException, IOException {
        ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
    }

    public void reconnect() {
        try {
            Thread.sleep(5000);
            connect(endpointURI);
        } catch (Exception ex) {
            Logger.getLogger(ActionEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            reconnect();
        }
    }

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    public interface MessageHandler {

        void handleMessage(String message);
    }
}
