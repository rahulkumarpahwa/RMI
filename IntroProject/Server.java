import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String args[]) throws Exception {
        // Create (or get) the RMI registry on the local host at default port 1099
        try {
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry created on port 1099");
        } catch (Exception e) {
            // registry might already be running
            System.out.println("RMI registry already running or failed to create: " + e.getMessage());
        }

        ImplementClass obj = new ImplementClass();
        Naming.rebind("REMOTEOBJ", obj); // bind the remote object with the name "REMOTEOBJ"
        System.out.println("Server Started and bound 'ADD'!");
    }
}
