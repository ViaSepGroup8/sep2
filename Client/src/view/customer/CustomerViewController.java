package view.customer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import model.Item;
import view.ViewHandler;
import viewmodel.CustomerViewModel;

import java.util.ArrayList;

public class CustomerViewController {
    //Labels
    @FXML public Label statusLabel;
    @FXML public Label totalSum;
    @FXML public Label customerName;

    //Table 1
    @FXML public TableView<ItemTableRowData> itemsTable;
    @FXML public TableColumn<ItemTableRowData, Number> id;
    @FXML public TableColumn<ItemTableRowData, String> item;
    @FXML public TableColumn<ItemTableRowData, Number> quantity;
    @FXML public TableColumn<ItemTableRowData, Number> price;

    //Table 2
    @FXML public TableView<Item> ordersTable;
    @FXML public TableColumn<Item, Number> orderId;
    @FXML public TableColumn<Item, String> numberOfItems;
    @FXML public TableColumn<Item, Number> totalPrice;
    @FXML public TableColumn<Item, Number> tableStatus;

    //Other
    private ViewHandler viewHandler;
    private CustomerViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, CustomerViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        id.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ItemTableRowData, Number>, ObservableValue<Number>>()
        {
            @Override public ObservableValue<Number> call(TableColumn.CellDataFeatures<ItemTableRowData, Number> itemTableRowDataNumberCellDataFeatures)
            {
                return itemTableRowDataNumberCellDataFeatures.getValue().uniqueIdProperty();
            }
        });
        item.setCellValueFactory(CellData -> CellData.getValue().nameProperty());

        quantity.setCellValueFactory(itemTableRowDataIntegerCellDataFeatures -> itemTableRowDataIntegerCellDataFeatures.getValue().quantityProperty());

        price.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ItemTableRowData, Number>, ObservableValue<Number>>()
        {
            @Override public ObservableValue<Number> call(TableColumn.CellDataFeatures<ItemTableRowData, Number> itemTableRowDataNumberCellDataFeatures)
            {
                return itemTableRowDataNumberCellDataFeatures.getValue().priceProperty();
            }
        });

        //Update the table to allow for the first and last name fields
        //to be editable
        itemsTable.setEditable(true);

//        roomNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        functionalitiesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        subjectsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        quantity.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
    }

    public Region getRoot() { return root; }

    //https://stackoverflow.com/questions/27900344/how-to-make-a-table-column-with-integer-datatype-editable-without-changing-it-to

    public void onNewOrder (ActionEvent actionEvent) {
        itemsTable.setItems(viewModel.getAllWarehouseItems());
    }

    public void onAcceptOrder (ActionEvent actionEvent) {
        ArrayList<ItemTableRowData> itemsSelectedRowData = new ArrayList<ItemTableRowData>();
        ArrayList<Item> itemsSelected = new ArrayList<Item>();
        itemsSelectedRowData.addAll(itemsTable.getItems());

        //Insert all elements from itemsSelectedRowData to itemsSelected(converting each element to Item Type)
        for (ItemTableRowData item : itemsSelectedRowData) {
            itemsSelected.add(item.toItemObject());
        }

        //Remove all not selected items by customer
        for (Item item : itemsSelected) {
            if (item.getQuantity() == 0){
                itemsSelected.remove(item);
            }
        }

        //Upload table with items selected to the second FXML table.
        for (Item item : itemsSelected) {
            orderId.setCellValueFactory();
            numberOfItems.setCellValueFactory(CellData -> new ItemTableRowData((item).getQuantity());
            totalPrice.setCellValueFactory();
            totalPrice.setCellValueFactory();
            tableStatus.setCellValueFactory();
        }
        /* HOW IT SHOULD BE DONE(Arraylist to TableView)
            columnOne.setCellValueFactory(c -> new SimpleStringProperty(new String("123")));
            columnTwo.setCellValueFactory(c -> new SimpleStringProperty(new String("456")));
        */

        //Pass info to viewModel
        viewModel.createCustomerNewOrder(itemsSelected);
    }

    public void onLogOut (ActionEvent actionEvent) {
        viewModel.logOut();
    }
}

