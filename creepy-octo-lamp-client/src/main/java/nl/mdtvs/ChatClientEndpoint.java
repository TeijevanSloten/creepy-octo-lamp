package nl.mdtvs;

import javax.websocket.*;
import java.net.URI;
import java.util.function.Consumer;

@ClientEndpoint
public class ChatClientEndpoint {

    private Consumer<String> consumer;

    public ChatClientEndpoint(final URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(final Session userSession) {
    }

    @OnClose
    public void onClose(final Session userSession, final CloseReason reason) {
    }

    @OnMessage
    public void onMessage(final String message) {
        if (consumer != null) {
            consumer.accept(message);
        }
    }

    public void addMessageHandler(final Consumer<String> consumer) {
        this.consumer = consumer;
    }
}
