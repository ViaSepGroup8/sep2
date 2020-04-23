package view.picker;

import javafx.fxml.FXML;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.Region;
import view.ViewHandler;
import viewmodel.PickerViewModel;

public class PickerViewController {
    private ViewHandler viewHandler;
    private PickerViewModel viewModel;
    private Region root;
    @FXML private TreeTableView pickerTable;

    public PickerViewController() {}

    public void init(ViewHandler viewHandler, PickerViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    public Region getRoot() { return root; }

    @FXML public void orderCompleted() {

    }

    @FXML public void logOut() {

    }
}
