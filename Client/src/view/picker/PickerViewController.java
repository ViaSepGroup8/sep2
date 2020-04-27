package view.picker;

import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Region;
import model.Item;
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

//    @FXML public TableView<Item> pickerTable;
//    @FXML public TableColumn<> nameColumn;
//    @FXML public TableColumn<> quantityColumn;
//    @FXML public TableColumn<> locationColumn;

    public PickerViewController() {}

    public void init(ViewHandler viewHandler, PickerViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    public void loadTable() {
        /*
        ObservableList<Exam> exams = FXCollections.observableArrayList();
        exams.addAll(getModel().getExams());
        examTable.setItems(exams);
        examTable.getSelectionModel().selectFirst();*/
    }

    public Region getRoot() { return root; }

    public void getNewJob() throws RemoteException {
        job = viewModel.getNewJob();
        loadTable();
    }

    @FXML public void orderCompleted() {
        //viewModel.completeJob();
    }

    @FXML public void logOut() { viewModel.logOut(); }
}
