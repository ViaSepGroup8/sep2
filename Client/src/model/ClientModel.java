package model;


import utility.ObserverSubject;
import java.util.ArrayList;

public interface ClientModel extends ObserverSubject
{
  void logOut();
  void userError(String message);
  void debugLog(String message);
  void fatalError(String message);

  //Login
  void login(String username, String password);
  UserAccount getUserAccount();

  //Administrator
  ArrayList<UserAccount> getAllUsers();
  ArrayList<Order> getAllOrders();
  void addProduct(String description, double price);
  void removeProduct(int id);

  void addAccount(String username, String fullName, UserType userType, String password);
  void deleteAccount(String username);
  void deleteOrder(int orderId);

  //Costumer
  ArrayList<Item> getAllWarehouseItems();
  void createCustomerNewOrder(Order order);
  ArrayList<Order> getOrderList();

  //Driver
  Order getNewPickupOrder();
  void deliver(Order order);

  //Picker
  Job getNewJob();
  void completeJob(Job job);

  //Server Asynchronous updates
  void receiveOrderUpdate(Order order);
}
