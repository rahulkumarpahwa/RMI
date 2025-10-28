import java.rmi.Naming;

public class Client {
    public static void main(String args[]) throws Exception {
        CreateRemoteInterface obj = (CreateRemoteInterface) Naming.lookup("ADD");
        // we don't need the obj of the class so we will typecast to interface.
        int n = obj.add(5, 4);
        int sub = obj.sub(9, 7);
        System.out.println("The sum of the numbers is : " + n);
        System.out.println("The subtract of the numbers is : " + sub);
    }
}
