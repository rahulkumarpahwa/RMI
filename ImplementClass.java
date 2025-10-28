import java.rmi.server.UnicastRemoteObject;


public class ImplementClass extends UnicastRemoteObject implements CreateRemoteInterface {
    public ImplementClass() throws Exception {
        super(); // to call the constructor of the UnicastRemoteObject and create the stub and Skeleton.
    }

    @Override
    public int add(int x, int y) {
        return x + y;
    }

    @Override
    public int sub(int x, int y) {
        return x - y;
    }
}
