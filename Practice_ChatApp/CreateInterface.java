// package Practice_ChatApp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CreateInterface extends Remote {
    public String Chat_Fxn(String chat) throws RemoteException;
}
