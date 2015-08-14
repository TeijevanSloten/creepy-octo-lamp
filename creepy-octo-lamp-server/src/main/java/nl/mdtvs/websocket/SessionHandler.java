package nl.mdtvs.websocket;

import nl.mdtvs.command.handler.CommandHandler;
import nl.mdtvs.models.DeviceManager;
import nl.mdtvs.models.Message;
import nl.mdtvs.models.WsDevice;
import nl.mdtvs.util.ConvertObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Map;

@ApplicationScoped
public class SessionHandler {

    @Inject
    private CommandHandler commandHandler;

    private DeviceManager dm = DeviceManager.getInstance();

    public void addSession(Session session) throws JAXBException, IOException {
        dm.registerDevice(session);
    }

    public void removeSession(Session session) throws IOException {
        dm.unRegisterDevice(session);
    }

    public void sendAction(Message message, String sessionId) throws JAXBException, IOException {
        String actionMessage = ConvertObject.wsActionToJson(message);
        DeviceManager.getInstance().getDevice(sessionId).sendText(actionMessage);
    }

    public void sendAction(Message message) throws JAXBException, IOException {
        String actionMessage = ConvertObject.wsActionToJson(message);
        System.out.println("Send: " + actionMessage);
        DeviceManager.getInstance().getDevices().values()
                .forEach(device -> device.sendText(actionMessage));
    }

    public void handleMessage(String jsonMessage, Session session) throws JAXBException, IOException {
        System.out.println("Received: " + jsonMessage);
        Message message = ConvertObject.jsonStringToWsAction(jsonMessage);
        commandHandler.execute(message, dm.getDevice(session.getId()));
    }

    public Map<String, WsDevice> getDevices() {
        return DeviceManager.getInstance().getDevices();
    }
}
