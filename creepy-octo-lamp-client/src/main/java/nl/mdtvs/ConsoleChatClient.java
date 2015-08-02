package nl.mdtvs;

import java.net.URI;
import java.net.URISyntaxException;

public class ConsoleChatClient {
    public static void main(final String[] args) throws InterruptedException, URISyntaxException {
        ChatClientEndpoint clientEndPoint = new ChatClientEndpoint(new URI("ws://0.0.0.0:8080/hascode/"));
        clientEndPoint.addMessageHandler(System.out::println);
    }
}