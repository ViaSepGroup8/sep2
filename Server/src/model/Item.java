package model;

public class Item
{
  int uniqueId;
  String name;
  Integer quantity;
  Location location;

  public Item(int uniqueId, String name, Integer quantity, Location location)
  {
    this.uniqueId = uniqueId;
    this.name = name;
    this.quantity = quantity;
    this.location = location;
  }

  @Override public String toString()
  {
    return "Item{" + "uniqueId=" + uniqueId + ", name='" + name + '\'' + ", quantity=" + quantity + ", location=" + location + '}';
  }
}
