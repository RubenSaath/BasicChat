package command;

import java.util.HashMap;
import java.util.Map;

public class CommandInterpreter {
    private final Map<String, Command> commands = new HashMap<>();

    public void registerCommand(String keyword, Command command) {
        commands.put(keyword, command);
    }

    public void executeCommand(String input) {
        String keyword = input.split(" ")[0];
        Command command = commands.get(keyword);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Unknown command: " + keyword);
        }
    }
}
