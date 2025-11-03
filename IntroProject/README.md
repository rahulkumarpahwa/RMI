# RMI (Remote Method Invocation) - Complete Guide

## 📋 Overview

This project demonstrates **Java RMI (Remote Method Invocation)**, a mechanism that allows a Java program running on one computer to invoke methods on a Java object located on another computer across a network.

### What is RMI?

RMI enables you to:

- Call methods on objects that exist on different machines
- Build distributed applications
- Create client-server architectures
- Work with remote objects transparently (as if they were local)

---

## 🏗️ Architecture

The RMI system consists of three main components:

```
┌─────────────┐         Network       ┌──────────────┐
│   CLIENT    │◄────────────────────► │    SERVER    │
│             │                       │              │
│ - Calls     │                       │ - Hosts      │
│ - Remote    │                       │ - Implements │
│   Methods   │                       │ - Registers  │
└─────────────┘                       └──────────────┘
       ▲                                       ▲
       └───────────────────┬───────────────────┘
                    ┌──────────────┐
                    │ RMI Registry │
                    │ (Port 1099)  │
                    └──────────────┘
```

---

## 📁 Project Files

### 1. **CreateRemoteInterface.java** - Remote Interface

```java
import java.rmi.Remote;

public interface CreateRemoteInterface extends Remote {
    public int add(int x, int y) throws Exception;
}
```

**Explanation:**

- Extends `Remote` interface from `java.rmi` package
- Declares the `add()` method that will be called remotely
- All methods must throw `Exception` (can throw `RemoteException`)
- This interface is implemented by the server and used by the client

---

### 2. **ImplementClass.java** - Remote Object Implementation

```java
import java.rmi.server.UnicastRemoteObject;

public class ImplementClass extends UnicastRemoteObject implements CreateRemoteInterface {
    public ImplementClass() throws Exception {
        super(); // Calls UnicastRemoteObject constructor
    }

    @Override
    public int add(int x, int y) {
        return x + y;
    }
}
```

**Explanation:**

- Extends `UnicastRemoteObject` (provides RMI functionality)
- Implements the `CreateRemoteInterface` interface
- Constructor calls `super()` to initialize the remote object
- Implements the `add()` method with actual logic
- `UnicastRemoteObject` automatically:
  - Creates stub and skeleton
  - Enables remote communication
  - Handles serialization

---

### 3. **Server.java** - RMI Server

```java
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
        Naming.rebind("ADD", obj); // bind the remote object with the name "ADD"
        System.out.println("Server Started and bound 'ADD'!");
    }
}
```

**Explanation:**

- `LocateRegistry.createRegistry(1099)` - Creates the RMI registry on port 1099
- `new ImplementClass()` - Creates an instance of the remote object
- `Naming.rebind("ADD", obj)` - Registers the object with name "ADD" in the registry
- The server waits indefinitely for client connections

#### Registry Creation Code Breakdown

The Server uses a try-catch block around `LocateRegistry.createRegistry(1099)` to create the RMI registry robustly:

```java
try {
    LocateRegistry.createRegistry(1099);
    System.out.println("RMI registry created on port 1099");
} catch (Exception e) {
    // registry might already be running
    System.out.println("RMI registry already running or failed to create: " + e.getMessage());
}
```

**What it does:**

- **`LocateRegistry.createRegistry(1099)`** - Attempts to start an in-JVM RMI registry listening on port 1099
- **Success scenario**: Prints confirmation and the server proceeds to bind remote objects
- **Failure scenario**: Catches exceptions (e.g., "Address already in use") and prints a message instead of crashing; the server can still bind to an existing registry

**Why use this pattern:**

- ✅ **Avoids requiring a separate `rmiregistry` process** - Registry runs inside the JVM
- ✅ **Makes server startup safe to run multiple times** - Won't crash if registry already exists
- ✅ **Provides clear diagnostics** - Informs user about registry status
- ✅ **Robust error handling** - Continues execution even if registry creation fails

**Example scenarios:**

1. **First run** - Registry successfully created:

   ```
   RMI registry created on port 1099
   Server Started and bound 'ADD'!
   ```

2. **Port already in use** - Registry catch block executes:
   ```
   RMI registry already running or failed to create: Address already in use
   Server Started and bound 'ADD'!
   ```
   Server continues and binds to the existing registry!

**After registry is ready**, the server registers the remote object:

```java
ImplementClass obj = new ImplementClass();
Naming.rebind("ADD", obj);
System.out.println("Server Started and bound 'ADD'!");
```

