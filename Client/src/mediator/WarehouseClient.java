package mediator;

import model.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WarehouseClient extends Remote
{
  void receiveOrderUpdate(Order order) throws RemoteException;
}