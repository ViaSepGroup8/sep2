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
    { StringOfItems = Files.readString(Paths.get("Server/src/database/fake-data.txt")); } catch (IOException e) { }
    String[] split = StringOfItems.replaceAll("\\s+", "").split(",");
    for (int i = 0; i < split.length; i++)
    {
      String item = split[i];
      items.add(new Item(i, split[i], -1, new Location("40" + chars.charAt(rnd.nextInt(chars.length())), 1 + rnd.nextInt(50), 1 + rnd.nextInt(3))));
    }

    System.out.println(items);
  }

  @Override public ArrayList<Item> getAllWarehouseItems()
  {
    return items;
  }

  @Override public User getUser(String username, String password)
  {
    return new User("Johnny1", "John Smith", "password");
  }

  @Override public UserType getUserType(String username, String password)
  {
    //todo implement with database
    switch (username)
    {
      case "admin":    return UserType.ADMIN;
      case "picker":   return UserType.PICKER;
      case "driver":   return UserType.DRIVER;
      case "customer": return UserType.CUSTOMER;
      default:         return UserType.UNKNOWN;
    }
  }
}