//package gui;
//
//    import examPlanner.Exam;
//    import examPlanner.MyDate;
//    import examPlanner.Room;
//    import javafx.beans.value.ChangeListener;
//    import javafx.beans.value.ObservableValue;
//    import javafx.collections.FXCollections;
//    import javafx.collections.ObservableList;
//    import javafx.event.ActionEvent;
//    import javafx.fxml.FXML;
//    import javafx.scene.control.ListView;
//    import javafx.scene.control.TableColumn;
//    import javafx.scene.control.TableView;
//    import javafx.scene.control.TextField;
//    import javafx.scene.control.cell.PropertyValueFactory;
//
//    import java.util.ArrayList;
//
//public class MainController extends Controller{
//
//    //Table
//    @FXML public TableView<Exam> examTable;
//    @FXML public TableColumn<Exam, String> subjectColumn;
//    @FXML public TableColumn<Exam, Room> RoomColumn;
//    @FXML public TableColumn<Exam, MyDate> DateColumn;
//    @FXML public TableColumn<Exam, String> TeacherColumn;
//    @FXML public TableColumn<Exam, String> typeColumn;
//    @FXML public TableColumn<Exam, String> formatColumn;
//    @FXML public TableColumn<Exam, String> ectsColumn;
//    @FXML public TableColumn<Exam, String> examinersColumn;
//
//    //ListViews
//    @FXML public ListView subjectList;
//    @FXML public ListView roomList;
//    @FXML public ListView dateList;
//
//    //TextFields
//    @FXML public TextField teacherTextField;
//    @FXML public TextField typeTextField;
//    @FXML public TextField formatTextField;
//    @FXML public TextField ectsTextField;
//    @FXML public TextField examinersTextField;
//
//    @Override public void initData()
//    {
//        subjectColumn.setCellValueFactory(new PropertyValueFactory<Exam, String>("subject"));
//        RoomColumn.setCellValueFactory(new PropertyValueFactory<Exam, Room>("room"));
//        DateColumn.setCellValueFactory(new PropertyValueFactory<Exam, MyDate>("date"));
//
//        TeacherColumn.setCellValueFactory(new PropertyValueFactory<Exam, String>("teacher"));
//        typeColumn.setCellValueFactory(new PropertyValueFactory<Exam, String>("type"));
//        formatColumn.setCellValueFactory(new PropertyValueFactory<Exam, String>("format"));
//        ectsColumn.setCellValueFactory(new PropertyValueFactory<Exam, String>("ects"));
//        examinersColumn.setCellValueFactory(new PropertyValueFactory<Exam, String>("examiners"));
//        loadLists();
//    }
//
//    @Override public void reset()
//    {
//        loadLists();
//    }
//
//    public void loadLists(){
//        //Get data for Exams;
//        ObservableList<Exam> exams = FXCollections.observableArrayList();
//        exams.addAll(getModel().getExams());
//        examTable.setItems(exams);
//        examTable.getSelectionModel().selectFirst();
//
//        //Get all subjects
//        ObservableList<String> subjects = FXCollections.observableArrayList();
//        subjects.addAll(getModel().getAllSubjects());
//        subjectList.setItems(subjects);
//        subjectList.getSelectionModel().selectFirst();
//
//        //Get all rooms
//        ObservableList<Room> rooms = FXCollections.observableArrayList();
//        rooms.addAll(getModel().getRooms());
//        roomList.setItems(rooms);
//        roomList.getSelectionModel().selectFirst();
//
//
//        //add listener for change in selection
//        subjectList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
//        {
//            @Override public void changed(ObservableValue observableValue, Object o,
//                Object t1)
//            {
//                showFreeDates();
//            }
//        });
//        roomList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
//        {
//            @Override public void changed(ObservableValue observableValue, Object o,
//                Object t1)
//            {
//                showFreeDates();
//            }
//        });
//        //Get all possible dates and show them
//        showFreeDates();
//    }
//
//    public void showFreeDates(){
//        ObservableList<MyDate> dates = FXCollections.observableArrayList();
//        dates.addAll(getModel().getAllFreeDates((String)subjectList.getSelectionModel().getSelectedItem(), (Room)roomList.getSelectionModel().getSelectedItem()));
//        dateList.setItems(dates);
//        dateList.getSelectionModel().selectFirst();
//    }
//
//    //      leaderListView.getSelectionModel().selectedItemProperty()
//    //        .addListener(new ChangeListener<Person>() {
//    //    public void changed(ObservableValue<? extends Person> observable,
//    //        Person oldValue, Person newValue) {
//    //      System.out.println("selection changed");
//    //    }
//    //  });
//
//    public void manageRooms(ActionEvent actionEvent)
//    {
//        getViewHandler().openView("manageRooms");
//    }
//
//    public void manageStudents(ActionEvent actionEvent)
//    {
//        getViewHandler().openView("manageStudents");
//    }
//
//    public void viewSchedule(ActionEvent actionEvent)
//    {
//    }
//
//    public void changePeriod(ActionEvent actionEvent)
//    {
//        getViewHandler().openView("changePeriod");
//    }
//
//    public void deleteExam(ActionEvent actionEvent)
//    {
//        examTable.getItems().remove(examTable.getSelectionModel().getSelectedItem());
//
//        ArrayList<Exam> exams = new ArrayList<Exam>();
//        exams.addAll(examTable.getItems());
//        getModel().setExams(exams);
//
//        loadLists();
//    }
//
//    public void createExam(ActionEvent actionEvent)
//    {
//
//        Exam newExam = new Exam(
//            (MyDate) dateList.getSelectionModel().getSelectedItem(),
//            (String) subjectList.getSelectionModel().getSelectedItem(),
//            (Room) roomList.getSelectionModel().getSelectedItem(),
//            teacherTextField.getText(),
//            typeTextField.getText(),
//            formatTextField.getText(),
//            ectsTextField.getText(),
//            examinersTextField.getText());
//
//        examTable.getItems().add(newExam);
//
//        ArrayList<Exam> exams = new ArrayList<Exam>();
//        exams.addAll(examTable.getItems());
//        getModel().setExams(exams);
//
//        loadLists();
//    }
//
//    public void saveChanges(ActionEvent actionEvent)
//    {
//        //todo save everything to lists !!!
//        ArrayList<Exam> exams = new ArrayList<Exam>();
//        exams.addAll(examTable.getItems());
//        getModel().setExams(exams);
//        getModel().save("data.bin");
//        getViewHandler().openView("mainView");
//    }
//
//    public void upload(ActionEvent actionEvent){
//        getModel().saveToCSV();
//        getModel().uploadToFTP();
//    }
//
//    public void exit(ActionEvent actionEvent)
//    {
//        System.exit(0);
//    }
//}