This ensures the registry is available (newly created or already running) before the remote object is registered with name "ADD".

---

### 4. **Client.java** - RMI Client

```java
import java.rmi.Naming;

public class Client {
    public static void main(String args[]) throws Exception {
        CreateRemoteInterface obj = (CreateRemoteInterface) Naming.lookup("ADD");
        // we don't need the obj of the class so we will typecast to interface.
        int n = obj.add(5, 4);
        System.out.println("The sum of the numbers is : " + n);
    }
}
```

**Explanation:**

- `Naming.lookup("ADD")` - Searches the registry for object named "ADD"
- Casts it to `CreateRemoteInterface` (not `ImplementClass`)
- Calls `add(5, 4)` on the remote object
- Prints the result (9)

---

## 🔄 How RMI Works - Step by Step

### 1. **Compilation Phase**

```bash
javac -source 1.8 -target 1.8 *.java
```

- Compiles all Java files
- Creates `.class` files for each Java source

### 2. **Registry Creation**

```bash
java -cp . Server
```

- Server creates RMI Registry on port 1099
- Registry maintains a lookup table of remote objects

### 3. **Object Registration**

```
Server creates ImplementClass object
         ↓
Server calls Naming.rebind("ADD", obj)
         ↓
Registry stores: "ADD" → reference to ImplementClass object
```

### 4. **Client Lookup**

```bash
java -cp . Client
```

- Client calls `Naming.lookup("ADD")`
- Registry returns a stub (proxy) to the remote object
- Client receives the stub and treats it like local object

### 5. **Remote Method Invocation**

```
Client calls: obj.add(5, 4)
         ↓
Stub marshals arguments (5, 4)
         ↓
Sent over network to Server
         ↓
Skeleton unmarshals and calls: real_obj.add(5, 4)
         ↓
Server executes: 5 + 4 = 9
         ↓
Result (9) marshalled and sent back
         ↓
Stub unmarshals and returns: 9
         ↓
Client receives: 9
```

### 6. **Output**

```
The sum of the numbers is : 9
```

---

## 🚀 How to Run

### Prerequisites

- Java 8 or higher installed
- Both files in the same directory

### Step 1: Navigate to RMI Folder

```bash
cd '/RMI'
```

### Step 2: Compile All Java Files

```bash
javac -source 1.8 -target 1.8 *.java
```

- Compiles for Java 1.8 compatibility
- Creates `.class` files in the same directory

### Step 3: Start the Server (Terminal 1)

```bash
java -cp . Server
```

**Expected Output:**

```
RMI registry created on port 1099
Server Started and bound 'ADD'!
```

- Keep this terminal open and running

### Step 4: Run the Client (Terminal 2)

```bash
java -cp . Client
```

**Expected Output:**

```
The sum of the numbers is : 9
```

- Client runs, calls remote method, prints result, and exits

---

## 📊 Key RMI Concepts

### Remote Interface

- Must extend `java.rmi.Remote`
- All methods must throw `RemoteException` or superclass `Exception`
- Defines the contract between client and server

### Stub and Skeleton

- **Stub**: Client-side proxy (automatically created)

  - Marshals method arguments
  - Sends request to skeleton
  - Unmarshals result
  - Returns result to client

- **Skeleton**: Server-side proxy (automatically created)
  - Unmarshals method arguments
  - Calls actual remote object method
  - Marshals result
  - Sends result back to stub

### UnicastRemoteObject

- Base class for remote objects
- Provides marshalling/unmarshalling support
- Automatically handles stub/skeleton creation
- Uses TCP/IP for communication

### Naming Service

- `Naming.rebind()` - Register object (overwrites if exists)
- `Naming.bind()` - Register object (error if exists)
- `Naming.lookup()` - Find object by name
- `Naming.unbind()` - Unregister object

### RMI Registry

- Lookup service for remote objects
- Runs on port 1099 by default
- Maintains name-to-object mappings
- Can be local or remote

---

## 🔌 Network Communication

### Default Ports

| Component    | Port               |
| ------------ | ------------------ |
| RMI Registry | 1099               |
| Dynamic RMI  | Random (high port) |

### URL Format

```
rmi://hostname:port/objectname
```

Example: `rmi://localhost:1099/ADD`

---

## 🎯 Advantages of RMI

