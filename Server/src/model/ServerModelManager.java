package model;

import mediator.Server;
import mediator.WarehouseServer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerModelManager implements ServerModel
{
  private WarehouseServer server;
  private PropertyChangeSupport property;

  public ServerModelManager()
  {
<<<<<<< HEAD
=======
    // Create server object for clients to connect to
    try {server = new Server(this); }
    catch (RemoteException e)
    {
      System.out.println(e.getMessage());
      log("initialization failed");
    }

>>>>>>> a73b8390e700567a7a505f9642867491666fae32
    //setup observable subject
    property = new PropertyChangeSupport(this);

    // Create server object for clients to connect to
    try {server = new Server(this); } catch (RemoteException e) { log("initialization failed");}
  }

  @Override public void start()
  {
    try
    {
      Registry registry = LocateRegistry.createRegistry(1099);
      registry.bind("server", server);
      log("server started..");
    }
    catch (Exception e){
      log("server start failed");
    }
  }

  @Override public void stop()
  {
    //todo add method to stop the server
  }

  @Override public void log(String line)
  {
    property.firePropertyChange("log", null, line);
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
