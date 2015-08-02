package nl.mdtvs;

import java.io.IOException;
import java.net.URI;
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

@ClientEndpoint
public class ActionEndpoint {

    Session userSession = null;
    private MessageHandler messageHandler;
    private URI endpointURI;

    public ActionEndpoint(URI uri) {
        endpointURI = uri;
        try {
            connect(endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
        reconnect();
    }

    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    public void connect(URI uri) throws DeploymentException, IOException {
        ContainerProvider.getWebSocketContainer().connectToServer(this, endpointURI);
    }
    
    public void reconnect(){
        try {
            Thread.sleep(5000);
            connect(endpointURI);
        } catch (Exception ex) {
            Logger.getLogger(ActionEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            reconnect();
        }
    }

    public interface MessageHandler {

        void handleMessage(String message);
    }
}
