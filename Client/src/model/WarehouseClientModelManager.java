package model;

public class WarehouseClientModelManager implements WarehouseClientModel {

    //Login
    public void login(String username, String password) { System.out.println("Logging in..."); }

    @Override
    public void answer(int profession) {
        switch (profession) {
            case 0:
                System.out.println("Impossible to login, try again!");
                //No profession (it will give and error message)
            case 1:
                System.out.println("Logged in as administrator!");
                //Will open admin view
            case 2:
                System.out.println("Logged in as customer!");
                //Will open customer view
            case 3:
                System.out.println("Logged in as picker!");
                //Will open picker view
            case 4:
                System.out.println("Logged in as driver!");
                //Will open driver view
        }
    }

    //Administrator

    //Costumer

    //Driver

    //Picker

}
