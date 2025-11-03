Here are the bash commands you need to run to start the RMI Server (in order):

cd '/RMI'

javac -source 1.8 -target 1.8 *.java

java -cp . Server

Summary:

1. Navigate to the RMI folder
2. Compile all Java files targeting Java 1.8 (to match your Java 8 runtime)
3. Run the Server

The Server will:

-> ✅ Create the RMI registry on port 1099 (internally)
-> ✅ Bind the ADD remote object
-> ✅ Print: RMI registry created on port 1099 and Server Started and bound 'ADD'!
-> ✅ Keep running and listening for client connections
-> Once the server is running, in a separate terminal, run the client:

cd '/RMI'

java -cp . Client

Then Output will printed as:
The sum of the numbers is : 9


note : 
Breaking it down:
javac -source 1.8 -target 1.8 *.java
Part	Meaning
javac	Java Compiler (converts .java → .class)
-source 1.8	Compile using Java 8 syntax rules
-target 1.8	Generate bytecode compatible with Java 8 runtime
*.java	All files ending with .java in current folder

Input Files (*.java):
├── Client.java
├── Server.java
├── CreateRemoteInterface.java
└── ImplementClass.java
        ↓
    javac -source 1.8 -target 1.8 *.java
        ↓
Output Files (.class):
├── Client.class
├── Server.class
├── CreateRemoteInterface.class
└── ImplementClass.class