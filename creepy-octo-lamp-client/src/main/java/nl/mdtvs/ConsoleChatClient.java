package nl.mdtvs;

import java.net.URI;
import java.net.URISyntaxException;

public class ConsoleChatClient {
    public static void main(final String[] args) throws InterruptedException, URISyntaxException {
        ChatClientEndpoint clientEndPoint = new ChatClientEndpoint(new URI("ws://localhost:8080/creepy-octo-lamp-server/actions"));
        clientEndPoint.onMessage("System home: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));

    }
}