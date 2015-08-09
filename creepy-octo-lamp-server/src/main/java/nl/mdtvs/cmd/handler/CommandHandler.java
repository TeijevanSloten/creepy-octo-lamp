package nl.mdtvs.cmd.handler;

import nl.mdtvs.cmd.invoker.Command;
import java.util.HashMap;
import javax.websocket.Session;
import nl.mdtvs.cmd.DeviceManager;

public class CommandHandler {

    private static final HashMap<Integer, Command> cmdList = new HashMap<>();
    private static final CommandHandler INSTANCE = new CommandHandler();

    private CommandHandler() {
        init();
    }

    private void init() {
        cmdList.put(CmdEnum.REGISTER_SESSION.getHashKey(),new Command(DeviceManager.getInstance(), "registerSession", new Class[]{Session.class}));
        cmdList.put(CmdEnum.UNREGISTER_SESSION.getHashKey(),new Command(DeviceManager.getInstance(), "unRegisterSession", new Class[]{Session.class}));
        cmdList.put(CmdEnum.REGISTER_DEVICE.getHashKey(),new Command(DeviceManager.getInstance(), "registerDevice", new Class[]{String.class, Session.class}));
        cmdList.put(CmdEnum.UNREGISTER_DEVICE.getHashKey(),new Command(DeviceManager.getInstance(), "unRegisterDevice", new Class[]{Session.class}));
    }

    public static CommandHandler getInstance() {
        return INSTANCE;
    }

    public void executeCommand(CmdEnum cmd, Object[] args) {
        cmdList.get(cmd.getHashKey()).execute(args);
    }
}
