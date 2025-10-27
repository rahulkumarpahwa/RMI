We will discuss  **RMI Registry Creation** code in detail:

```java
try {
    LocateRegistry.createRegistry(1099);
    System.out.println("RMI registry created on port 1099");
} catch (Exception e) {
    System.out.println("RMI registry already running or failed to create: " + e.getMessage());
}
```

---

## Breaking it down:

### **1. `try { ... } catch { ... }`** (Error Handling)

This is a try-catch block that handles errors gracefully.

- **`try`** - Attempts to create the registry
- **`catch`** - If it fails, catches the error instead of crashing

### **2. `LocateRegistry.createRegistry(1099)`** (Main Line)

This line does the heavy lifting:

| Part                    | Meaning                                |
| ----------------------- | -------------------------------------- |
| **`LocateRegistry`**    | Java RMI class that manages registries |
| **`.createRegistry()`** | Method to create/start an RMI registry |
| **`1099`**              | Port number where registry listens     |

---

## What is Port 1099?

**Port 1099** is the **default RMI registry port**:

- It's like a "telephone number" for the RMI registry
- Server and Client communicate through this port
- 1099 is the standard (you can change it, but 1099 is convention)

```
Network:
┌─────────────────────────────────────┐
│          Internet                    │
│                                      │
│  Port 1099 = RMI Registry listening  │
│  Port 8080 = Web server              │
│  Port 5432 = Database                │
│  Port 3306 = MySQL                   │
└─────────────────────────────────────┘
```

---

## Why use try-catch here?

### **Scenario 1: First time running**

```bash
java -cp . Server
# Output:
# RMI registry created on port 1099
# Server Started and bound 'ADD'!
```

✅ Registry created successfully!

---

### **Scenario 2: Registry already running**

If you run the server twice in the same JVM:

```bash
java -cp . Server  # First time - success ✅
java -cp . Server  # Second time - catches exception
# Output:
# RMI registry already running or failed to create: Address already in use
# Server Started and bound 'ADD'!  (still works!)
```

❌ Can't create twice (port already in use)  
✅ But code handles it gracefully, doesn't crash

---

## Step-by-step flow:

```
1. try {
   ├─ Attempts: LocateRegistry.createRegistry(1099)
   │
   ├─ Success? ✅
   │  └─ Print: "RMI registry created on port 1099"
   │  └─ Continue to next line
   │
   └─ Failure? ❌
      └─ Jump to catch block

2. } catch (Exception e) {
   ├─ Print: "RMI registry already running or..."
   └─ Continue anyway (doesn't crash)

3. ImplementClass obj = new ImplementClass();
   Naming.rebind("ADD", obj);  // This still happens!
```

---

## Real-world analogy:

Think of it like **opening a restaurant**:

```java
try {
    // Try to rent a building on Main Street (port 1099)
    LocateRegistry.createRegistry(1099);
    System.out.println("Got the restaurant location!");
} catch (Exception e) {
    // Building already rented or unavailable
    System.out.println("Location unavailable, but we'll work with existing one");
}
```

Even if you can't rent a new building, you can still use an existing one! 🏪

---

## What happens after this code:

```java
// If registry created successfully:
ImplementClass obj = new ImplementClass();
Naming.rebind("ADD", obj);
System.out.println("Server Started and bound 'ADD'!");

// Now the registry has:
// ├─ Running on port 1099
// └─ Service "ADD" registered
```

---

## Complete flow diagram:

```
┌─────────────────────────────────────┐
│      java -cp . Server              │
└──────────────┬──────────────────────┘
               │
               ▼
    ┌──────────────────────┐
    │ try {                │
    │  createRegistry      │
    │  (1099)              │
    └────┬──────────┬──────┘
         │          │
      Success    Failure
         │          │
         ▼          ▼
      Print ✅   catch { }
                  Print ⚠️
         │          │
         └────┬─────┘
              │
              ▼
    ┌──────────────────────┐
    │ Create ImplementClass│
    │ Bind to registry     │
    │ Server ready!        │
    └──────────────────────┘
```

---

## Summary:

```java
try {
    // Try to create/start RMI registry on port 1099
    LocateRegistry.createRegistry(1099);
    // If success, print this:
    System.out.println("RMI registry created on port 1099");
} catch (Exception e) {
    // If it fails (already exists), catch the error and print this:
    System.out.println("RMI registry already running or failed to create: " + e.getMessage());
}
```

**In simple terms:**
_"Try to start the RMI registry. If it's already running, don't crash — just use the existing one."_ 🚀

---

## Why this is important:

✅ **Graceful Error Handling** - Code doesn't crash  
✅ **Reusability** - Can run server multiple times  
✅ **Robustness** - Handles edge cases  
✅ **User-Friendly** - Informative messages
