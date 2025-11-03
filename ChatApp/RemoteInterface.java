import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {
    public String Chat(String msg) throws RemoteException;
}
