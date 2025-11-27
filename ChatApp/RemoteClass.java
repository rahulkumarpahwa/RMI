import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RemoteClass extends UnicastRemoteObject implements RemoteInterface {
    RemoteClass() throws RemoteException {
        super();
    }

    @Override
    public String Chat(String msg) throws RemoteException {
        System.out.println("Client : " + msg);
        if (msg.equals("Hello Server!")) {
            return "Server : Hello from Server";
        } else if (msg.equals("You are Joker!")) {
            return "Server : Same to You!";
        }
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        sc.close();
        return "Server : " + line;
    }
}
