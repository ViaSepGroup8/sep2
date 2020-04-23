package model;

import java.util.ArrayList;

public class Job
{
  String jobId;
  String orderId;
  ArrayList<Item> items;
  boolean complete;

  public Job(String jobId, String orderId, ArrayList<Item> items)
  {
    this.jobId = jobId;
    this.orderId = orderId;
    this.items = items;

    complete = false;
  }

  public String getJobId()
  {
    return jobId;
  }

  public ArrayList<Item> getItems()
  {
    return items;
  }

  public String getOrderId()
  {
    return orderId;
  }

  public boolean isComplete()
  {
    return complete;
  }

  public void setComplete(boolean complete)
  {
    this.complete = complete;
  }
}
