import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        try {
            RemoteInterface obj = (RemoteInterface) Naming.lookup("rmi://localhost:9999/CHAT");
            System.out.println("Chat Started!");
            while (true) {
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                if (line.equals("exit") || line.equals("EXIT") || line.equals("Exit")) {
                    System.out.println("Client Exiting!");
                    sc.close();
                    return;
                }
                String reply = obj.Chat(line);
                System.out.println(reply);
            }
        } catch (Exception e) {
            System.out.println("Connection failed!");
            System.out.println(e.getMessage());
        }
    }
}