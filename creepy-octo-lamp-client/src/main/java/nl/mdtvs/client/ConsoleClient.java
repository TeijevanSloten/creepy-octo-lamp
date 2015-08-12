package nl.mdtvs.client;

import java.net.URI;
import java.net.URISyntaxException;

public class ConsoleClient {

    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        try {
            new ActionEndpoint(new URI("ws://localhost:8080/creepy-octo-lamp-server/actions"));

            keepCycling();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    private static void keepCycling() throws InterruptedException {
        while (true) {
            Thread.sleep(500);
        }
    }
}
