package nl.mdtvs.command;

import java.util.HashMap;
import java.util.Map;
import javax.websocket.Session;
import lombok.NonNull;
import nl.mdtvs.cmd.modules.RegisterDeviceCommand;
import nl.mdtvs.cmd.modules.UnRegisterDeviceCommand;

public class CommandHandler {

    private Map<String, Command> commandMap = new HashMap<>();

    public CommandHandler() {
        addCommand(new RegisterDeviceCommand());
        addCommand(new UnRegisterDeviceCommand());
    }

    public Message execute(@NonNull Message m, @NonNull Session session) {
        Command c = this.commandMap.get(m.getActionName());
        if (c != null) {
            return c.execute(m.getActionMessage(), session);
        }
        return new Message("actionNotFound", "Action was not found");
    }

    private void addCommand(Command c) {
        this.commandMap.put(c.getCommandName(), c);
    }

    public boolean isAvailableCommand(String actionName) {
        return commandMap.containsKey(actionName);
    }
}
