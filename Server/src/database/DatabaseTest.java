package database;

import logger.Logger;
import model.UserType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabaseTest {
    private Database database;

    @BeforeEach
    void setUp()
    {
        Logger.getInstance().setEnabled(false);
        database = new Database_Implementation();
        database.deleteAllData();
        database.setupStructure();
        database.loadSampleData();
        print("------------- ------------- TEST START ------------- -------------");
    }

    @AfterEach
    void tearDown()
    {
        print("------------- ------------- TEST END   ------------- -------------");
        database.close();
    }

    @Test
    void getAllWarehouseItems()
    {
        print("all items in the warehouse:");
        print(database.getAllWarehouseProducts());
    }

    @Test
    void getUser()
    {
        print("test for valid input:");
        print(database.getUser("javi", "ipabnfdf54asfpas"));
        print("test for invalid input:");
        print(database.getUser("javi", "not-his-passsowrd"));
    }

    @Test
    void getAllUsers() {
        print("all warehouse users: ");
        print(database.getAllUsers());
    }

    @Test
    void addUser() {
        print("current users: ");
        print(database.getAllUsers());
        print("adding new users, list after adding");
        database.addUser("tester1337", "FULL NAME", UserType.CUSTOMER, "*secret*");
        database.addUser("tester2222", "FULL LONG NAME", UserType.PICKER, "*coool*");
        print(database.getAllUsers());
    }

    @Test
    void removeUser() {
        print("current users: ");
        database.addUser("tester", "true tester doesnt have a name", UserType.CUSTOMER, "*secret*");
        print(database.getAllUsers());
        print("removing tester user, list after removing");
        database.removeUser("tester");
        print(database.getAllUsers());
    }

    @Test
    void createJob() {
        print("adding a job");
        print(database.createJob("1"));
        print("adding a job");
        print(database.createJob("1"));
        print("adding a job");
        print(database.createJob("1"));
    }

    @Test
    void completeJob() {
    }

    @Test
    void getNewJob() {
    }

    @Test
    void getJobById() {
    }

    @Test
    void getJobByUser() {
    }

    @Test
    void remove() {
    }

    @Test
    void addOrder() {
    }

    @Test
    void getUserOrders() {
    }

    @Test
    void getAllOrders() {
    }

    @Test
    void setOrderStatus() {
    }

    @Test
    void getNewPickupOrder() {
    }

    static void print(Object o){ System.out.println("TEST " + o); }
}