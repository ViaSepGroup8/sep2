package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ClientModel;
import model.Item;
import view.customer.ItemTableRowData;

public class CustomerViewModel {
    private ClientModel model;

    public CustomerViewModel(ClientModel model) {
        this.model = model;
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
}
