package cli;

import command.CommandInterpreter;
import communication.ConnectionInterface;

import java.util.Scanner;

public class CommandLineInterface {
    private final CommandInterpreter interpreter;
    private final ConnectionInterface connection;
    private String username = "Guest";

    public CommandLineInterface(CommandInterpreter interpreter, ConnectionInterface connection) {
        this.interpreter = interpreter;
        this.connection = connection;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.startsWith("/")) {
                interpreter.executeCommand(input.substring(1));
            } else {
                try {
                    connection.sendMessage(username + ": " + input);
                } catch (Exception e) {
                    System.out.println("Failed to send message: " + e.getMessage());
                }
            }
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
