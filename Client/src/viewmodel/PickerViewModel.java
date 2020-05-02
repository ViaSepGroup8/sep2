package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ClientModel;
import model.Item;
import model.Job;
import model.Order;
import view.customer.OrderTableRowData;
import view.picker.PickerTableRowData;

import java.rmi.RemoteException;

public class PickerViewModel {
    private ClientModel model;

    public PickerViewModel(ClientModel model) { this.model = model; }

    public Job getNewJob() throws RemoteException { return model.getNewJob(); }

    public void logOut() { model.logOut(); }

    public void completeJob(String jobId) throws RemoteException { model.completeJob(jobId); }

    // I have doubts if this will work
    public ObservableList<PickerTableRowData> getPickerList() throws RemoteException {
        ObservableList<PickerTableRowData> list = FXCollections.observableArrayList();
        for (Item item:  model.getNewJob().getItems()) {
            list.add(new PickerTableRowData(item));
        }
        return list;
    }
}
