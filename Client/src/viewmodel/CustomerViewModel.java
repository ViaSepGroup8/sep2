package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ClientModel;
import model.Item;
import model.Order;
import view.customer.ItemTableRowData;
import view.customer.OrderTableRowData;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class CustomerViewModel implements PropertyChangeListener
{
    private ClientModel model;
    private SimpleStringProperty username;
    ObservableList<OrderTableRowData> list;

    public CustomerViewModel(ClientModel model) {
        this.model = model;
        username = new SimpleStringProperty();
        list = FXCollections.observableArrayList();

        model.addListener(this);
    }

    public void refresh()
    {
        list.clear();
        for (Order order: model.getOrderList()) list.add(new OrderTableRowData(order));
        username.setValue(model.getUser().getFullName());
    }

    public ObservableList<ItemTableRowData> getAllWarehouseItems() {
        ObservableList<ItemTableRowData> AllwarehouseItems = FXCollections.observableArrayList();
        for (Item item: model.getAllWarehouseItems()) AllwarehouseItems.add(new ItemTableRowData(item));
        return AllwarehouseItems;
    }

    public void createCustomerNewOrder(ArrayList<Item> itemsSelected) {
        model.createCustomerNewOrder(new Order(model.getUser(), itemsSelected));
    }

    public ObservableList<OrderTableRowData> getOrdersList() { return list; }
    public SimpleStringProperty getUsername() { return username; }
    public void logOut () { model.logOut(); }

    @Override public void propertyChange(PropertyChangeEvent event)
    {
        if(event.getPropertyName().equals("orderUpdate")){
            Order order = (Order) event.getNewValue();
            for (OrderTableRowData rowData : list)
            {
                if(rowData.orderIdProperty().get().equals(order.getUniqueId())){
                    System.out.println("updated 1 order");
                    rowData.set(order);
                    return; }
            }
            System.out.println("added 1 order");
            list.add(new OrderTableRowData(order));
        }
    }
}
