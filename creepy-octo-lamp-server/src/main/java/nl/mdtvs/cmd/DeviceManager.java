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
    private static final Map<String, Session> sessions = new HashMap<>();
    private static final Map<String, WsDevice> devices = new HashMap<>();

    private DeviceManager() {
    }

    public static DeviceManager getInstance() {
        return INSTANCE;
    }

    public void registerSession(Session s) {
        sessions.put(s.getId(), s);
    }

    public void unRegisterSession(Session s) {
        sessions.remove(s.getId());
    }

    public void registerDevice(String jsonSting, Session s) throws JAXBException, IOException {
        WsDevice wsd = new WsDevice(s, ConvertObject.jsonStringToMap(jsonSting));
        devices.put(s.getId(), wsd);
    }

    public void unRegisterDevice(Session s) throws JAXBException, IOException {
        devices.remove(s.getId());
    }

    public Map<String, Session> getSessions() {
        return sessions;
    }

    public Map<String, WsDevice> getDevices() {
        return devices;
    }
}
