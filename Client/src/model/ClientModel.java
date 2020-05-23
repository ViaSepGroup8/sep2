package model;

import utility.ObserverSubject;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
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

  //local model
  //  void setUser(User user);
  //  User getUser();
  //
  //  //server side
  //  void registerClient();
  //  void updateUser();
  //  void broadCast(Message message);
  //  void requestUserList();
  //
  //  //client side
  //  void receiveMessage(Message message);
  //  void receiveUserList(ArrayList<User> users);
  //  void answer(int profession);
}
