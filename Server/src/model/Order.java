package model;

import java.util.ArrayList;

public class Order
{
  String uniqueId;
  ArrayList<Item> items;
  ArrayList<Job> jobs;

  public Order(String uniqueId, ArrayList<Item> items, ArrayList<Job> jobs)
  {
    this.uniqueId = uniqueId;
    this.items = items;
    this.jobs = jobs;
  }

  public String getUniqueId()
  {
    return uniqueId;
  }

  public ArrayList<Item> getItems()
  {
    return items;
  }

  public ArrayList<Job> getJobs()
  {
    return jobs;
  }
}
