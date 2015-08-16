package nl.mdtvs.modules;

import nl.mdtvs.command.Command;
import nl.mdtvs.command.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TerminalCommand implements Command {
    @Override
    public String getCommandName() {
        return "terminal";
    }

    @Override
    public Message execute(String message) {
        try {
            return new Message("TERMINAL_RESPONSE", getResponse(executeMessage(message)));
        } catch (IOException e) {
            return new Message("exception", e.getMessage());
        }
    }

    private String getResponse(BufferedReader in) {
        return in.lines().reduce((acc, curr) -> acc.concat("\n" + curr)).get();
    }

    private BufferedReader executeMessage(String message) throws IOException {
        InputStream in = Runtime.getRuntime().exec(message).getInputStream();
        return new BufferedReader(new InputStreamReader(in));
    }
}
