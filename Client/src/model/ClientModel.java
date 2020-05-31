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
  User getUser();

  //Administrator

  void addAccount();
  void deleteAccount();
  void deleteOrder();

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
