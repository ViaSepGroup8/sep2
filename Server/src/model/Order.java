package model;

import java.util.ArrayList;

public class Order
{
  OrderStatus status;
  String uniqueId;
  ArrayList<Item> items;
  ArrayList<Job> jobs;
  String gate;

  public Order(String uniqueId, ArrayList<Item> items, ArrayList<Job> jobs)
  {
    this.uniqueId = uniqueId;
    this.items = items;
    this.jobs = jobs;

    status = OrderStatus.CREATED;
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

  public void setStatus(OrderStatus status)
  {
    this.status = status;
  }

  public String getGate()
  {
    return gate;
  }

  public void setGate(String gate)
  {
    this.gate = gate;
  }
}
