package view.driver;

import javafx.scene.layout.Region;
import view.ViewHandler;
import viewmodel.DriverViewModel;

public class DriverViewController {
    private ViewHandler viewHandler;
    private DriverViewModel viewModel;
    private Region root;

    public DriverViewController() {}

    public void init(ViewHandler viewHandler, DriverViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    public Region getRoot() { return root; }
}
