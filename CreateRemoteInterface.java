import java.rmi.Remote;

public interface CreateRemoteInterface extends Remote {
    public int add(int x, int y) throws Exception;
}


// steps :
/*
 * 1. Create the remote interface extending the Remote interface.
 * 2. Implement the Interface we created in the class.
 * 3. Compile, Stub and Skeleton (RMIC)
 * 4. Start the registry. (rmi registry)
 * 5. create and start the server.
 * 6. create and start the client.
 *  command to execute after creating the class files of all:
 *  1. rmic ImplementClass (creates the stub)
 *  2. start rmiregistry (creating the registry)
 *  3. java server (start server)
 *  4. java client (start client)
 * // the result get printed.
 *  */