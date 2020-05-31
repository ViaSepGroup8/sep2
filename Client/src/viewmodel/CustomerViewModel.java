package viewmodel;

import javafx.application.Platform;
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
    private SimpleStringProperty userError;
    private SimpleStringProperty username;
    private SimpleStringProperty deliveryAddress;
    ObservableList<OrderTableRowData> orderList;
    ObservableList<ItemTableRowData> productList;

    public CustomerViewModel(ClientModel model) {
        this.model = model;
        model.addListener(this);
        username = new SimpleStringProperty();
        userError = new SimpleStringProperty("");
        deliveryAddress  = new SimpleStringProperty();
        orderList = FXCollections.observableArrayList();
        productList = FXCollections.observableArrayList();
    }

    public void refreshProductList()
    {
        productList.clear();
        for (Item item: model.getAllWarehouseItems()) productList.add(new ItemTableRowData(item));
    }

    public void refreshOrderList()
    {
        orderList.clear();
        for (Order order: model.getOrderList()) orderList.add(new OrderTableRowData(order));
        username.set(model.getUser().getFullName());
    }

    public void createNewOrder()
    {
        if(deliveryAddress.get() == null || deliveryAddress.get().equals("")){
            model.userError("Cannot create order without specifying the address.");return;
        }

        ArrayList<Item> itemsSelected = new ArrayList<Item>();

        //Insert all elements from itemsSelectedRowData to itemsSelected(converting each element to Item Type)
        for (ItemTableRowData item : productList) {
            itemsSelected.add(item.toItemObject());
        }

        //Remove all not selected items by customer also remove invalid values
        ArrayList<Item> valuesToRemove = new ArrayList<>();
        for (Item item : itemsSelected) {
            if (item.getQuantity() == 0) valuesToRemove.add(item);
            if (item.getQuantity() < 0){
                model.userError("Cannot create order with negative quantity.");return;
            }
        }
        itemsSelected.removeAll(valuesToRemove);
        if(itemsSelected.size()  == 0){
            model.userError("Cannot create order with zero items.");return;
        }

        model.createCustomerNewOrder(new Order(model.getUser(), itemsSelected, deliveryAddress.getValue()));
        productList.clear();
    }

    public ObservableList<OrderTableRowData> getOrderList() { return orderList; }
    public ObservableList<ItemTableRowData> getProductList() { return productList; }
    public SimpleStringProperty getUsername() { return username; }
    public SimpleStringProperty getUserError() { return userError; }
    public SimpleStringProperty getDeliveryAddress() { return deliveryAddress; }
    public void logOut () { model.logOut(); }

    @Override public void propertyChange(PropertyChangeEvent event)
    {
        if(event.getPropertyName().equals("orderUpdate")){
            Order order = (Order) event.getNewValue();
            for (OrderTableRowData rowData : orderList)
            {
                if(rowData.orderIdProperty().get().equals(order.getUniqueId())){
                    System.out.println("updated 1 order");
                    rowData.set(order);
                    return; }
            }
            System.out.println("added 1 order");
            orderList.add(new OrderTableRowData(order));
        }

        else if(event.getPropertyName().equals("userError")){
            userError.set((String) event.getNewValue());
            new Thread(() -> {
                try { Thread.sleep(3500); Platform.runLater(() -> { userError.set(""); }); }
                catch (InterruptedException e) { e.printStackTrace(); } }).start();
        }
    }
}
