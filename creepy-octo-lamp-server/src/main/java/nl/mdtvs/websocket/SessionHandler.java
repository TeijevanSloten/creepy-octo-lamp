package nl.mdtvs.websocket;

import nl.mdtvs.cmd.DeviceManager;
import nl.mdtvs.cmd.handler.CmdEnum;
import nl.mdtvs.cmd.handler.CommandHandler;
import nl.mdtvs.models.WsAction;
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
    private ConvertObject convertObject;

    @Inject
    private CommandHandler commandHandler;

    public void addSession(Session session) {
        commandHandler.executeCommand(CmdEnum.REGISTER_SESSION, new Object[]{session});
        System.out.println("add: " + session);
    }

    public void removeSession(Session session) {
        commandHandler.executeCommand(CmdEnum.UNREGISTER_SESSION, new Object[]{session});
        commandHandler.executeCommand(CmdEnum.UNREGISTER_DEVICE, new Object[]{session});
        System.out.println("remove: " + session);
    }

    public void sendAction(WsAction wsAction) throws JAXBException, IOException {
        String actionMessage = ConvertObject.wsActionToJson(wsAction);
        DeviceManager.getInstance().getSessions().values().forEach(session -> session.getAsyncRemote().sendText(actionMessage));
    }

    public void sendAction(WsAction wsAction, String sessionId) throws JAXBException, IOException {
        String actionMessage = ConvertObject.wsActionToJson(wsAction);
        DeviceManager.getInstance().getSessions().get(sessionId).getAsyncRemote().sendText(actionMessage);
    }

    public void handleMessage(String jsonString, Session session) throws JAXBException, IOException {
        WsAction ws = ConvertObject.jsonStringToWsAction(jsonString);

        try {
            commandHandler.executeCommand(CmdEnum.valueOf(ws.actionName), new Object[]{ws.actionMessage, session});
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Unkown message");
        }
    }

    public Map<String, WsDevice> getDevices() {
        return DeviceManager.getInstance().getDevices();
    }
}
