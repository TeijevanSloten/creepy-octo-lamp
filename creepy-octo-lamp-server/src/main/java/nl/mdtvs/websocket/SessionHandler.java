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

    private Session serverGui;

    public Session getServerGui() {
        return serverGui;
    }

    public void setServerGui(Session serverGui) {
        this.serverGui = serverGui;
    }
    
    public void addSession(Session session) {
        System.out.println("add: " + session);
    }

    public void removeSession(Session session) {
        commandHandler.executeCommand(CmdEnum.UNREGISTER_DEVICE, new Object[]{session});
        serverGui.getAsyncRemote().sendText("updateClients");
        System.out.println("remove: " + session);
        
    }

    public void sendAction(WsAction wsAction) throws JAXBException, IOException {
        String actionMessage = ConvertObject.wsActionToJson(wsAction);
        DeviceManager.getInstance().getDevices().values().forEach(device -> device.getSessionObject().getAsyncRemote().sendText(actionMessage));
    }

    public void sendAction(WsAction wsAction, String sessionId) throws JAXBException, IOException {
        String actionMessage = ConvertObject.wsActionToJson(wsAction);
        DeviceManager.getInstance().getDevices().get(sessionId).getSessionObject().getAsyncRemote().sendText(actionMessage);
    }

    public void handleMessage(String jsonString, Session session) throws JAXBException, IOException {
        try {
            System.out.println(jsonString);
            WsAction ws = ConvertObject.jsonStringToWsAction(jsonString);
            commandHandler.executeCommand(CmdEnum.valueOf(ws.getAction()), new Object[]{ws.getMessage(), session});
            if(CmdEnum.valueOf(ws.getAction()).getHashKey() == 0) {
                serverGui.getAsyncRemote().sendText("updateClients");
            }
            
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Unkown message");
        }
    }

    public Map<String, WsDevice> getDevices() {
        return DeviceManager.getInstance().getDevices();
    }

    public WsDevice getDevice(String sessionId) {
        return DeviceManager.getInstance().getDevice(sessionId);
    }
}
