package viewmodel;

import model.ClientModel;
import model.Job;

import java.rmi.RemoteException;

public class PickerViewModel {
    private ClientModel model;

    public PickerViewModel(ClientModel model) {
        this.model = model;
    }

    public Job getNewJob() throws RemoteException { return model.getNewJob(); }

    public void logOut() { model.logOut(); }

    public void completeJob(String jobId) throws RemoteException { model.completeJob(jobId); }
}
