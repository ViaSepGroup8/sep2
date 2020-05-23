package database;

import model.*;
import java.util.ArrayList;

public interface Database
{
  ArrayList<Item> getAllWarehouseItems();
  User getUser(String username, String password);
  ArrayList<User> getAllUsers();
  //Jobs (for pickers)
  void addJob(Job job);
  void completeJob(User user, Job job);
  Job getNewJob();
  Job getJobById(String id) throws InvalidDatabaseRequestException;
  Job getJobByUser(User user) throws InvalidDatabaseRequestException;


  void addOrder(Order order) throws InvalidDatabaseRequestException;
  ArrayList<Order> getUserOrders(User customer);
  ArrayList<Order> getAllOrders();

  void setOrderStatus(String orderId, OrderStatus status) throws InvalidDatabaseRequestException;
  Order getNewPickupOrder(User driver) throws InvalidDatabaseRequestException;
}
