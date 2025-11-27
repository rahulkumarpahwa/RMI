// package Practice_ChatApp;

import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            CreateInterface obj = (CreateInterface) Naming.lookup("rmi://localhost:1997/practice_chatapp");
            System.out.println("Client Initiated:");
            Scanner sc = new Scanner(System.in);
            while (true) {
                String input = sc.nextLine();
                if (input.equals("Exit") || input.equals("exit")) {
                    System.out.println("Client Ended!");
                    sc.close();
                    return;
                }
                String chat = obj.Chat_Fxn(input);
                System.out.println(chat);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
