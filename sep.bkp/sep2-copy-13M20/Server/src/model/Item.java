package model;

import java.io.Serializable;

public class Item implements Serializable
{
  Integer uniqueId;
  String name;
  Integer quantity;
  Location location;
  Integer price;

  public Item(Integer uniqueId, String name, Integer quantity, Location location, Integer price)
  {
    this.uniqueId = uniqueId;
    this.name = name;
    this.quantity = quantity;
    this.location = location;
    this.price = price;
  }

  public Integer getUniqueId()
  {
    return uniqueId;
  }

  public String getName()
  {
    return name;
  }

  public Integer getQuantity()
  {
    return quantity;
  }

  public Location getLocation()
  {
    return location;
  }

  public Integer getPrice()
  {
    return price;
  }

  @Override public String toString()
  {
    return "Item{" + "uniqueId=" + uniqueId + ", name='" + name + '\'' + ", quantity=" + quantity + ", location=" + location + '}';
  }
}
