package mediator;
import model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface WarehouseServer extends Remote
{
  String ping() throws RemoteException;
  UserAccount login(String username, String password, WarehouseClient client) throws RemoteException;
  ArrayList<Item> getAllWarehouseItems() throws RemoteException;

  //Picker
  Job getNewJob(UserAccount userAccount) throws RemoteException;
  void completeJob(UserAccount userAccount, Job job) throws RemoteException;

  void createNewOrder(Order order) throws RemoteException;

  ArrayList<Order> getUserOrders(UserAccount customer) throws RemoteException;
  ArrayList<Order> getOrders() throws RemoteException;

  //Driver
  Order getNewPickupOrder(UserAccount userAccount) throws RemoteException;
  void deliver(Order order, UserAccount userAccount) throws RemoteException;

  // Admin
  //ArrayList<UserAccount> getAllUsers();
  void addUser(String username, String fullName, UserType userType, String password) throws RemoteException;
  void removeUser(String username) throws RemoteException;
  void addProduct(String description, double price) throws RemoteException;
  void removeProduct(int id) throws RemoteException;
  ArrayList<Order> getAllOrders() throws RemoteException;
  ArrayList<UserAccount> getAllUsers() throws RemoteException;

//  void registerClient(WarehouseClient client, UserAccount user) throws  RemoteException;
//  void broadCast(Message message, WarehouseClient sender) throws RemoteException;
//  void requestUserList(WarehouseClient client) throws  RemoteException;
//  void updateUser(UserAccount user, WarehouseClient client) throws RemoteException;
}
