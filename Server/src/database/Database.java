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
  ArrayList<Item> getAllWarehouseProducts();                   // should return list of every item in the warehouse with quantity set to 0

  // login and user accounts
  User getUser(String username, String password);
  ArrayList<User> getAllUsers();
  void addUser(String username, String fullName, UserType userType, String password);
  void removeUser(String username);

  // jobs for pickets
  String createJob(String order_id);                        // only used by the server to generate new jobs when a new order is received, returns job_id
  Job getNewJob(User user);                                          // checks if there are unassigned jobs and returns one of them, if none return null
  void completeJob(User user, Job job);

  // orders
  String addOrder(Order order) throws InvalidDatabaseRequestException;  //returns id of the order
  void removeOrder(String order_id) throws InvalidDatabaseRequestException;
  void orderAddItem(int item_id, int quantity, String order_id, String job_id);
  ArrayList<Order> getOrdersByUser(User customer);
  Order getOrderByOrderId(String order_id) throws InvalidDatabaseRequestException;
  ArrayList<Order> getAllOrders();
  void setOrderStatus(String orderId, OrderStatus status) throws InvalidDatabaseRequestException;

  // driver
  Order getNewDriverOrder(User driver) throws InvalidDatabaseRequestException;
}
