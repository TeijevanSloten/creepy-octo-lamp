package nl.mdtvs;

import java.net.URI;
import java.net.URISyntaxException;

public class ConsoleClient {
    public static void main(final String[] args) throws InterruptedException, URISyntaxException {
        try {
            final ActionEndpoint clientEndPoint = new ActionEndpoint(new URI("ws://localhost:8080/creepy-octo-lamp-server/actions"));
            clientEndPoint.addMessageHandler(System.out::println);
            clientEndPoint.sendMessage("{'event':'addChannel','channel':'ok_btccny_ticker'}");

            while(true) {
                Thread.sleep(2000);
                System.out.println("cycle");
            }

        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
}