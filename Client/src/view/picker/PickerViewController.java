package view.picker;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import model.Job;
import view.ViewHandler;
import viewmodel.PickerViewModel;

import java.rmi.RemoteException;

public class PickerViewController {
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

        idColumn.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().uniqueIdProperty());
        nameColumn.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().nameProperty());
        quantityColumn.setCellValueFactory(itemTableRowDataIntegerCellDataFeatures -> itemTableRowDataIntegerCellDataFeatures.getValue().quantityProperty());
        locationColumn.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().locationProperty());

        pickerTable.setEditable(true);

        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

        //Table
        idColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PickerTableRowData, Number>, ObservableValue<Number>>() {
            @Override public ObservableValue<Number> call(
                    TableColumn.CellDataFeatures<PickerTableRowData, Number> orderTableRowDataNumberCellDataFeatures) {
                return orderTableRowDataNumberCellDataFeatures.getValue().uniqueIdProperty();
            }
        });

        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PickerTableRowData, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<PickerTableRowData, String> orderTableRowDataStringCellDataFeatures) {
                return orderTableRowDataStringCellDataFeatures.getValue().nameProperty();
            }
        });

        quantityColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PickerTableRowData, Number>, ObservableValue<Number>>() {
            @Override public ObservableValue<Number> call(
                    TableColumn.CellDataFeatures<PickerTableRowData, Number> orderTableRowDataNumberCellDataFeatures) {
                return orderTableRowDataNumberCellDataFeatures.getValue().quantityProperty();
            }
        });


        locationColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PickerTableRowData, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call (
                    TableColumn.CellDataFeatures<PickerTableRowData, String> orderTableRowDataStringCellDataFeatures) {
                return orderTableRowDataStringCellDataFeatures.getValue().locationProperty();
            }
        });
        //ObservableList<PickerTableRowData> pickerList = viewModel.getPickerList();
        getNewJob();
        // I need to receive a Job object and initiate the job variable
    }

    public Region getRoot() { return root; }

    @FXML public void newOrderButtonPressed()  { getNewJob(); }

    @FXML public void orderCompletedButtonPressed() throws RemoteException {
        if (job != null) {
            String jobId = this.job.getJobId();
            viewModel.completeJob(job);
        }
        else { System.out.println("Job is null"); }
        pickerTable.setItems(null);
    }

    @FXML public void logOut() { viewModel.logOut(); }

    private void getNewJob() {
        try {
            job = viewModel.getNewJob();
            pickerTable.setItems(viewModel.getPickerList(job));
            System.out.println("New job loaded");
        } catch (Exception e) { System.out.println("No job loaded"); }
    }
}
