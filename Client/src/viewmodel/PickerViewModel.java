package viewmodel;

import model.ClientModel;

public class PickerViewModel {
    private ClientModel model;

    public PickerViewModel(ClientModel model) {
        this.model = model;
    }

    public void logOut() { model.logOut(); }
}
