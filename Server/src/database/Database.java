package database;

import model.*;

import java.util.ArrayList;

public interface Database
{
  ArrayList<Item> getAllWarehouseItems();
  User getUser(String username, String password);
  UserType getUserType(String username, String password);
  Job getNewJob();
  void addOrder(Order order);
  ArrayList<Order> getOrders();
}
