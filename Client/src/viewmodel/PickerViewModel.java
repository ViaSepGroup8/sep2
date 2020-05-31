package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ClientModel;
import model.Item;
import model.Job;
import view.picker.PickerTableRowData;

import java.rmi.RemoteException;

public class PickerViewModel {
    private ClientModel model;
    private Job job;
    private ObservableList<PickerTableRowData> pickerList = FXCollections.observableArrayList();
    private SimpleStringProperty jobId;

    public PickerViewModel(ClientModel model) {
        this.model = model;
        jobId = new SimpleStringProperty("none");
    }
    public void logOut() { model.logOut(); }

    public void completeJob() throws RemoteException {
        if (job != null) {
            model.completeJob(job);
            job = null;
            jobId.set("none");
            pickerList.clear();
        }
        else System.out.println("you don't have a job");
    }

    public void getNewOrder()
    {
        if(job != null) System.out.println("you already have a job");
        else {
            job = model.getNewJob();
            if(job == null) System.out.println("there are probably no jobs");
            else{
                jobId.set(job.getJobId());
                pickerList.clear();
                for (Item item : job.getItems()) {
                    pickerList.add(new PickerTableRowData(item));
                }
            }
        }
    }

    public ObservableList<PickerTableRowData> getPickerList(){
        return pickerList;
    }

    public SimpleStringProperty getJobId()
    {
        return jobId;
    }

    public Job getNewJob() { return model.getNewJob(); }
}
