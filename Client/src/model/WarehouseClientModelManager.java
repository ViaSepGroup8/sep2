package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import client.mediator.ChatClient;
import client.mediator.Client;
import server.mediator.ChatServer;
import server.model.Message;
import server.model.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class WarehouseClientModelManager implements WarehouseClientModel {
    private ChatClient client;
    private ChatServer server;
    private User user;
    private PropertyChangeSupport property;

    public ClientModelManager()
    {
        property = new PropertyChangeSupport(this);
        try
        {
            client = new Client(this);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        user = new User("New User", "unspecified", "unknown");
    }

    @Override public void connect()
    {
        try {
            //connect to the registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            server = (ChatServer) registry.lookup("server");
        } catch (Exception exception){
            System.out.println("connection failed");
            System.exit(0);
        }

        try
        {
            System.out.println(server.ping());
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        registerClient();
    }

    @Override public void registerClient()
    {
        try
        {
            server.registerClient(client, user);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override public void broadCast(Message message)
    {
        try
        {
            server.broadCast(message, client);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override public void requestUserList()
    {
        try
        {
            server.requestUserList(client);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override public void receiveMessage(Message message)
    {
        System.out.println("got a message" + message);
        property.firePropertyChange("broadcast", null, message);
    }

<<<<<<< HEAD
    @Override public void receiveUserList(ArrayList<User> users)
    {
        System.out.println(users);
        property.firePropertyChange("userlist", null, users);
=======
    //Login
    public void login(String username, String password) { System.out.println("Logging in..."); }

    @Override
    public void answer(int profession) {
        switch (profession) {
            case 0:
                System.out.println("Impossible to login, try again!");
                //No profession (it will give and error message)
                break;
            case 1:
                System.out.println("Logged in as administrator!");
                //Will open admin view
                break;
            case 2:
                System.out.println("Logged in as customer!");
                //Will open customer view
                break;
            case 3:
                System.out.println("Logged in as picker!");
                //Will open picker view
                break;
            case 4:
                System.out.println("Logged in as driver!");
                //Will open driver view
                break;
        }
>>>>>>> 8b1cb92f5d9a9108d113139b41b49733ea99d802
    }

    @Override public User getUser()
    {
        return user;
    }

    @Override public void setUser(User user)
    {
        System.out.println("new user profile " + user);
        this.user = user;
        updateUser();
    }

    @Override public void updateUser()
    {
        try
        {
            server.updateUser(user, client);
        }
        catch (RemoteException e)
        {
            System.out.println("user profile update failed");
        }
    }

    @Override public void addListener(PropertyChangeListener listener)
    {
        property.addPropertyChangeListener(listener);
    }

    @Override public void removeListener(PropertyChangeListener listener)
    {
        property.removePropertyChangeListener(listener);
    }
}
