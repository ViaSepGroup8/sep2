package mediator;

import model.ClientModel;
import model.Order;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
public class Client implements WarehouseClient
{
  private ClientModel model;

  public Client(ClientModel model) throws RemoteException
  {
    UnicastRemoteObject.exportObject(this, 0);
    this.model = model;
  }

  @Override public void receiveOrderUpdate(Order order) throws RemoteException
  {
    model.receiveOrderUpdate(order);
  }
}
