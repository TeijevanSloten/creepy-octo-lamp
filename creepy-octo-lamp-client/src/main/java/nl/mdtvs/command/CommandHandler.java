package nl.mdtvs.command;

import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import nl.mdtvs.modules.DosCommand;
import nl.mdtvs.modules.EncryptionCommand;
import nl.mdtvs.modules.TerminalCommand;

public class CommandHandler {

    private Map<String, Command> commandMap = new HashMap<>();

    public CommandHandler() {
        addCommand(new DosCommand());
        addCommand(new EncryptionCommand());
        addCommand(new TerminalCommand());
    }

    public Message execute(@NonNull Message m){
        Command c = this.commandMap.get(m.getActionName());
        if (c != null) {
            return c.execute(m.getActionMessage());
        }
        return new Message("actionNotFound", "Action was not found");
    }

    private void addCommand(Command c){
        this.commandMap.put(c.getCommandName(), c);
    }
}
