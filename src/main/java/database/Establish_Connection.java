package database;


import java.sql.Connection;
import java.sql.DriverManager;


public class Establish_Connection {
    private static final String url ="jdbc:mysql://localhost:3306/javabank";
    private static final String username = "root";
    private static final String passkey ="asnit123";

    private Connection establish()throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url,username,passkey);
    }

    public Connection get_connection()throws Exception {return establish();}
    }
