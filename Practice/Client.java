import java.rmi.Naming;

public class Client {
    public static void main(String[] args) throws Exception {
        RemoteInterface obj = (RemoteInterface) Naming.lookup("rmi://localhost:1998/practice_sum");
        System.out.println("The Sum of the two numbers is : " + obj.sum(3, 6));
    }

}
