package nl.mdtvs.websocket;

import java.util.HashMap;
import javax.websocket.Session;

public class GuiHandler {

    private final HashMap<String, Session> serverGui = new HashMap();
    private static final GuiHandler guihandler = new GuiHandler();

    private GuiHandler() {
    }

    public static GuiHandler getInstance() {
        return guihandler;
    }

    public void addServerGui(Session serverGui) {
        this.serverGui.put(serverGui.getId(), serverGui);
    }

    public void removeServerGui(Session serverGui) {
        this.serverGui.remove(serverGui.getId());
    }

    public Session getServerGui(Session serverGui) {
        return this.serverGui.get(serverGui.getId());
    }

    public HashMap<String, Session> getServerGui() {
        return this.serverGui;
    }
}
