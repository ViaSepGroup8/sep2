package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ClientModel;
import model.Item;
import model.Job;
import view.picker.PickerTableRowData;

import java.rmi.RemoteException;

public class PickerViewModel {
    private ClientModel model;

    public PickerViewModel(ClientModel model) { this.model = model; }

    public void logOut() { model.logOut(); }

    public void completeJob(Job job) throws RemoteException { model.completeJob(job); }

    // I have doubts if this will work
    public ObservableList<PickerTableRowData> getPickerList(Job job){
        ObservableList<PickerTableRowData> list = FXCollections.observableArrayList();

        if(job == null) {
            System.out.println("There are probably no jobs");
        }
        for (Item item: job.getItems()) {
            list.add(new PickerTableRowData(item));
        }
        return list;
    }

    public Job getNewJob() { return model.getNewJob(); }
}
