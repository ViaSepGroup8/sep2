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
        this.loginViewModel = new LoginViewModel(warehouseClientModelManager);
        this.customerViewModel = new CustomerViewModel(warehouseClientModelManager);
        this.administratorViewModel = new AdministratorViewModel(warehouseClientModelManager);
        this.driverViewModel = new DriverViewModel(warehouseClientModelManager);
        this.pickerViewModel = new PickerViewModel(warehouseClientModelManager);
    }

    public LoginViewModel getLoginViewModel() { return loginViewModel; }
    public CustomerViewModel getCustomerViewModel() { return customerViewModel; }
    public AdministratorViewModel getAdministratorViewModel() { return administratorViewModel; }
    public DriverViewModel getDriverViewModel() { return driverViewModel; }
    public PickerViewModel getPickerViewModel() { return pickerViewModel; }
}
