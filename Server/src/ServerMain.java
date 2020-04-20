import server.Server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    //setup view and server
    ServerModel server = new ServerModelManager();
    SimpleConsoleView view = new SimpleConsoleView(server);

    //start the server
    server.start();


    public static void main(String[] args) throws RemoteException, MalformedURLException {
        startRegistry();

        Server sever = new Server();
        sever.start();
        System.out.println("Server started...");
    }

    public static void startRegistry() throws RemoteException {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started...");
        }
        catch (java.rmi.server.ExportException e) {
            System.out.println("Registry already started?");
        }
    }
}
