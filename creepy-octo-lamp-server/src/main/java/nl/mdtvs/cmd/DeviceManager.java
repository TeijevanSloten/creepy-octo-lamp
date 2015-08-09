package nl.mdtvs.cmd;

import nl.mdtvs.models.WsDevice;
import nl.mdtvs.util.ConvertObject;

import javax.websocket.Session;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeviceManager {

    private static final DeviceManager INSTANCE = new DeviceManager();
    private static final Map<String, WsDevice> devices = new HashMap<>();

    private DeviceManager() {
    }

    public static DeviceManager getInstance() {
        return INSTANCE;
    }

    public void registerDevice(String jsonSting, Session s) throws JAXBException, IOException {
        devices.put(s.getId(), new WsDevice(s, ConvertObject.jsonStringToMap(jsonSting)));
    }

    public void unRegisterDevice(Session s) throws JAXBException, IOException {
        devices.remove(s.getId());
    }

    public Map<String, WsDevice> getDevices() {
        return devices;
    }

    public WsDevice getDevice(String sessionId) {
        return devices.entrySet().stream().filter(wsDeviceEntry -> wsDeviceEntry.getKey().equals(sessionId)).findAny().get().getValue();
    }

    private void handleTerminalResponse(String sessionId, String terminalResponse){
        getDevice(sessionId).setTerminalResponse(terminalResponse);
    }
}
