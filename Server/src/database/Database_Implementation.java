package database;

import logger.Logger;
import model.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

public class Database_Implementation implements Database{

    // database credentials
    static final String USER = "postgres";
    static final String PASS = "password";
    static final String URL  = "jdbc:postgresql://localhost:5432/";
    static final String DB   = "warehouse_database";
    Connection connection;
    Statement statement;

    @Override
    public void connect() {
        // make connection to the database
        if(connection != null) { Logger.getInstance().addLog("db already connected");return;}
        try { Class.forName("org.postgresql.Driver"); } catch (ClassNotFoundException e){ e.printStackTrace(); Logger.getInstance().addLog("db cannot init driver");}
        try { connection = DriverManager.getConnection(URL + DB,USER,PASS); Logger.getInstance().addLog("db connected");}
        catch (SQLException e) { e.printStackTrace(); Logger.getInstance().addLog("db connection error");}
    }

    @Override
    public void setupStructure() {
        // create a postgres database and try to connect make a connection
        managePostgres("CREATE DATABASE " + DB + ";");
        connect();

        // read postgres-structure-setup.sql and try to execute it
        String setupSQL = fileToString("posgres-structure-setup.sql");
        executeSingleSQL(setupSQL);
    }

    @Override
    public void loadSampleData() {
        // read sample-data.sql and try to execute it
        String sampleData = fileToString("sample-data.sql");
        executeSingleSQL(sampleData);
    }

    @Override
    public void deleteAllData()
    {
        // kick all users first, trying to drop a database while users are connected would result in error
        managePostgres("SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '" + DB +"' AND pid <> pg_backend_pid();");
        // drop the database
        managePostgres("DROP DATABASE IF EXISTS "+DB+";");
    }

    @Override
    public void close() {
        try { if(connection!=null) connection.close(); Logger.getInstance().addLog("db disconnected");}
        catch (SQLException e) { e.printStackTrace(); }

    }

    @Override
    public ArrayList<Item> getAllWarehouseItems () {
        ArrayList<Item> items = new ArrayList<Item>();
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database","postgres","password1234?");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"warehouse\".item");
            while (resultSet.next ()) {
               // String id = resultSet.getString ("id") + ", description" + resultSet.getString ("description")+ ", price:" + resultSet.getString ("price") + ", quantity" + resultSet.getString ("quantity") ;
                ////System.out.println (id);
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
        //System.out.println (items);
        return items;
    }

