package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable
{
  User customer;
  OrderStatus status;
  String uniqueId;
  ArrayList<Item> items;
  ArrayList<Job> jobs;
  String gate;
  String deliverAddress;

  public Order(User user, ArrayList<Item> orderItems)
  {
    customer = user;
    items = orderItems;
    status = OrderStatus.CREATED;
    deliverAddress = "qwertyvej 123. WASD, WWW";
  }

  public Order(User customer, OrderStatus status, String uniqueId, String gate, String deliverAddress)
  {
    this.customer = customer;
    this.status = status;
    this.uniqueId = uniqueId;
    this.gate = gate;
    this.deliverAddress = deliverAddress;
  }

  public void setCustomer(User customer)
  {
    this.customer = customer;
  }

  public void setStatus(OrderStatus status)
  {
    this.status = status;
  }

  public void setUniqueId(String uniqueId)
  {
    this.uniqueId = uniqueId;
  }

  public void setItems(ArrayList<Item> items)
  {
    this.items = items;
  }

  public void setJobs(ArrayList<Job> jobs)
  {
    this.jobs = jobs;
  }

  public void setGate(String gate)
  {
    this.gate = gate;
  }

  public User getCustomer()
  {
    return customer;
  }

  public OrderStatus getStatus()
  {
    return status;
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

  public String getGate()
  {
    return gate;
  }

  public String getDeliverAddress()
  {
    return deliverAddress;
  }

  public void setDeliverAddress(String deliverAddress)
  {
    this.deliverAddress = deliverAddress;
  }
}
