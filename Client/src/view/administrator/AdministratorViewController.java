package view.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import model.Item;
import view.ViewHandler;
import view.customer.ItemTableRowData;
import viewmodel.AdministratorViewModel;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class AdministratorViewController {
    private ViewHandler viewHandler;
    private AdministratorViewModel viewModel;
    private Region root;

    public AdministratorViewController() {}

    public void init(ViewHandler viewHandler, AdministratorViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    public Region getRoot() { return root; }

    @FXML
    public void logOutButtonClicked () {
        viewModel.logOut();
    }

    // Customer
    /*
    @FXML
    public void newOrderButtonClicked () {
        itemsTable.setItems(viewModel.getAllWarehouseItems());
    }

    @FXML
    public void acceptOrderButtonClicked () {
        ArrayList<ItemTableRowData> itemsSelectedRowData = new ArrayList<ItemTableRowData>();
        ArrayList<Item> itemsSelected = new ArrayList<Item>();
        itemsSelectedRowData.addAll(itemsTable.getItems());

        //Insert all elements from itemsSelectedRowData to itemsSelected(converting each element to Item Type)
        for (ItemTableRowData item : itemsSelectedRowData) {
            itemsSelected.add(item.toItemObject());
        }

        //Remove all not selected items by customer
        ArrayList<Item> valuesToRemove = new ArrayList<>();
        for (Item item : itemsSelected) {
            if (item.getQuantity() == 0){
                //valuesToRemove.remove(item); This Fucking error took me 10 minutes of my life
                valuesToRemove.add(item);
            }
        }

        itemsSelected.removeAll(valuesToRemove);

        //Pass info to viewModel
        viewModel.createCustomerNewOrder(itemsSelected);

        //refresh the list of orders
        ordersTable.setItems(viewModel.getOrdersList());
    }

    @FXML
    public void refreshButtonClicked() {
        ordersTable.setItems(viewModel.getOrdersList());
    }

    // Driver

    @FXML
    public void askButtonClicked() {
        viewModel.ask();
        if(!orderId.textProperty().get().equals("-")) {
            deliveryButton.setDisable(false);
        }
    }

    @FXML
    public void deliveredButtonClicked() {
        viewModel.deliver();
        deliveryButton.setDisable(true);
    }

    // Picker

    @FXML
    public void orderCompletedButtonClicked() throws RemoteException {
        if (job != null) {
            String jobId = this.job.getJobId();
            viewModel.completeJob(jobId);
        }
        else { System.out.println("Job is null"); }
    }
    */
}
