package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TablePosition;
import model.*;
import view.administrator.UserTableRowData;
import view.customer.ItemTableRowData;
import view.customer.OrderTableRowData;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class AdministratorViewModel implements PropertyChangeListener
{
    private ClientModel model;
    private SimpleStringProperty userError;

    ObservableList<OrderTableRowData> orderList;
    ObservableList<ItemTableRowData> productList;
    ObservableList<UserTableRowData> userList;
    private SimpleStringProperty fullNameProperty;
    private SimpleStringProperty userNameProperty;
    private SimpleStringProperty passwordFieldProperty;
    private SimpleStringProperty descriptionProperty;
    private SimpleDoubleProperty priceProperty;

    public AdministratorViewModel(ClientModel model) {
        this.model = model;
        model.addListener(this);

        userError = new SimpleStringProperty("");

        fullNameProperty = new SimpleStringProperty("");
        userNameProperty = new SimpleStringProperty("");
        passwordFieldProperty = new SimpleStringProperty("");

        descriptionProperty = new SimpleStringProperty("");
        priceProperty = new SimpleDoubleProperty(0);

        orderList = FXCollections.observableArrayList();
        productList = FXCollections.observableArrayList();
        userList = FXCollections.observableArrayList();
    }

    public void refreshProductList()
    {
        productList.clear();
        for (Item item: model.getAllWarehouseItems()) productList.add(new ItemTableRowData(item));
    }

    public void refreshOrderList()
    {
        orderList.clear();
        for (Order order: model.getAllOrders()) orderList.add(new OrderTableRowData(order));
    }

    public void refreshUserList()
    {
        userList.clear();
        for (User user: model.getAllUsers()){
            userList.add(new UserTableRowData(user));
        }
    }

    public void createNewOrder()
    {

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

        model.createCustomerNewOrder(new Order(model.getUser(), itemsSelected, ""));
        productList.clear();
    }

    public ObservableList<OrderTableRowData> getOrderList() { return orderList; }
    public ObservableList<ItemTableRowData> getProductList() { return productList; }
    public SimpleStringProperty getUserError() { return userError; }
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

    public ObservableList<UserTableRowData> getUserList()
    {
        return userList;
    }


    public void addAccount(UserType userType)
    {
        model.addAccount(userNameProperty.getValue(), fullNameProperty.getValue(), userType, passwordFieldProperty.getValue());
        refreshUserList();
    }

    public void deleteAccount(UserTableRowData row)
    {
        model.deleteAccount(row.usernameProperty().getValue());
        refreshUserList();
    }

    public void addProduct()
    {
        System.out.println("price" + priceProperty.get());
        model.addProduct(descriptionProperty.getValue(), priceProperty.doubleValue());
        refreshProductList();
    }

    public void removeProduct(ItemTableRowData row)
    {
        model.removeProduct(row.getUniqueId());
        refreshProductList();
    }

    public void deleteOrder()
    {
    }

    public SimpleStringProperty getUserNameProperty() { return userNameProperty; }
    public SimpleStringProperty getFullNameProperty() { return fullNameProperty; }
    public SimpleStringProperty getPasswordFieldProperty() { return passwordFieldProperty; }

    public SimpleStringProperty getDescriptionProperty()
    {
        return descriptionProperty;
    }

    public SimpleDoubleProperty getPriceProperty()
    {
        return priceProperty;
    }
}
