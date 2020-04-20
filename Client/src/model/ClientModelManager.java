package model;

import mediator.Client;
import mediator.WarehouseClient;
import mediator.WarehouseServer;
import utility.ObserverSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientModelManager implements ClientModel, ObserverSubject
{
    private WarehouseClient client;
    private WarehouseServer server;
    private PropertyChangeSupport property;

    public ClientModelManager() throws RemoteException
    {
        //setup observable subject
        property = new PropertyChangeSupport(this);

        client = new Client(this);

        //connect to the server
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            server = (WarehouseServer) registry.lookup("server");
        } catch (Exception exception){
            System.out.println("connection failed");
            System.exit(0);
        }

        //test the connection
        try
        {
            System.out.println(server.ping());
        }
        catch (RemoteException e)
        {
            System.out.println("server does not respond, exiting");
            System.exit(0);
        }

        //register client
    }

    @Override public void userError(String message)
    {
        property.firePropertyChange("userError", null, message);
        debugLog("user error:" + message);
    }

    @Override public void debugLog(String message)
    {
        System.out.println("debug>>");
    }

    @Override public UserType login(String username, String password)
    {
        try
        {
            return server.login(username, password);
        }
        catch (RemoteException e)
        {
            System.out.println("error in remote login method");
        }
        return null;
    }

    @Override public void addListener(PropertyChangeListener listener)
    {
        property.addPropertyChangeListener(listener);
    }

    @Override public void removeListener(PropertyChangeListener listener)
    {
        property.addPropertyChangeListener(listener);
    }

    //    @Override public void registerClient()
//    {
//        try
//        {
//            server.registerClient(client, user);
//        }
//        catch (RemoteException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    @Override public void broadCast(Message message)
//    {
//        try
//        {
//            server.broadCast(message, client);
//        }
//        catch (RemoteException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    @Override public void requestUserList()
//    {
//        try
//        {
//            server.requestUserList(client);
//        }
//        catch (RemoteException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    @Override public void receiveMessage(Message message)
//    {
//        System.out.println("got a message" + message);
//        property.firePropertyChange("broadcast", null, message);
//    }
//    //Login
//    public void login(String username, String password) { System.out.println("Logging in..."); }
//
//    @Override public User getUser()
//    {
//        return user;
//    }
//
//    @Override public void setUser(User user)
//    {
//        System.out.println("new user profile " + user);
//        this.user = user;
//        updateUser();
//    }
//
//    @Override public void updateUser()
//    {
//        try
//        {
//            server.updateUser(user, client);
//        }
//        catch (RemoteException e)
//        {
//            System.out.println("user profile update failed");
//        }
//    }
}
