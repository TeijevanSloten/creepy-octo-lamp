package nl.mdtvs.command;

import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import nl.mdtvs.cmd.modules.RegisterDeviceCommand;
import nl.mdtvs.cmd.modules.UnRegisterDeviceCommand;

public class CommandHandler {

    private Map<String, Command> commandMap = new HashMap<>();

    public CommandHandler() {
        addCommand(new RegisterDeviceCommand());
        addCommand(new UnRegisterDeviceCommand());
    }

    private Object[] args;

    @NonNull
    public CommandHandler input(Object[] args) {
        this.args = args;
        return this;
    }

    public Message execute(@NonNull Message m) {
        Command c = this.commandMap.get(m.getActionName());
        if (c != null && args != null) {
            return c.input(args).execute(m.getActionMessage());
        } else if (c != null) {
            return c.execute(m.getActionMessage());
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
