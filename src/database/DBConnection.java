package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class defines the methods necessary to connect the application to the database.
 */

public abstract class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//localhost:3306/";
    private static final String dbName = "client_schedule";

    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    private static final String MYSQLJBCDriver = "com.mysql.jdbc.Driver";

    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    private static Connection conn = null;

    /**
     * The startConnection method sets the connection information and allows the program to connect to the
     * database.
     *
     * @return conn The connection to the database.
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection Successful");
        }catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * The getConnection method allows the program to connect to the database without having to reopen the connection
     * each time.
     *
     * @return conn The connection to the database.
     */
    public static Connection getConnection(){
        return conn;
    }

    /**
     * The closeConnection method closes the connection to the database.
     */
    public static void closeConnection(){
        try{
            conn.close();
            System.out.println("Connection Closed");
        } catch (Exception e){

        }
    }
}