✅ **Simplicity**: Works like calling local methods  
✅ **Transparency**: Client doesn't know it's remote  
✅ **Type Safety**: Java type checking at compile time  
✅ **Distributed Computing**: Build multi-machine systems  
✅ **Automatic Stub Generation**: No manual proxy coding

---

## ⚠️ Limitations & Considerations

❌ **Java-Only**: Both client and server must use Java  
❌ **Network Overhead**: Slower than local calls  
❌ **Complexity**: Thread management, timeouts  
❌ **Security**: Requires careful network configuration  
❌ **Firewall Issues**: May need port forwarding

---

## 🔍 Troubleshooting

### Error: `Connection refused`

**Cause:** Server not running  
**Solution:** Start server first with `java -cp . Server`

### Error: `UnsupportedClassVersionError`

**Cause:** Compiled with newer Java than runtime  
**Solution:** Use `javac -source 1.8 -target 1.8 *.java`

### Error: `RemoteException: cannot export object`

**Cause:** Port already in use  
**Solution:** Change registry port or kill process using port 1099

### Error: `NotBoundException`

**Cause:** Object not registered in registry  
**Solution:** Verify object name matches (case-sensitive)

---

## 📚 Learning Path

1. **Understand** the three components (Interface, Implementation, Server, Client)
2. **Run** the example step-by-step
3. **Modify** the `add()` method to do something different
4. **Extend** with more methods (subtract, multiply, divide)
5. **Experiment** with multiple clients connecting
6. **Study** error cases and recovery

---

## 🔧 Advanced Topics

### Add More Methods

Update `CreateRemoteInterface.java`:

```java
public interface CreateRemoteInterface extends Remote {
    public int add(int x, int y) throws Exception;
    public int subtract(int x, int y) throws Exception;
    public int multiply(int x, int y) throws Exception;
}
```

### Remote Exception Handling

```java
try {
    CreateRemoteInterface obj = (CreateRemoteInterface) Naming.lookup("ADD");
    int result = obj.add(5, 4);
} catch (RemoteException e) {
    System.out.println("Network error: " + e.getMessage());
} catch (NotBoundException e) {
    System.out.println("Object not found in registry");
}
```

### Multiple Server Instances

```java
ImplementClass obj1 = new ImplementClass();
Naming.rebind("ADD1", obj1);

ImplementClass obj2 = new ImplementClass();
Naming.rebind("ADD2", obj2);
```

---

## 📋 Quick Reference

| Task                      | Command                                                   |
| ------------------------- | --------------------------------------------------------- |
| Compile                   | `javac -source 1.8 -target 1.8 *.java`                    |
| Start Server              | `java -cp . Server`                                       |
| Run Client                | `java -cp . Client`                                       |
| View Classpath            | `echo %CLASSPATH%` (Windows) or `echo $CLASSPATH` (Linux) |
| Kill Process on Port 1099 | `netstat -ano \| findstr :1099` (Windows)                 |

---

## 📖 Java RMI Documentation

For more information:

- [Oracle RMI Documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.rmi/module-summary.html)
- [RMI Tutorial](https://docs.oracle.com/javase/tutorial/rmi/)
- [RMI Architecture](https://docs.oracle.com/javase/tutorial/rmi/overview.html)

---

## ✅ Summary

**RMI** allows Java objects to invoke methods on remote objects as if they were local. This project demonstrates:

- ✅ Creating a remote interface
- ✅ Implementing remote objects
- ✅ Registering objects in RMI Registry
- ✅ Client lookup and remote method invocation
- ✅ Network communication between JVMs

With RMI, you can build powerful distributed applications using pure Java!

---

## 🎓 Example Modifications

### Make it Return Multiple Values

```java
public class Result {
    public int sum;
    public long timestamp;
}

public interface CreateRemoteInterface extends Remote {
    public Result add(int x, int y) throws Exception;
}
```

### Add Logging

```java
public class Server {
    public static void main(String args[]) throws Exception {
        LocateRegistry.createRegistry(1099);
        ImplementClass obj = new ImplementClass();
        Naming.rebind("ADD", obj);
        System.out.println("[" + new Date() + "] Server started");
        while(true) { Thread.sleep(1000); }
    }
}
```

### Handle Client Disconnections

```java
try {
    CreateRemoteInterface obj = (CreateRemoteInterface) Naming.lookup("ADD");
    int result = obj.add(5, 4);
} catch (ConnectException e) {
    System.out.println("Server is down!");
}
```

---

**Created:** October 27, 2025  
**Version:** 1.0
