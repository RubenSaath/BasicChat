package main;

import cli.CommandLineInterface;
import command.CommandInterpreter;
import communication.ConnectionInterface;
import communication.TcpConnection;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConnectionInterface connection = new TcpConnection();
        CommandInterpreter interpreter = new CommandInterpreter();

        CommandLineInterface cli = new CommandLineInterface(interpreter, connection);

        interpreter.registerCommand("setname", () -> {
            System.out.print("Enter new username: ");
            Scanner scanner = new Scanner(System.in);
            String newName = scanner.nextLine();
            cli.setUsername(newName);
            System.out.println("Username updated to: " + newName);
        });

        System.out.println("Starting Chat Application...");
        cli.start();
    }
}