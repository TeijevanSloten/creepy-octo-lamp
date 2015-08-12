package nl.mdtvs.command.handler;

import nl.mdtvs.command.PropertiesCommand;
import nl.mdtvs.command.TerminalCommand;
import nl.mdtvs.models.Message;
import nl.mdtvs.models.WsDevice;

import java.util.HashMap;

public class CommandHandler {

    private final HashMap<String, Command> cmdList = new HashMap<>();

    public CommandHandler() {
        addCommand(new TerminalCommand());
        addCommand(new PropertiesCommand());
    }

    private void addCommand(Command c) {
        cmdList.put(c.getCommandName(), c);
    }

    public void execute(Message message, WsDevice device) {
        Command command = cmdList.get(message.getAction());
        if (command != null) {
            command.execute(message.getMessage(), device);
        } else {
            throw new IllegalArgumentException("Command '" + message.getAction() + "' not found!");
        }
    }
}
