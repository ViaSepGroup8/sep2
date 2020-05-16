package database;

import logger.Logger;
import model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class FakeDatabase implements Database
{
  ArrayList<Item> items;
  ArrayList<User> users;
  ArrayList<Order> orders;
  ArrayList<Job> jobs;

  public FakeDatabase()
  {
    //initialize list
    items = new ArrayList<Item>();
    users = new ArrayList<User>();
    orders = new ArrayList<Order>();
    jobs = new ArrayList<Job>();

    String chars = "BCDEFGHI";
    Random rnd = new Random();
    //load some fake data
    Logger.getInstance().addLog("db>> loading fake data");
    String StringOfItems = "error";
    try
    {
      StringOfItems = Files.readString(Paths.get("Server/src/database/fake-data.txt"));
    }
    catch (IOException e)
    {
    }
    String[] split = StringOfItems.replaceAll("\\s+", "").split(",");
    for (int i = 0; i < split.length; i++)
    {
      String item = split[i];
      items.add(new Item(i, split[i], 0, new Location("40" + chars.charAt(rnd.nextInt(chars.length())), 1 + rnd.nextInt(50), 1 + rnd.nextInt(3)), rnd.nextInt(100)));
    }

    //System.out.println(items);

    //add some fake orders
    for (int i = 0; i < 5; i++)
    {
      orders.add(new Order(new User("rema", "Rema 1000", UserType.CUSTOMER), OrderStatus.READY_FOR_PICKUP, chars.charAt(rnd.nextInt(chars.length())) + "X" + chars.charAt(rnd.nextInt(chars.length())) + rnd.nextInt(1000), "Gate " + chars.charAt(rnd.nextInt(chars.length())), "1337 Street, Half-Life 1"));
      //System.out.println("db>> " + orders.get(i));
    }


  }

  @Override public ArrayList<Item> getAllWarehouseItems()
  {
    return items;
  }

  @Override public User getUser(String username, String password)
  {
    UserType u;
    switch (username)
    {
      case "":
        u = UserType.CUSTOMER;break;
      case "admin":
        u =  UserType.ADMIN;break;
      case "picker":
        u =  UserType.PICKER;break;
      case "driver":
        u = UserType.DRIVER;break;
      case "customer":
        u =  UserType.CUSTOMER;break;
      default:
        u = UserType.UNKNOWN;break;
    }

    return new User("fakeuser1", "Fake User", u);
  }

  @Override public void addJob(Job job)
  {
    jobs.add(job);
  }

  @Override public Job getNewJob()
  {
    //todo the job should have should not be assigned to anybody else.

    //initialize list
    ArrayList<Item> Jobitems = new ArrayList<Item>();

    String chars = "BCDEFGHI";
    Random rnd = new Random();
    //load some fake data
    String StringOfItems = "error";
    try
    { StringOfItems = Files.readString(Paths.get("Server/src/database/fake-data.txt")); } catch (IOException e) { }
    String[] split = StringOfItems.replaceAll("\\s+", "").split(",");
    for (int i = 0; i < 10; i++)
    {
      String item = split[i];
      Jobitems.add(new Item(i, split[i], 1 + rnd.nextInt(10), new Location("40" + chars.charAt(rnd.nextInt(chars.length())), 1 + rnd.nextInt(50), 1 + rnd.nextInt(3)), rnd.nextInt(100)));
    }


    return new Job("Job001", "fakeOrder", Jobitems);
  }

  @Override public Job getJobById(String id) throws InvalidDatabaseRequestException
  {
    for (Job job : jobs)
    {
      if(job.getJobId().equals(id)){
        return job;
      }
    }
    throw new InvalidDatabaseRequestException("cannot find the job by id");
  }

  @Override public void addOrder(Order order) throws InvalidDatabaseRequestException
  {
    if(order.totalItemsNumber() < 1){
      throw new InvalidDatabaseRequestException("order cannot have 0 items");
    }

    String chars = "BCDEFGHI";
    Random rnd = new Random();

    order.setUniqueId(chars.charAt(rnd.nextInt(chars.length())) + "X" + chars.charAt(rnd.nextInt(chars.length())) + rnd.nextInt(1000));
    orders.add(order);
  }

  @Override public ArrayList<Order> getUserOrders(User customer)
  {
    ArrayList<Order> userOrders = new ArrayList<Order>();
    for (Order order : orders)
    {
      if(order.getCustomer().getUsername().equals(customer.getUsername())){
        userOrders.add(order);
      }
    }

    return userOrders;
  }

  @Override public ArrayList<Order> getAllOrders()
  {
    return orders;
  }

  @Override public void setOrderStatus(String orderId, OrderStatus status) throws InvalidDatabaseRequestException
  {
    for (Order order : orders)
    {
      if(order.getUniqueId().equals(orderId)){
        order.setStatus(status);
        return;
      }
    }
    throw new InvalidDatabaseRequestException("cannot find the order");
  }

  @Override public Order getNewPickupOrder(User driver) throws InvalidDatabaseRequestException
  {
    for (Order order : orders)
    {
      if(order.getStatus().equals(OrderStatus.READY_FOR_PICKUP) && order.getDriver() == null){
        order.setDriver(driver);
        return order;
      }
    }
    throw new InvalidDatabaseRequestException("no more orders");
  }
}
