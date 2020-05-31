package model;

import logger.Logger;
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
    //setup observable subject
    property = new PropertyChangeSupport(this);

    // Create server object for clients to connect to
    try {server = new Server(this); } catch (RemoteException e) { e.printStackTrace(); Logger.getInstance().addLog("initialization failed");}
  }

  @Override
  public void addUser(String username, String fullName, UserType userType, String password) {
    try {
      server.addUser(username, fullName, userType, password);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void removeUser(String username) {
    try {
      server.removeUser(username);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override public void start()
  {
    try
    {
      Registry registry = LocateRegistry.createRegistry(1099);
      registry.bind("server", server);
      Logger.getInstance().addLog("server started..");
    }
    catch (Exception e){
      e.printStackTrace();
      Logger.getInstance().addLog("server start failed");
    }
  }

  @Override public void stop()
  {
    //todo add method to stop the server
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
