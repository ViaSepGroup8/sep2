package view.picker;

import javafx.scene.layout.Region;
import view.ViewHandler;
import viewmodel.PickerViewModel;

public class PickerViewController {
    private ViewHandler viewHandler;
    private PickerViewModel viewModel;
    private Region root;

    public PickerViewController() {}

    public void init(ViewHandler viewHandler, PickerViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    public Region getRoot() { return root; }
}
