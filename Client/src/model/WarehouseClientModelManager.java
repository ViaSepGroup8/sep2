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
                break;
            case 1:
                System.out.println("Logged in as administrator!");
                //Will open admin view
                break;
            case 2:
                System.out.println("Logged in as customer!");
                //Will open customer view
                break;
            case 3:
                System.out.println("Logged in as picker!");
                //Will open picker view
                break;
            case 4:
                System.out.println("Logged in as driver!");
                //Will open driver view
                break;
        }
    }

    //Administrator

    //Costumer

    //Driver

    //Picker

}
