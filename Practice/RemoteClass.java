import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteClass extends UnicastRemoteObject implements RemoteInterface {
    public RemoteClass() throws RemoteException {
        super();
    }

    @Override
    public int sum(int x, int y) throws RemoteException {
        return x + y;
    }
}
