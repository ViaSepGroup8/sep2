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
    @FXML public TableColumn<ItemTableRowData, Location> locationColumn;

    public PickerViewController() {}

    public void init(ViewHandler viewHandler, PickerViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    public void loadTable() {

    }

    public Region getRoot() { return root; }

    public void getNewJob() throws RemoteException {
        job = viewModel.getNewJob();
        loadTable();
    }

    @FXML public void orderCompleted() throws RemoteException {
        String jobId = "POOP"
        viewModel.completeJob(jobId);
    }

    @FXML public void logOut() { viewModel.logOut(); }
}
