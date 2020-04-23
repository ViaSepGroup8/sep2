package model;

import java.util.ArrayList;

public class Job
{
  String jobId;
  ArrayList<Item> items;

  public Job(String jobId, ArrayList<Item> items)
  {
    this.jobId = jobId;
    this.items = items;
  }

  public String getJobId()
  {
    return jobId;
  }

  public ArrayList<Item> getItems()
  {
    return items;
  }
}
