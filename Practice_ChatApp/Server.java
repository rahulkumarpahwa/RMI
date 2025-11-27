// package Practice_ChatApp;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1997);
            CreateClass obj = new CreateClass();
            Naming.rebind("rmi://localhost:1997/practice_chatapp", obj);
            System.out.println("Server has been started!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
