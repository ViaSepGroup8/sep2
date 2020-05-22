package database;

import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Database_Implementation implements Database{
    Connection connection = null;
    Statement statement = null;

    @Override
    public ArrayList<Item> getAllWarehouseItems () {
        ArrayList<Item> items = new ArrayList<> ();
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"Warehouse\".Item");
            while (resultSet.next ()) {
               // String id = resultSet.getString ("id") + ", description" + resultSet.getString ("description")+ ", price:" + resultSet.getString ("price") + ", quantity" + resultSet.getString ("quantity") ;
                //System.out.println (id);
                int uniquedId = resultSet.getInt ("id");
                String name = resultSet.getString ("description");
                int quantity = resultSet.getInt ("quantity");
                Location location = Location.fromString (resultSet.getString ("location"));
                int price = resultSet.getInt ("price");
                Item item = new Item (uniquedId, name, quantity, location, price);
                items.add (item);
            }
            resultSet.close ();
            statement.close ();
            connection.close ();
        }catch (Exception e){
            System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
            System.exit (0);
        }
        System.out.println (items);
        return items;
    }

    @Override
    public User getUser (String username, String password) {
        return null;
    }

    @Override
    public void addJob (Job job) {
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
            statement = connection.createStatement ();
            String jobId = job.getJobId ();
            String orderId = job.getOrderId ();
            ResultSet resultSet = statement.executeQuery ("INSERT INTO \"Warehouse\".jobs VALUES(" +jobId+ "," + null+"," +orderId +")" );
            resultSet.close ();
            statement.close ();
            connection.close ();
        }catch (Exception e){
            System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
            System.exit (0);
        }
    }

    @Override
    public void completeJob (User user, Job job) {

    }

    @Override
    public Job getNewJob () {
        return null;
    }

    @Override
    public Job getJobById (String id) throws InvalidDatabaseRequestException {
        return null;
    }

    @Override
    public Job getJobByUser (User user) throws InvalidDatabaseRequestException {
        return null;
    }

    @Override
    public void addOrder (Order order) throws InvalidDatabaseRequestException {
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
            statement = connection.createStatement ();
            User username = order.getCustomer ();
            OrderStatus status = order.getStatus ();
            String uniqueId = order.getUniqueId ();
            String gate = order.getGate ();
            int totalItems = order.totalItemsNumber ();
            String delivery_address = order.getDeliverAddress ();
            ResultSet resultSet = statement.executeQuery ("INSERT INTO \"Warehouse\".orders VALUES(" +uniqueId+ "," +totalItems+"," +status + ","+gate + "," + delivery_address + ","+username +")" );
            resultSet.close ();
            statement.close ();
            connection.close ();
        }catch (Exception e){
            System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
            System.exit (0);
        }
    }

    @Override
    public ArrayList<Order> getUserOrders (User customer) {
        ArrayList<Order> orders = new ArrayList<> ();

        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"Warehouse\".Orders a LEFT JOIN \"Warehouse\".Users b ON a.username = b.username");
            while (resultSet.next ()) {
                String username = resultSet.getString ("username");
                String fullName = resultSet.getString ("fullname");
                int roleNumber = resultSet.getInt ("role");
                User user = new User (username, fullName, UserType.values ()[roleNumber]);
                int status = resultSet.getInt ("status");
                String uniqueId ="" + resultSet.getInt ("order_id");
                String gate = resultSet.getString ("gate");
                String delivery_address = resultSet.getString ("delivery_address");
                Order order = new Order (user,OrderStatus.values()[status],uniqueId,gate,delivery_address);
                orders.add (order);
            }
            resultSet.close ();
            statement.close ();
            connection.close ();
        }catch (Exception e){
            System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
            System.exit (0);
        }
        for (Order order: orders) {
            if (order.getCustomer () != customer){
                orders.remove (order);
            }
        }
        System.out.println (orders);
        return orders;
    }

    @Override
    public ArrayList<Order> getAllOrders () {
        ArrayList<Order> orders = new ArrayList<> ();

        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"Warehouse\".Orders a LEFT JOIN \"Warehouse\".Users b ON a.username = b.username");
            while (resultSet.next ()) {
                String username = resultSet.getString ("username");
                String fullName = resultSet.getString ("fullname");
                int roleNumber = resultSet.getInt ("role");
                User user = new User (username, fullName, UserType.values ()[roleNumber]);
                int status = resultSet.getInt ("status");
                String uniqueId ="" + resultSet.getInt ("order_id");
                String gate = resultSet.getString ("gate");
                String delivery_address = resultSet.getString ("delivery_address");
                Order order = new Order (user,OrderStatus.values()[status],uniqueId,gate,delivery_address);
                orders.add (order);
            }
            resultSet.close ();
            statement.close ();
            connection.close ();
        }catch (Exception e){
            System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
            System.exit (0);
        }
        System.out.println (orders);
        return orders;
    }

    @Override
    public void setOrderStatus (String orderId, OrderStatus status) throws InvalidDatabaseRequestException {

    }

    @Override
    public Order getNewPickupOrder (User driver) throws InvalidDatabaseRequestException {
        return null;
    }
}
