package mediator;
import model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface WarehouseServer extends Remote
{
  String ping() throws RemoteException;
  User login(String username, String password) throws RemoteException;
  ArrayList<Item> getAllWarehouseItems() throws RemoteException;
  Job getNewJob() throws RemoteException;
  void completeJob(String jobId) throws RemoteException;
  void createNewOrder(Order order) throws RemoteException;

  ArrayList<Order> getUserOrders(User customer) throws RemoteException;
  ArrayList<Order> getOrders() throws RemoteException;

  //Driver
  Order getNewPickupOrder(User user) throws RemoteException;
  void deliver(Order order, User user) throws RemoteException;

//  void registerClient(WarehouseClient client, User user) throws  RemoteException;
//  void broadCast(Message message, WarehouseClient sender) throws RemoteException;
//  void requestUserList(WarehouseClient client) throws  RemoteException;
//  void updateUser(User user, WarehouseClient client) throws RemoteException;
}
