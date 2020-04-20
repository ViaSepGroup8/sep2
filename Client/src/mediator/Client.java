package mediator;

import client.model.ClientModel;
import server.model.Message;
import server.model.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Client implements ChatClient
{
  private ClientModel model;

  public Client(ClientModel model) throws RemoteException
  {
    UnicastRemoteObject.exportObject(this, 0);
    this.model = model;
  }

  @Override public void receiveMessage(Message message) throws RemoteException
  {
    model.receiveMessage(message);
  }

  @Override public void receiveUserList(ArrayList<User> users) throws RemoteException
  {
    model.receiveUserList(users);
  }
}
