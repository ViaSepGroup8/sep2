package view.administrator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Item;
import model.Location;

public class PickerTableRowData
{
  private SimpleIntegerProperty uniqueId;
  private SimpleStringProperty name;
  private SimpleIntegerProperty quantity;
  private Location location;
  private SimpleIntegerProperty price;

  public PickerTableRowData(Item item)
  {
    uniqueId = new SimpleIntegerProperty(item.getUniqueId()){};
    name = new SimpleStringProperty(item.getName());
    quantity = new SimpleIntegerProperty(item.getQuantity());
    location = item.getLocation();
    price = new SimpleIntegerProperty(item.getPrice());
  }

  public int getUniqueId()
  {
    return uniqueId.get();
  }

  public SimpleIntegerProperty uniqueIdProperty()
  {
    return uniqueId;
  }

  public String getName()
  {
    return name.get();
  }

  public SimpleStringProperty nameProperty()
  {
    return name;
  }

  public SimpleStringProperty locationProperty()
  {
    return new SimpleStringProperty(location.toString());
  }

  public int getQuantity()
  {
    return quantity.get();
  }

  public SimpleIntegerProperty quantityProperty() { return quantity; }

  public Location getLocation()
  {
    return location;
  }

  public int getPrice()
  {
    return price.get();
  }

  public SimpleIntegerProperty priceProperty()
  {
    return price;
  }

  public Item toItemObject(){
    //Item(Integer uniqueId, String name, Integer quantity, Location location, Integer price)
    return new Item(getUniqueId(), getName(), getQuantity(), getLocation(), getPrice());
  }
}
