package nl.mdtvs.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import nl.mdtvs.command.Command;
import nl.mdtvs.command.Message;

public class TerminalCommand implements Command {
    @Override
    public String getCommandName() {
        return "terminal";
    }

    @Override
    public Message execute(String message) {
        try {
            Process p = Runtime.getRuntime().exec(message);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String output = in.lines().reduce((acc, curr) -> acc.concat("\n\r" + curr)).get();

            return new Message("TERMINAL_RESPONSE", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Message("TERMINAL_RESPONSE", "No message received");
    }
}
