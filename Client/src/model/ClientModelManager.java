package model;

import mediator.Client;
import mediator.WarehouseClient;
import mediator.WarehouseServer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class ClientModelManager implements ClientModel
{
    private WarehouseClient client;
    private WarehouseServer server;
    private PropertyChangeSupport property;
    //private Order order;
    private User user;

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
            fatalError("connection failed");
        }

        //test the connection
        //test
        try
        {
            if(server.ping().equals("pong")){
                debugLog("connected to server");
            }
            else{
                debugLog("wrong response from server");
            }
        }
        catch (RemoteException e)
        {
            fatalError("server does not respond");
        }

        //register client

    }

    @Override public void logOut()
    {
        debugLog("user logout");
        user = null;
    }

    @Override public void userError(String message)
    {
        property.firePropertyChange("userError", null, message);
        debugLog("user error:" + message);
    }

    @Override public void debugLog(String message)
    {
        System.out.println("client>> " + message);
    }

    @Override public void fatalError(String message)
    {
        System.out.println("client fatal error>> " + message);
        System.exit(0);
    }

    @Override public void login(String username, String password)
    {
        try
        {
            this.user = server.login(username, password, client);
            debugLog(user.toString());
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            fatalError("cannot login");
        }

    }

    @Override public User getUser()
    {
        return user;
    }

    @Override public ArrayList<Item> getAllWarehouseItems()
    {
        try
        {
            return server.getAllWarehouseItems();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createCustomerNewOrder(Order order){
        // Pass order to the client that connects with the server.
        try {
            server.createNewOrder(order);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        /*
        * If we have 2 clients(ClientModel and WarehouseClient), isn't WarehouseClient to make the connections with the
        * server and where everything related to the connections is. And then the ClientModel that connects with the
        * viewModel and is unrelated to the RMI? Because we have do the registry and get the server here, but we don't
        * use the Client class for anything
        * */
    }

    @Override
    public ArrayList<Order> getOrderList() {
        ArrayList<Order> ordersInServer = new ArrayList<>();
        try {
            ordersInServer = server.getUserOrders(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ordersInServer;
    }

    @Override public Job getNewJob(){
        try
        {
            return server.getNewJob(this.user);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            fatalError("Cannot get new job");
        }
        return null;
    }

    @Override public void completeJob(Job job) {
        try
        {
            server.completeJob(this.user, job);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override public Order getNewPickupOrder()
    {
        try
        {
            return server.getNewPickupOrder(this.user);
        }
        catch (RemoteException e)
        {
            debugLog("error");
            e.printStackTrace();
        }
        return null;
    }

    @Override public void deliver(Order order)
    {
        try
        {
            server.deliver(order, this.user);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            fatalError("server does not respond to order deliver");
        }
    }

    @Override public void addListener(PropertyChangeListener listener)
    {
        property.addPropertyChangeListener(listener);
    }

    @Override public void removeListener(PropertyChangeListener listener)
    {
        property.addPropertyChangeListener(listener);
    }

    @Override public void receiveOrderUpdate(Order order)
    {
        System.out.println("!@# " + order.toString());
        property.firePropertyChange("orderUpdate", null, order);
    }

    @Override public void addAccount() { }
    @Override public void deleteAccount() {}
    @Override public void deleteOrder() {}
}
