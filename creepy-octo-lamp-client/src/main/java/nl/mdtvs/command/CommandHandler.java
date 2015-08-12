package nl.mdtvs.command;

import lombok.NonNull;
import nl.mdtvs.modules.DosCommand;
import nl.mdtvs.modules.EncryptionCommand;
import nl.mdtvs.modules.TerminalCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    public static final String ACTION_WAS_NOT_FOUND = "Action was not found";
    private Map<String, Command> commandMap = new HashMap<>();

    public CommandHandler() {
        addCommand(new DosCommand());
        addCommand(new EncryptionCommand());
        addCommand(new TerminalCommand());
    }

    public Message execute(@NonNull Message m){
        Command c = this.commandMap.get(m.getAction());
        if (c != null) {
            return c.execute(m.getMessage());
        }
        return new Message("exception", ACTION_WAS_NOT_FOUND);
    }

    private void addCommand(Command c){
        this.commandMap.put(c.getCommandName(), c);
    }
}