    @Override
    public User getUser (String username, String password) {
        ArrayList<User> usuarios = new ArrayList<> ();
        User usuario = null;
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database","postgres","password1234?");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"warehouse\".Users  WHERE(username = '" + username +"' AND password = '" + password +"')");
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
        if(usuario==null) return new User("",UserType.UNKNOWN);
        return  usuario;
    }

    @Override
    public ArrayList<User> getAllUsers () {
        ArrayList<User> users = new ArrayList<User>();
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database","postgres","password1234?");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"warehouse\".Users");
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
        //System.out.println (users);
        return users;
    }

    @Override
    public void addUser(String username, String fullName, UserType userType, String password) {
        //todo add user method
    }

    @Override
    public void removeUser(String username) {

    }

    @Override
    public String createJob(String order_id) {
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database","postgres","password1234?");
            statement = connection.createStatement ();
            String jobId = "";
            String orderId = "";
            ResultSet resultSet = statement.executeQuery ("INSERT INTO \"warehouse\".jobs VALUES(" +jobId+ "," + null+"," +orderId +")" );
            resultSet.close ();
            statement.close ();
            connection.close ();
        }catch (Exception e){
            System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
            System.exit (0);
        }
        return null;
    }

    @Override
    public void jobAddItem(String item_id) {

    }


    @Override
    public void completeJob (User user, Job job) {
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database","postgres","password1234?");
            statement = connection.createStatement ();
            //the query changes all the order's status when we choose just one picker, ask someone how to do it
            ResultSet resultSet = statement.executeQuery ("UPDATE \"warehouse\".orders SET status = 4 FROM \"warehouse\".orders a JOIN \"warehouse\".jobs b ON a.order_id = b.order_id WHERE b.username_picker = '" +user.getUsername ()+"' ;");
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
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database","postgres","password1234?");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"warehouse\".item a FULL JOIN \"warehouse\".jobs b ON a.job_id = b.job_id WHERE username_picker IS null;");
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

        //System.out.println (jobss.remove (0).getItems ());
        return  jobss.remove (0);
    }

    public Job getJobById (String id) throws InvalidDatabaseRequestException {
        //String id is from order id or job id?

        Job job = null;
        if(id.equals (null)){
            throw new InvalidDatabaseRequestException ("id cannot be null");
        }else {
            try{
                Class.forName ("org.postgresql.Driver");
                connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database","postgres","password1234?");
                statement = connection.createStatement ();
                ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"warehouse\".item a JOIN \"warehouse\".orders b ON a.order_id=b.order_id WHERE job_id = "+id+";");
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
                //System.out.println (job.getItems ().get (i).getLocation ());
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
                connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database","postgres","password1234?");
                statement = connection.createStatement ();
                ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"warehouse\".item a JOIN \"warehouse\".orders b ON a.order_id=b.order_id WHERE username = '"+user.getUsername ()+"';");
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
                //System.out.println (job.getItems ().get (i).toString ());
            }
            return  job;
        }
    }

    @Override
    public void remove(String order_id) throws InvalidDatabaseRequestException {

    }


    @Override
    public void addOrder (Order order) throws InvalidDatabaseRequestException {
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database","postgres","password1234?");
            statement = connection.createStatement ();
            User username = order.getCustomer ();
            OrderStatus status = order.getStatus ();
            String uniqueId = order.getUniqueId ();
            String gate = order.getGate ();
            int totalItems = order.totalItemsNumber ();
            String delivery_address = order.getDeliverAddress ();
            ResultSet resultSet = statement.executeQuery ("INSERT INTO \"warehouse\".orders VALUES(" +uniqueId+ "," +totalItems+"," +status + ","+gate + "," + delivery_address + ","+username +")" );
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
        ArrayList<Order> orders = new ArrayList<Order>();
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database","postgres","password1234?");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"warehouse\".Orders a LEFT JOIN \"warehouse\".Users b ON a.username = b.username");
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
        //System.out.println (orders);
        return orders;
    }

    @Override
    public ArrayList<Order> getAllOrders () {
        ArrayList<Order> orders = new ArrayList<Order>();
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database","postgres","password1234?");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"warehouse\".Orders a LEFT JOIN \"warehouse\".Users b ON a.username = b.username");
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
        //System.out.println (orders);
        return orders;
    }

    @Override
    public void setOrderStatus (String orderId, OrderStatus status) throws InvalidDatabaseRequestException {
        if(orderId.equals (null) || status.equals (null)){
            throw new InvalidDatabaseRequestException ("order id and status no bueno te has equivocado");
        }else {
            try {
                Class.forName ("org.postgresql.Driver");
                connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database", "postgres", "password1234?");
                statement = connection.createStatement ();
                //the query changes all the order's status when we choose just one picker, ask someone how to do it
                ResultSet resultSet = statement.executeQuery ("UPDATE \"warehouse\".orders SET status = " + status + " WHERE order_id = " + orderId);
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
                connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/warehouse_database","postgres","password1234?");
                statement = connection.createStatement ();
                ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"warehouse\".orders a LEFT JOIN \"warehouse\".users b ON a.username = b.username  WHERE status = 3;");
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
            //System.out.println (order_driver);
            //System.out.println (order_driver.remove (0));
            return order_driver.remove (0);
        }
    }

    private void executeSingleSQL(String sql)
    {
        if(sql==null || sql.equals("")){
            Logger.getInstance().addLog("sql query empty or null");return; }

        if(connection == null) connect();

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close(); }
        catch (Exception e ) {
            e.printStackTrace();
            Logger.getInstance().addLog("db error executing sql: " + sql); }
    }

    private void executeQuerySQL(String sql)
    {
        //todo query
    }

    private static void managePostgres(String SQL) {
        // make special connection to the database without specifying the database name
        // useful when you need to delete or create a database
        Connection connection = null;
        try{ Class.forName("org.postgresql.Driver"); }catch (ClassNotFoundException e){ e.printStackTrace(); Logger.getInstance().addLog("db cannot init driver");}
        try{ connection = DriverManager.getConnection(URL,USER,PASS); } catch (SQLException e) { e.printStackTrace(); Logger.getInstance().addLog("db connection error");}
        try{ Statement statement = connection.createStatement();
            statement.execute(SQL);
            statement.close();
            connection.close();}
        catch(Exception e ) { e.printStackTrace(); Logger.getInstance().addLog("db error in manage database, SQL: " + SQL); }
    }

    private static String fileToString(String filename){
        // this method reads a file and returns a string
        String returnString;
        try { returnString = Files.readString(Paths.get(Database_Implementation.class.getResource(filename).toURI())); }
        catch (Exception e) { e.printStackTrace(); throw new RuntimeException("cannot find or access file " + filename);}
        Logger.getInstance().addLog("loading SQL file: " + returnString.replace("\n", " ").replace("\r", "").substring(0,150) + "...");
        return returnString;
    }
}
