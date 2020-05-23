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
    private ArrayList<Item> items;
    private ArrayList<User> users;
    private ArrayList<Order> orders;
    private ArrayList<Job> jobs;

    public Database_Implementation(){
        this.items = new ArrayList<> ();
        this.users = new ArrayList<> ();
        this.orders = new ArrayList<> ();
        this.jobs = new ArrayList<> ();
    }

    @Override
    public ArrayList<Item> getAllWarehouseItems () {
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
        ArrayList<User> usuarios = new ArrayList<> ();
        User usuario = null;
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"Warehouse\".Users  WHERE(username = '" + username +"' AND password = '" + password +"')");
            while (resultSet.next ()) {
                String username1 = resultSet.getString ("username");
                String fullName = resultSet.getString ("fullname");
                int userType = resultSet.getInt ("role");
                usuario = new User (username1,fullName,UserType.values ()[userType]);
            }
        }catch (Exception e){
            System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
            System.exit (0);
        }
        System.out.println (usuario);
        return  usuario;
    }

    @Override
    public ArrayList<User> getAllUsers () {
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"Warehouse\".Users");
            while (resultSet.next ()) {
                String username = resultSet.getString ("username");
                String fullName = resultSet.getString ("fullname");
                int roleNumber = resultSet.getInt ("role");
                User user = new User (username, fullName, UserType.values ()[roleNumber]);
                users.add (user);
            }
            resultSet.close ();
            statement.close ();
            connection.close ();
        }catch (Exception e){
            System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
            System.exit (0);
        }
        System.out.println (users);
        return users;
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
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
            statement = connection.createStatement ();
            //the query changes all the order's status when we choose just one picker, ask someone how to do it
            ResultSet resultSet = statement.executeQuery ("UPDATE \"Warehouse\".orders SET status = 4 FROM \"Warehouse\".orders a JOIN \"Warehouse\".jobs b ON a.order_id = b.order_id WHERE b.username_picker = '" +user.getUsername ()+"' ;");
            resultSet.close ();
            statement.close ();
            connection.close ();
        }catch (Exception e){
            System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
            System.exit (0);
        }
    }

    @Override
    public Job getNewJob () {
        ArrayList<Job> jobss = new ArrayList<> ();
        Job job = null;
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"Warehouse\".item a FULL JOIN \"Warehouse\".jobs b ON a.job_id = b.job_id WHERE username_picker IS null;");
            while (resultSet.next ()) {
                String jobId = resultSet.getString ("job_id");
                String orderId = resultSet.getString ("order_id");
                Item item = new Item (resultSet.getInt ("id"),resultSet.getString ("description"),resultSet.getInt ("quantity"),Location.fromString (resultSet.getString ("location")), resultSet.getInt ("price"));
                ArrayList<Item> itemss = new ArrayList<> ();
                itemss.add (item);
                job = new Job (jobId,orderId,itemss);
                jobss.add (job);
            }
        }catch (Exception e){
            System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
            System.exit (0);
        }
        //Job job_1 = null;
        //job_1 = jobss.remove (0);

        System.out.println (jobss.remove (0).getItems ());
        return  jobss.remove (0);
    }

    @Override
    public Job getJobById (String id) throws InvalidDatabaseRequestException {
        //String id is from order id or job id?

        Job job = null;
        if(id.equals (null)){
            throw new InvalidDatabaseRequestException ("id cannot be null");
        }else {
            try{
                Class.forName ("org.postgresql.Driver");
                connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
                statement = connection.createStatement ();
                ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"Warehouse\".item a JOIN \"Warehouse\".orders b ON a.order_id=b.order_id WHERE job_id = "+id+";");
                while (resultSet.next ()) {
                    String jobId = resultSet.getString ("job_id");
                    String orderId = resultSet.getString ("order_id");
                    Item item = new Item (resultSet.getInt ("id"),resultSet.getString ("description"),resultSet.getInt ("quantity"),Location.fromString (resultSet.getString ("location")), resultSet.getInt ("price"));
                    ArrayList<Item> itemss = new ArrayList<> ();
                    //only prints one item
                    itemss.add (item);
                    job = new Job (jobId,orderId,itemss);
                }
            }catch (Exception e) {
                System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
                System.exit (0);
            }
            for (int i = 0; i<job.getItems ().size (); i++){
                System.out.println (job.getItems ().get (i).getLocation ());
            }
            return  job;
        }
    }

    @Override
    public Job getJobByUser (User user) throws InvalidDatabaseRequestException {

        Job job = null;
        if(user.equals (null)){
            throw new InvalidDatabaseRequestException ("id cannot be null");
        }else {
            try{
                Class.forName ("org.postgresql.Driver");
                connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
                statement = connection.createStatement ();
                ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"Warehouse\".item a JOIN \"Warehouse\".orders b ON a.order_id=b.order_id WHERE username = '"+user.getUsername ()+"';");
                while (resultSet.next ()) {
                    String jobId = resultSet.getString ("job_id");
                    String orderId = resultSet.getString ("order_id");
                    Item item = new Item (resultSet.getInt ("id"),resultSet.getString ("description"),resultSet.getInt ("quantity"),Location.fromString (resultSet.getString ("location")), resultSet.getInt ("price"));
                    ArrayList<Item> itemss = new ArrayList<> ();
                    //only prints one item
                    itemss.add (item);
                    job = new Job (jobId,orderId,itemss);
                }
            }catch (Exception e) {
                System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
                System.exit (0);
            }
            for (int i = 0; i<job.getItems ().size (); i++){
                System.out.println (job.getItems ().get (i).toString ());
            }
            return  job;
        }
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
        if(orderId.equals (null) || status.equals (null)){
            throw new InvalidDatabaseRequestException ("order id and status no bueno te has equivocado");
        }else {
            try {
                Class.forName ("org.postgresql.Driver");
                connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse", "postgres", "password1234?");
                statement = connection.createStatement ();
                //the query changes all the order's status when we choose just one picker, ask someone how to do it
                ResultSet resultSet = statement.executeQuery ("UPDATE \"Warehouse\".orders SET status = " + status + " WHERE order_id = " + orderId);
                resultSet.close ();
                statement.close ();
                connection.close ();
            } catch (Exception e) {
                System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
                System.exit (0);
            }
        }
    }

    @Override
    public Order getNewPickupOrder (User driver) throws InvalidDatabaseRequestException {
        ArrayList<Order> order_driver = new ArrayList<> ();
        if(driver.equals (null)){
            throw new InvalidDatabaseRequestException ("driver cannot be null");
        }else {
            try{
                Class.forName ("org.postgresql.Driver");
                connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
                statement = connection.createStatement ();
                ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"Warehouse\".orders a LEFT JOIN \"Warehouse\".users b ON a.username = b.username  WHERE status = 3;");
                while (resultSet.next ()) {
                    String customer = resultSet.getString ("username");
                    int userType = resultSet.getInt ("role");
                    User user = new User (customer,UserType.values ()[userType]);
                    int orderStatus = resultSet.getInt ("status");
                    String uniqueId = resultSet.getString ("order_id");
                    String gate = resultSet.getString ("gate");
                    String delivery_address = resultSet.getString ("delivery_address");
                    Order order = new Order (user,OrderStatus.values()[orderStatus],uniqueId,gate,delivery_address);
                    order_driver.add (order);
                }
            }catch (Exception e) {
                System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
                System.exit (0);
            }
            System.out.println (order_driver);
            System.out.println (order_driver.remove (0));
            return order_driver.remove (0);
        }
    }
}
