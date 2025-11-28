// package Practice_ChatApp;

import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class CreateClass extends UnicastRemoteObject implements CreateInterface {
    public CreateClass() throws Exception {
        super();
    }

    // Reuse a single Scanner for System.in to avoid closing the global input
    // stream.
    // private static final
    Scanner SERVER_SCANNER = new Scanner(System.in);

    @Override
    public String Chat_Fxn(String chat) {
        try {
            System.out.println("Client : " + chat);
            String input = SERVER_SCANNER.nextLine();
            if (input.equals("Exit") || input.equals("exit")) {
                System.out.println("Server Ended!");
                SERVER_SCANNER.close();
                return "Server Exited!";
            }
            return input;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error: " + e.getMessage();
        }
    }
}
