import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1998);
            RemoteClass obj = new RemoteClass();
            Naming.rebind("rmi://localhost:1998/practice_sum", obj);
            System.out.println("Registry has been created and server has been started!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
