package database;

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
    System.out.println("db>> loading fake data");
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

    return new User("Jon Snow", "Jon Snow", u);
  }

  @Override public UserType getUserType(String username, String password)
  {
    //todo implement with database
    switch (username)
    {
      case "admin":
        return UserType.ADMIN;
      case "picker":
        return UserType.PICKER;
      case "driver":
        return UserType.DRIVER;
      case "customer":
        return UserType.CUSTOMER;
      default:
        return UserType.UNKNOWN;
    }
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

  @Override public void addOrder(Order order)
  {
    orders.add(order);
  }

  @Override public ArrayList<Order> getOrders()
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

  @Override public Order getNewPickupOrder()
  {
    //todo find order that doesnt have any driver assigned.
    return new Order(new User("rema", "Rema 1000", UserType.CUSTOMER), OrderStatus.READY_FOR_PICKUP,"XVT1338", "Gate A", "1337 Street, Half-Life 1");
  }
}
