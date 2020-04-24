package view.picker;

import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Region;
import model.Job;
import view.ViewHandler;
import viewmodel.PickerViewModel;

import java.rmi.RemoteException;

public class PickerViewController {
    private ViewHandler viewHandler;
    private PickerViewModel viewModel;
    private Region root;
    private Job job;
    @FXML private TreeTableColumn nameColumn;
    @FXML private TreeTableColumn quantityColumn;
    @FXML private TreeTableColumn locationColumn;

    public PickerViewController() {}

    public void init(ViewHandler viewHandler, PickerViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    public Region getRoot() { return root; }

    public void getNewJob() throws RemoteException { job = viewModel.getNewJob(); }

    @FXML public void orderCompleted() {
        //viewModel.completeJob();
    }

    @FXML public void logOut() { viewModel.logOut(); }
}
