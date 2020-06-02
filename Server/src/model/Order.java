package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable
{
  UserAccount customer;
  OrderStatus status;
  String uniqueId;
  ArrayList<Item> items;
  ArrayList<Job> jobs;
  String gate;
  String deliverAddress;
  UserAccount driver;

  public Order(UserAccount userAccount, ArrayList<Item> orderItems, String deliverAddress)
  {
    customer = userAccount;
    items = orderItems;
    status = OrderStatus.CREATED;
    this.deliverAddress = deliverAddress;
  }

  public Order(UserAccount customer, OrderStatus status, String uniqueId, ArrayList<Item> items, String gate, String deliverAddress)
  {
    this.customer = customer;
    this.status = status;
    this.uniqueId = uniqueId;
    this.gate = gate;
    this.deliverAddress = deliverAddress;
    this.items = items;
  }

  public Order(UserAccount customer, OrderStatus status, String uniqueId, String gate, String deliverAddress)
  {
    this.customer = customer;
    this.status = status;
    this.uniqueId = uniqueId;
    this.gate = gate;
    this.deliverAddress = deliverAddress;
  }

  public Integer totalSum(){
    if(items == null){
      return 0;
    }
    Integer sum = 0;
    for (Item item : items)
    {
      sum+=item.getPrice()*item.quantity;
    }
    return sum;
  }

  public Integer totalItemsNumber(){
    Integer count = 0;
    if(items == null){
      return 0;
    }

    for (Item item : items)
    {
      count+=item.quantity;
    }

    return count;
  }

  @Override public String toString()
  {
    return "Order{" + "customer=" + customer + ", status=" + status + ", uniqueId='" + uniqueId + '\'' + ", items=" + items + ", jobs=" + jobs
        + ", gate='" + gate + '\'' + ", deliverAddress='" + deliverAddress + '\'' + '}';
  }

  public void setCustomer(UserAccount customer)
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

  public UserAccount getCustomer()
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

  public UserAccount getDriver()
  {
    return driver;
  }

  public void setDriver(UserAccount driver)
  {
    this.driver = driver;
  }
}
