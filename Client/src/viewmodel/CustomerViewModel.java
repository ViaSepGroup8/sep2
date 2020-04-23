package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ClientModel;
import model.Item;

public class CustomerViewModel {
    private ClientModel model;
    private ObservableList<Item> emptyList;

    public CustomerViewModel(ClientModel model) {
        this.model = model;
        emptyList = loadList();
    }

    public void logOut ()
    {
        model.logOut();
    }

    private ObservableList<Item> loadList()
    {
        ObservableList<Item> observableList = FXCollections.observableArrayList();
        for (Item item: model.getAllWarehouseItems())
        {
            observableList.add(item);
        }
        return observableList;
    }

    public ObservableList<Item> getEmptyList()
    {
        return emptyList;
    }
}
