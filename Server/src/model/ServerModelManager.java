package model;

import server.mediator.ChatServer;
import server.mediator.Server;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerModelManager implements ServerModel
{
  private ChatServer server;
  private PropertyChangeSupport property;

  public ServerModelManager()
  {
    try { server = new Server(this); } catch (RemoteException e) { log("initialization failed");}
    property = new PropertyChangeSupport(this);
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
