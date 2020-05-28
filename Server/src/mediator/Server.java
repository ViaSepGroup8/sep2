package mediator;

import database.Database;
import database.Database_Implementation;
import database.InvalidDatabaseRequestException;
import logger.Logger;
import model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements WarehouseServer
{
  ServerModel model;
  Database database;
  //  ArrayList<WarehouseClient> clients;
  //  ArrayList<User> users;

  public Server(ServerModel model) throws RemoteException
  {
    this.database = new Database_Implementation();
    this.model = model;
    //    clients = new ArrayList<WarehouseClient>();
    //    users = new ArrayList<User>();
    UnicastRemoteObject.exportObject(this, 0);
  }

  @Override public String ping() throws RemoteException
  {
    return "pong";
  }

  @Override public User login(String username, String password) throws RemoteException
  {
    //return database.getUserType(username, password);
    Logger.getInstance().addLog("user " + username + " has logged in");
    return database.getUser(username, password);
  }

  @Override public ArrayList<Item> getAllWarehouseItems()
  {
    return database.getAllWarehouseProducts();
  }

  @Override public Job getNewJob(User user) throws RemoteException
  {
    return database.getNewJob(user);
  }

  @Override public void completeJob(User user, Job job) throws RemoteException
  {
    database.completeJob(user, job);
    try
    {
      database.setOrderStatus(job.getOrderId(), OrderStatus.READY_FOR_PICKUP);
    }
    catch (InvalidDatabaseRequestException e)
    {
      Logger.getInstance().addLog("cannot find order " + job.getOrderId());
    }
  }

  @Override public void createNewOrder(Order order) throws RemoteException
  {
    //add the order to the database and get back the id it generates
    try { order.setUniqueId(database.addOrder(order)); }
    catch (InvalidDatabaseRequestException e) { e.printStackTrace(); return;}

    p("Creating jobs for order number " + order.getUniqueId() +" with total of " + order.totalItemsNumber() + " items.");
    new Thread(() -> {
      //sleepSeconds(2);

      int jobCount = 1;
      int maxQuantity = 200;
      int jobItemCount = 0;
      String jobId = database.createJob(order.getUniqueId());                                                                         p("Created new job with id " + jobId + ".");

      for (int i = 0; i < order.getItems().size(); i++) {
        if(jobItemCount == maxQuantity){
          jobId = database.createJob(order.getUniqueId());
          jobCount++;
          jobItemCount = 0;                                                                                                            p("Job has max possible (" + maxQuantity + ") items new one created for the next items.");
        }

        Item item = order.getItems().get(i);
        int itemQuantity = item.getQuantity();

        while(jobItemCount + itemQuantity > maxQuantity){
          int maxPossibleCount = maxQuantity-jobItemCount;
          itemQuantity-=maxPossibleCount;
          database.orderAddItem(item.getUniqueId(), maxPossibleCount, order.getUniqueId(), jobId);
          jobId = database.createJob(order.getUniqueId());
          jobCount++;
          jobItemCount = 0;                                                                                                           p("Added " + maxPossibleCount + " of " + item.getName() + " there are " + itemQuantity + " left for the next job.");
        }

        jobItemCount+=itemQuantity;                                                                                                   p("Added " + itemQuantity + " of " + item.getName() + ".");
        database.orderAddItem(item.getUniqueId(), itemQuantity, order.getUniqueId(), jobId);
      }                                                                                                                               p("There are no more items in the order.  In total " + jobCount + " were created.");

      //change the status of the order to job divided
      try { database.setOrderStatus(order.getUniqueId(), OrderStatus.JOBS_DIVIDED); }
      catch (Exception e) { Logger.getInstance().addLog("database cannot find order id" + order.getUniqueId()); }

      //this code block is run in a separate thread to ensure that it doesn't block other server requests
    }).start();
  }

  @Override public ArrayList<Order> getUserOrders(User customer) throws RemoteException
  {
    return database.getOrdersByUser(customer);
  }

  @Override public ArrayList<Order> getOrders() throws RemoteException
  {
    return database.getAllOrders();
  }

  @Override public Order getNewPickupOrder(User user) throws RemoteException
  {
    Order order = null;
    try
    {
      order = database.getNewDriverOrder(user);
      Logger.getInstance().addLog("driver " + user.getFullName() + " has been assigned to deliver order " + order.getUniqueId());
    }
    catch (InvalidDatabaseRequestException e)
    {
      Logger.getInstance().addLog("no available orders for drivers");
    }
    finally
    {
      return order;
    }
  }

  @Override public void deliver(Order order, User user) throws RemoteException
  {
    try
    {
      Logger.getInstance().addLog("driver " + user.getFullName() + " has delivered order " + order.getUniqueId());
      database.setOrderStatus(order.getUniqueId(), OrderStatus.DELIVERED);
    }
    catch (InvalidDatabaseRequestException e)
    {
      Logger.getInstance().addLog("requested order cannot be changed to deliver, order not found");
    }

    ArrayList<Order> list = database.getAllOrders();
    for (Order o : list)
    {
      System.out.println("# " + o);
    }
    System.out.println("");
  }

  @Override
  public void addUser(String username, String fullName, UserType userType, String password) {
    database.addUser(username, fullName, userType, password);
  }

  @Override
  public void removeUser(String username) {
    database.removeUser(username);
  }

  private void sleepSeconds(int seconds){
    try
    {
      Thread.sleep(seconds*1000);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }

  private void p(Object o){ Logger.getInstance().addLog(">>>>> JOB CREATOR >>>>> " + o); }

}

  //  @Override public void registerClient(WarehouseClient client, User user) throws RemoteException
  //  {
  //    clients.add(client);
  //    users.add(user);
  //    Logger.getInstance().addLog("new client connected " + client + "\n" + user);
  //    updateAllClientsUserList();
  //  }
  //
  //  @Override public void broadCast(Message message, WarehouseClient sender) throws RemoteException
  //  {
  //    Logger.getInstance().addLog("broadcasting message" + message);
  //    for (WarehouseClient client : clients)
  //    {
  ////      if(client.equals(sender))
  ////        continue;
  //
  //      client.receiveMessage(message);
  //    }
  //  }
  //
  //  @Override public void requestUserList(WarehouseClient client) throws RemoteException
  //  {
  //    Logger.getInstance().addLog("userlist request");
  //    client.receiveUserList(users);
  //  }
  //
  //  @Override public String ping() throws RemoteException
  //  {
  //    return "pong";
  //  }
  //
  //  @Override public void updateUser(User user, WarehouseClient client) throws RemoteException
  //  {
  //    users.set(clients.indexOf(client), user);
  //    updateAllClientsUserList();
  //  }
  //
  //  public void updateAllClientsUserList() throws RemoteException
  //  {
  //    for (WarehouseClient warehouseClient : clients)
  //    {
  //      warehouseClient.receiveUserList(users);
  //    }
  //  }
