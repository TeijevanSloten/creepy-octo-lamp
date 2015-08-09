package nl.mdtvs.client;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
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

import nl.mdtvs.command.CommandHandler;
import nl.mdtvs.command.Message;
import nl.mdtvs.modules.PropertiesCommand;
import org.codehaus.jackson.map.ObjectMapper;

@ClientEndpoint
public class ActionEndpoint {

    private Session userSession = null;
    private URI endpointURI;
    private ObjectMapper objectMapper = new ObjectMapper();
    private CommandHandler commandhandler = new CommandHandler();

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
        sendAction("REGISTER_DEVICE", PropertiesCommand.getJsonSystemProperties());
    }

    @OnClose
    public void onClose(CloseReason reason) {
        System.out.println("closing websocket" + reason.getReasonPhrase());
        this.userSession = null;
        reconnect();
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        Message response = commandhandler.execute(objectMapper.readValue(message, Message.class));
        if (response != null) {
            System.out.println(response.getActionMessage());
            sendAction(response);
        }
    }

    public void sendAction(Message m) throws IOException {
        this.userSession.getAsyncRemote().sendText(objectMapper.writeValueAsString(m));
    }

    public void sendAction(String actionName, String actionMessage) throws IOException {
        Map<String, String> action = new HashMap<>();
        action.put("action", actionName);
        action.put("actionmessage", actionMessage);
        this.userSession.getAsyncRemote().sendText(objectMapper.writeValueAsString(action));
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
}
