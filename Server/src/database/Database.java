package database;

import model.*;
import java.util.ArrayList;

public interface Database
{
  // database Operations
  void setupStructure();
  void loadSampleData();
  void deleteAllData();
  void connect();
  void close();
                                                            // NOTES:
  // warehouse items
  ArrayList<Item> getAllWarehouseItems();                   // should return list of every item in the warehouse with quantity set to 0

  // login and user accounts
  User getUser(String username, String password);
  ArrayList<User> getAllUsers();
  void addUser(String username, String fullName, UserType userType, String password);
  void removeUser(String username);

  // jobs for pickets
  String createJob(String order_id);                           // only used by the server to generate new jobs when a new order is received, returns job_id
  void jobAddItem(String item_id);
  void completeJob(User user, Job job);
  Job getNewJob();                                          // checks if there are unassigned jobs and returns one of them, if none return null
  Job getJobByUser(User user) throws InvalidDatabaseRequestException;   // used to check if the worker is current assigned a job

  // orders
  void remove(String order_id) throws InvalidDatabaseRequestException;
  void addOrder(Order order) throws InvalidDatabaseRequestException;
  ArrayList<Order> getUserOrders(User customer);
  ArrayList<Order> getAllOrders();
  void setOrderStatus(String orderId, OrderStatus status) throws InvalidDatabaseRequestException;

  // driver
  Order getNewPickupOrder(User driver) throws InvalidDatabaseRequestException;
}
