package viewmodel;

import model.WarehouseClientModelManager;

import java.io.IOException;

public class ViewModelFactory {
    private LoginViewModel loginViewModel;
    private CustomerViewModel customerViewModel;
    private AdministratorViewModel administratorViewModel;
    private DriverViewModel driverViewModel;
    private PickerViewModel pickerViewModel;

    public ViewModelFactory() throws IOException {
        WarehouseClientModelManager warehouseClientModelManager = new WarehouseClientModelManager();
        loginViewModel = new LoginViewModel(warehouseClientModelManager);
        customerViewModel = new CustomerViewModel(warehouseClientModelManager);
        administratorViewModel = new AdministratorViewModel(warehouseClientModelManager);
        driverViewModel = new DriverViewModel(warehouseClientModelManager);
        pickerViewModel = new PickerViewModel(warehouseClientModelManager);
    }

    public LoginViewModel getLoginViewModel() { return loginViewModel; }
    public CustomerViewModel getCustomerViewModel() { return customerViewModel; }
    public AdministratorViewModel getAdministratorViewModel() { return administratorViewModel; }
    public DriverViewModel getDriverViewModel() { return driverViewModel; }
    public PickerViewModel getPickerViewModel() { return pickerViewModel; }
}
