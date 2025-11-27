// package Practice_ChatApp;

import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class CreateClass extends UnicastRemoteObject implements CreateInterface {
    public CreateClass() throws Exception {
        super();
    }

    @Override
    public String Chat_Fxn(String chat) {
        try {
            System.out.println("Client : " + chat);
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            sc.close();
            return input;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error: " + e.getMessage();
        }
    }
}
