package view.picker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import model.Job;
import view.ViewHandler;
import viewmodel.PickerViewModel;

import java.rmi.RemoteException;

public class PickerViewController {
    public Label jobLabel;
    private ViewHandler viewHandler;
    private PickerViewModel viewModel;
    private Region root;
    private Job job;

    //Table
    @FXML public TableView<PickerTableRowData> pickerTable;
    @FXML public TableColumn<PickerTableRowData, Number> idColumn;
    @FXML public TableColumn<PickerTableRowData, String> nameColumn;
    @FXML public TableColumn<PickerTableRowData, Number> quantityColumn;
    @FXML public TableColumn<PickerTableRowData, String> locationColumn;

    public PickerViewController() {}

    public void init(ViewHandler viewHandler, PickerViewModel viewModel, Region root) throws RemoteException {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        // set cell value factory
        idColumn.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().uniqueIdProperty());
        nameColumn.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().nameProperty());
        quantityColumn.setCellValueFactory(itemTableRowDataIntegerCellDataFeatures -> itemTableRowDataIntegerCellDataFeatures.getValue().quantityProperty());
        locationColumn.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().locationProperty());

        // bind the picker list view <> viewmodel
        pickerTable.setItems(viewModel.getPickerList());
        jobLabel.textProperty().bindBidirectional(viewModel.getJobId());

//        // make table editable
//        pickerTable.setEditable(true);
//        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
//        idColumn.setCellValueFactory(orderTableRowDataNumberCellDataFeatures -> orderTableRowDataNumberCellDataFeatures.getValue().uniqueIdProperty());
//        nameColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().nameProperty());
    }

    public Region getRoot() { return root; }

    @FXML public void newOrderButtonPressed() throws RemoteException {
        viewModel.getNewOrder();
    }

    @FXML public void orderCompletedButtonPressed() throws RemoteException {
        viewModel.completeJob();
    }

    @FXML public void logOut() {
        viewModel.logOut();
        viewHandler.openView("Login");
    }
}
