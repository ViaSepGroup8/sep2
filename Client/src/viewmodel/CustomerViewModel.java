package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ClientModel;
import model.Item;
import model.Job;
import model.Order;
import view.customer.ItemTableRowData;

import java.util.ArrayList;

public class CustomerViewModel {
    private ClientModel model;
    //private ArrayList<Item> itemsSelected;

    public CustomerViewModel(ClientModel model) {
        this.model = model;
        //this.itemsSelected = new ArrayList<>();
    }

    public void logOut ()
    {
        model.logOut();
    }

    public ObservableList<ItemTableRowData> getAllWarehouseItems()
    {
        ObservableList<ItemTableRowData> AllwarehouseItems = FXCollections.observableArrayList();
        for (Item item: model.getAllWarehouseItems())
        {
            AllwarehouseItems.add(new ItemTableRowData(item));
        }
        return AllwarehouseItems;
    }


    //Creates order from list of items selected
    public void createCustomerNewOrder(ArrayList<Item> itemsSelected) {
        //this.itemsSelected = itemsSelected;
        //return new Order(/*String uniqueId*/null, itemsSelected, /*ArrayList< Job > jobs*/null);

        //uniqueID of the list of items selected??
        this.model.createCustomerNewOrder(new Order(/*String uniqueId*/null, itemsSelected, /*ArrayList< Job > jobs*/null));
    }
}
