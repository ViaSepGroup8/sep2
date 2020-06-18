package viewmodel;

import model.ClientModel;

import java.io.IOException;

public class ViewModelFactory {
    private LoginViewModel loginViewModel;
    private CustomerViewModel customerViewModel;
    private AdministratorViewModel administratorViewModel;
    private DriverViewModel driverViewModel;
    private PickerViewModel pickerViewModel;

    public ViewModelFactory(ClientModel model) throws IOException {
        this.loginViewModel = new LoginViewModel(model);
        this.customerViewModel = new CustomerViewModel(model);
        this.administratorViewModel = new AdministratorViewModel(model);
        this.driverViewModel = new DriverViewModel(model);
        this.pickerViewModel = new PickerViewModel(model);
    }

    public LoginViewModel getLoginViewModel() { return loginViewModel; }
    public CustomerViewModel getCustomerViewModel() { return customerViewModel; }
    public AdministratorViewModel getAdministratorViewModel() { return administratorViewModel; }
    public DriverViewModel getDriverViewModel() { return driverViewModel; }
    public PickerViewModel getPickerViewModel() { return pickerViewModel; }
}
