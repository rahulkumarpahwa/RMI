import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) throws Exception {
        try {
            LocateRegistry.createRegistry(9999); // type of the port is int.
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        RemoteClass obj = new RemoteClass();
        Naming.rebind("rmi://localhost:9999/CHAT", obj);
        System.out.println("Server Started!");
    }
}
