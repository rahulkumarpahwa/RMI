import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        RemoteInterface obj = (RemoteInterface) Naming.lookup("rmi://localhost:9999/CHAT");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String reply = obj.Chat(line);
        System.out.println(reply);
        while (true) {
            if (line.equals("exit") || line.equals("EXIT") || line.equals("Exit")) {
                sc.close();
                return;
            }
            line = sc.nextLine();
            reply = obj.Chat(line);
            System.out.println(reply);
        }
    }
}