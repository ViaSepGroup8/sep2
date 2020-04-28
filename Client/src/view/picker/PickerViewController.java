package view.picker;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import model.Job;
import model.Location;
import view.ViewHandler;
import viewmodel.PickerViewModel;

import java.rmi.RemoteException;

public class PickerViewController {
    private ViewHandler viewHandler;
    private PickerViewModel viewModel;
    private Region root;
    private Job job;

    //Table
    @FXML public TableView<ItemTableRowData> pickerTable;
    @FXML public TableColumn<ItemTableRowData, String> nameColumn;
    @FXML public TableColumn<ItemTableRowData, Number> quantityColumn;
    @FXML public TableColumn<ItemTableRowData, String> locationColumn;

    public PickerViewController() {}

    public void init(ViewHandler viewHandler, PickerViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        try { this.getNewJob(); } catch (Exception e) {}
    }

    public Region getRoot() { return root; }

    public void getNewJob() throws RemoteException {
        job = viewModel.getNewJob();
        loadTable();
    }

    public void loadTable() {
        nameColumn.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().nameProperty());
        quantityColumn.setCellValueFactory(itemTableRowDataIntegerCellDataFeatures -> itemTableRowDataIntegerCellDataFeatures.getValue().quantityProperty());
        locationColumn.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().locationProperty());

        pickerTable.setEditable(true);

        // No idea how to go from here
    }

    @FXML public void orderCompleted() throws RemoteException {
        String jobId = this.job.getJobId();
        viewModel.completeJob(jobId);
    }

    @FXML public void logOut() { viewModel.logOut(); }
}
