package mediator;
import model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface WarehouseServer extends Remote
{
  String ping() throws RemoteException;
  User login(String username, String password, WarehouseClient client) throws RemoteException;
  ArrayList<Item> getAllWarehouseItems() throws RemoteException;

  //Picker
  Job getNewJob(User user) throws RemoteException;
  void completeJob(User user, Job job) throws RemoteException;

  void createNewOrder(Order order) throws RemoteException;

  ArrayList<Order> getUserOrders(User customer) throws RemoteException;
  ArrayList<Order> getOrders() throws RemoteException;

  //Driver
  Order getNewPickupOrder(User user) throws RemoteException;
  void deliver(Order order, User user) throws RemoteException;

  // Admin
  //ArrayList<User> getAllUsers();
  void addUser(String username, String fullName, UserType userType, String password) throws RemoteException;
  void removeUser(String username) throws RemoteException;
  void addProduct(String description, double price) throws RemoteException;
  void removeProduct(int id) throws RemoteException;
  ArrayList<Order> getAllOrders() throws RemoteException;
  ArrayList<User> getAllUsers() throws RemoteException;

//  void registerClient(WarehouseClient client, User user) throws  RemoteException;
//  void broadCast(Message message, WarehouseClient sender) throws RemoteException;
//  void requestUserList(WarehouseClient client) throws  RemoteException;
//  void updateUser(User user, WarehouseClient client) throws RemoteException;
}
