package nl.mdtvs.cmd.handler;

import java.util.HashMap;
import javax.websocket.Session;
import nl.mdtvs.cmd.DeviceManager;
import nl.mdtvs.cmd.invoker.Command;

public class CommandHandler123 {

    private final HashMap<Integer, Command> cmdList = new HashMap<>();

    public CommandHandler123() {
        cmdList.put(CmdEnum.REGISTER_DEVICE.getHashKey(), new Command(DeviceManager.getInstance(), "registerDevice", new Class[]{String.class, Session.class}));
        cmdList.put(CmdEnum.UNREGISTER_DEVICE.getHashKey(), new Command(DeviceManager.getInstance(), "unRegisterDevice", new Class[]{Session.class}));
        cmdList.put(CmdEnum.TERMINAL_RESPONSE.getHashKey(), new Command(DeviceManager.getInstance(), "handleTerminalResponse", new Class[]{String.class, Session.class}));
    }

    public void executeCommand(CmdEnum cmd, Object[] args) {
        cmdList.get(cmd.getHashKey()).execute(args);
    }
}
