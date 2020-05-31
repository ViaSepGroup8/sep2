package database;

import logger.Logger;
import model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

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

        String chars = "BCDEFGHI";
        Random rnd = new Random();
        //load some fake data
        Logger.getInstance().addLog("db>> loading fake data");
        String StringOfItems = "error";
        try
        {
            StringOfItems = Files.readString(Paths.get("Server/src/database/fake-data.txt"));
        }
        catch (IOException e)
        {
        }
        String[] split = StringOfItems.replaceAll("\\s+", "").split(",");
        for (int i = 0; i < split.length; i++)
        {
            String name = split[i];
            Location  location = new Location(rnd.nextInt(10)+40 + "" + chars.charAt(rnd.nextInt(chars.length())), 1 + rnd.nextInt(50), 1 + rnd.nextInt(3));
            int price = rnd.nextInt(100);
            String sql = "INSERT INTO products VALUES(DEFAULT, '" + name+ "', "+price+",'"+location.databaseFormat()+"');";
            executeSingleSQL(sql);
        }
    }

    @Override
    public void deleteAllData() {
        // kick all users first, trying to drop a database while users are connected would result in error
        managePostgres("SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '" + DB +"' AND pid <> pg_backend_pid();");
        // drop the database
        managePostgres("DROP DATABASE IF EXISTS "+DB+";");
    }

    @Override
    public void close() {
        try { if(statement!=null) statement.close();
              if(connection!=null) connection.close(); Logger.getInstance().addLog("db disconnected"); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public ArrayList<Item> getAllWarehouseProducts() {
        ArrayList<Item> items= new ArrayList<Item>();
        ResultSet resultSet = executeSingleQuerySQL("SELECT * FROM \"warehouse\".products");
        try {
            while (resultSet.next()) {
                int uniqueId = resultSet.getInt("product_id");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                Location location = new Location(resultSet.getString("location"));
                Item item = new Item(uniqueId, description, 0, location, price);
                items.add(item);
            }resultSet.close();
        }catch (SQLException e) { e.printStackTrace(); }
        return items;
    }

    @Override
    public User getUser (String username, String password) {
        User user = new User();
        ResultSet resultSet = executeSingleQuerySQL("SELECT * FROM \"warehouse\".Users  WHERE(username = '" + username +"' AND password = '" + password +"')");
        try {
            while (resultSet.next()) {
                String username1 = resultSet.getString ("username");
                String fullName = resultSet.getString ("fullname");
                int userType = resultSet.getInt ("role");
                user = new User (username1,fullName,UserType.values ()[userType]);
            }resultSet.close();
        }catch (SQLException e) { e.printStackTrace(); }
        return user;
    }

    @Override
    public ArrayList<User> getAllUsers () {
        ArrayList<User> users = new ArrayList<User>();
        try{
            ResultSet resultSet = executeSingleQuerySQL("SELECT * FROM \"warehouse\".users");
            while (resultSet.next ()) {
                String username = resultSet.getString ("username");
                String fullName = resultSet.getString ("fullname");
                int roleNumber = resultSet.getInt ("role");
                User user = new User (username, fullName, UserType.values ()[roleNumber]);
                users.add (user);
            }resultSet.close();
        }catch (SQLException e) { e.printStackTrace(); }
        return users;
    }

    @Override
    public void addUser(String username, String fullName, UserType userType, String password) {
        executeSingleSQL("INSERT INTO warehouse.users VALUES('"+username+"', '"+fullName+"', '" +password+"', '"+userType.ordinal()+"');");
    }

    @Override
    public void removeUser(String username) {
        executeSingleSQL("DELETE FROM warehouse.users WHERE username = '" + username + "';");}

    @Override
    public String createJob(String order_id) {
        ResultSet resultSet = executeSingleQuerySQL("INSERT INTO warehouse.jobs VALUES( DEFAULT, " + order_id + ", NULL,'false') RETURNING job_id;");
        try {
            resultSet.next();
            return resultSet.getString("job_id");
        } catch (SQLException e) { e.printStackTrace(); throw new RuntimeException("cannot create jobs");}
    }

    @Override
    public void orderAddItem(int item_id, int quantity, String order_id, String job_id) {
        executeSingleSQL("INSERT INTO warehouse.items VALUES("+item_id+", " + order_id + ", " + job_id + ", " + quantity +");");
    }


    @Override
    public void completeJob (User user, Job job) {
        //todo also check for the job id with "and" for security
        executeSingleSQL("UPDATE warehouse.jobs SET completed = true WHERE picker = '" + user.getUsername() +"';");
        Logger.getInstance().addLog("picker " + user.getUsername() + " completed job " + job.getJobId() + ".");
        //todo this should change status of job and check it is the last unfinshed job in order, if that's true it should also change the status of the order
        //the query changes all the order's status when we choose just one picker, ask someone how to do it
        //ResultSet resultSet = statement.executeQuery ("UPDATE \"warehouse\".orders SET status = 4 FROM \"warehouse\".orders a JOIN \"warehouse\".jobs b ON a.order_id = b.order_id WHERE b.username_picker = '" +user.getUsername ()+"' ;");
    }

    @Override
    public Job getNewJob (User user)
    {
        Job job = null;
        try {
            ResultSet resultSet = executeSingleQuerySQL("SELECT * FROM warehouse.items a JOIN warehouse.jobs b ON a.job_id = b.job_id WHERE picker IS null LIMIT 1;");
            if (resultSet.next()) {
                String jobId = resultSet.getString("job_id");
                String orderId = resultSet.getString("order_id");
                resultSet.close();
                executeSingleSQL("UPDATE warehouse.jobs SET picker = '" + user.getUsername() + "' WHERE job_id = ' " + jobId +"';");
                resultSet = executeSingleQuerySQL("SELECT * FROM warehouse.items a JOIN warehouse.products b ON a.product_id = b.product_id WHERE job_id = " + jobId + ";");
                ArrayList<Item> items = new ArrayList<Item>();
                while (resultSet.next()) {
                    int quantity = resultSet.getInt("quantity");
                    int uniqueId = resultSet.getInt("product_id");
                    String description = resultSet.getString("description");
                    int price = resultSet.getInt("price");
                    Location location = new Location(resultSet.getString("location"));
                    Item item = new Item(uniqueId, description, quantity, location, price);
                    items.add(item);
                }
                resultSet.close();
                job = new Job(jobId, orderId, items);
            }
        }catch (SQLException e) {e.printStackTrace();}
        Logger.getInstance().addLog("returning new job: " + job);
        return job;
    }

    @Override
    public void removeOrder(String order_id) throws InvalidDatabaseRequestException {
        executeSingleSQL("DELETE FROM warehouse.orders WHERE order_id = '" + order_id + "';");
    }


    @Override
    public String addOrder (Order order) throws InvalidDatabaseRequestException {
        if(order==null || order.totalItemsNumber() <= 0) throw new InvalidDatabaseRequestException("order with 0 items");
        String order_id = order.getUniqueId();
        int item_count = order.totalItemsNumber();
        int status = order.getStatus().ordinal();
        String gate = order.getGate();
        String delivery_address = order.getDeliverAddress();
        String customer = order.getCustomer().getUsername();
        String driver = order.getCustomer().getUsername();
        Logger.getInstance().addLog("user " + order.getCustomer().getUsername()+ " made a new order");

        ResultSet resultSet = executeSingleQuerySQL("INSERT INTO warehouse.orders VALUES(DEFAULT,"+item_count+","+status+", null, '"+delivery_address+"', '"+customer+"', null) RETURNING order_id;");
        try {
            resultSet.next();
            return resultSet.getString("order_id");
        } catch (SQLException e) { e.printStackTrace(); throw new RuntimeException("cannot create jobs");}
    }

    @Override public Order getOrderByOrderId(String order_id) throws InvalidDatabaseRequestException
    {
        Order order = null;
        ResultSet resultSet = executeSingleQuerySQL("SELECT * FROM warehouse.orders a LEFT JOIN warehouse.users b ON a.customer = b.username WHERE a.order_id = '" + order_id + "';");
        try {
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String fullName = resultSet.getString("fullname");
                int roleNumber = resultSet.getInt("role");
                User user = new User(username, fullName, UserType.values()[roleNumber]);
                int status = resultSet.getInt("status");
                String uniqueId = "" + resultSet.getInt("order_id");
                String gate = resultSet.getString("gate");
                String delivery_address = resultSet.getString("delivery_address");
                // SUB QUERY
                ResultSet SubResultSet = executeMultipleQuerySQL("SELECT * FROM warehouse.items a JOIN warehouse.products b ON a.product_id = b.product_id WHERE order_id = " + uniqueId + ";");
                ArrayList<Item> items = new ArrayList<Item>();
                while (SubResultSet.next()) {
                    int quantity = SubResultSet.getInt("quantity");
                    int product_id = SubResultSet.getInt("product_id");
                    String description = SubResultSet.getString("description");
                    int price = SubResultSet.getInt("price");
                    Location location = new Location(SubResultSet.getString("location"));
                    Item item = new Item(product_id, description, quantity, location, price);
                    items.add(item);
                }
                SubResultSet.close();
                //End Of SUB QUERY
                order = new Order(user, OrderStatus.values()[status], uniqueId, items, gate, delivery_address);
            }
        }catch (SQLException e) {e.printStackTrace(); }
        if (order == null) throw new InvalidDatabaseRequestException();
        return order;
    }

    @Override
    public ArrayList<Order> getOrdersByUser(User customer) {
        ArrayList<Order> orders = new ArrayList<Order>();
        ResultSet resultSet = executeSingleQuerySQL("SELECT * FROM warehouse.orders a LEFT JOIN warehouse.users b ON a.customer = b.username WHERE a.customer = '" + customer.getUsername() + "';");
        try {
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String fullName = resultSet.getString("fullname");
                int roleNumber = resultSet.getInt("role");
                User user = new User(username, fullName, UserType.values()[roleNumber]);
                int status = resultSet.getInt("status");
                String uniqueId = "" + resultSet.getInt("order_id");
                String gate = resultSet.getString("gate");
                String delivery_address = resultSet.getString("delivery_address");
                // SUB QUERY
                ResultSet SubResultSet = executeMultipleQuerySQL("SELECT * FROM warehouse.items a JOIN warehouse.products b ON a.product_id = b.product_id WHERE order_id = " + uniqueId + ";");
                ArrayList<Item> items = new ArrayList<Item>();
                while (SubResultSet.next()) {
                    int quantity = SubResultSet.getInt("quantity");
                    int product_id = SubResultSet.getInt("product_id");
                    String description = SubResultSet.getString("description");
                    int price = SubResultSet.getInt("price");
                    Location location = new Location(SubResultSet.getString("location"));
                    Item item = new Item(product_id, description, quantity, location, price);
                    items.add(item);
                }
                SubResultSet.close();
                //
                Order order = new Order(user, OrderStatus.values()[status], uniqueId, items, gate, delivery_address);
                orders.add(order);
            }
        }catch (SQLException e) {e.printStackTrace(); }
        Logger.getInstance().addLog("user " + customer.getUsername() + " asked for orders: " + orders);
        return orders;
    }

    @Override
    public ArrayList<Order> getAllOrders () {
        ArrayList<Order> orders = new ArrayList<Order>();
//        ResultSet resultSet = executeSingleQuerySQL("SELECT * FROM warehouse.orders a LEFT JOIN warehouse.users b ON a.customer = b.username;");
//        try { while (resultSet.next()) {
//            String username = resultSet.getString("username");
//            String fullName = resultSet.getString("fullname");
//            int roleNumber = resultSet.getInt("role");
//            User user = new User(username, fullName, UserType.values()[roleNumber]);
//            int status = resultSet.getInt("status");
//            String uniqueId = "" + resultSet.getInt("order_id");
//            String gate = resultSet.getString("gate");
//            String delivery_address = resultSet.getString("delivery_address");
//            Order order = new Order(user, OrderStatus.values()[status], uniqueId, gate, delivery_address);
//            orders.add(order);}
//        }catch (SQLException e) {e.printStackTrace(); }
        return orders;
    }

    @Override
    public void setOrderStatus (String orderId, OrderStatus status) throws InvalidDatabaseRequestException {
        executeSingleSQL("UPDATE warehouse.orders SET status = " + status.ordinal() + " WHERE order_id = " + orderId);
    }

    @Override
    public Order getNewDriverOrder(User driver) throws InvalidDatabaseRequestException {
        Order order = null;
        ResultSet resultSet = executeSingleQuerySQL ("SELECT * FROM warehouse.orders a LEFT JOIN warehouse.users b ON a.customer = b.username WHERE status = 3 AND driver is NULL Limit 1;");
        try{ if (resultSet.next ()) {
            String username = resultSet.getString("customer");
            String fullName = resultSet.getString("fullname");
            int roleNumber = resultSet.getInt("role");
            User user = new User(username, fullName, UserType.values()[roleNumber]);
            int status = resultSet.getInt("status");
            String uniqueId = "" + resultSet.getInt("order_id");
            String gate = resultSet.getString("gate");
            String delivery_address = resultSet.getString("delivery_address");
            order = new Order(user, OrderStatus.values()[status], uniqueId, gate, delivery_address);
        }}catch (SQLException e) { e.printStackTrace();}
        Logger.getInstance().addLog("driver order: " + order);
        return order;
    }

    private void executeSingleSQL(String sql)
    {
        Logger.getInstance().addLog("SQL:" + sql);
        if(connection == null) connect();
        if(statement == null) { try { statement = connection.createStatement(); } catch (SQLException e) { e.printStackTrace(); }}
        try{ statement.executeUpdate(sql); } catch (SQLException e) { e.printStackTrace(); }
    }

    private ResultSet executeSingleQuerySQL(String sql)
    {
        return executeMultipleQuerySQL(sql);
          //todo this should be resolved somehow
//        Logger.getInstance().addLog("SQL:" + sql);
//        ResultSet resultSet = null;
//        if(connection == null) connect();
//        if(statement == null) { try { statement = connection.createStatement(); } catch (SQLException e) { e.printStackTrace(); }}
//        try { resultSet = statement.executeQuery(sql); } catch (SQLException e) { e.printStackTrace(); }
//        return resultSet;
    }

    private ResultSet executeMultipleQuerySQL(String sql)
    {
        Logger.getInstance().addLog("SQL:" + sql);
        ResultSet resultSet = null;
        Statement statement = null;
        if(connection == null) connect();
        try { statement = connection.createStatement(); } catch (SQLException throwables) { throwables.printStackTrace(); }
        try { resultSet = statement.executeQuery(sql); } catch (SQLException e) { e.printStackTrace(); }
        return resultSet;
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
