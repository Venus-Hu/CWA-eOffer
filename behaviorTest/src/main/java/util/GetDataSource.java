package util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by skumar on 9/19/2016.
 */
public class GetDataSource {

    public MongoClient getConnection(String host, Integer port)
    {
        MongoClient mongoClient = new MongoClient(host,port);
        return mongoClient;
    }

    public MongoDatabase getDatabase(String host, Integer port, String dbName)
    {
        MongoDatabase db = getConnection(host,port).getDatabase(dbName);
        return db;
    }

    public void verifyRedShiftConnection(String dbURL,String username,String password) throws SQLException, IOException, Exception {

        Connection conn = null;
        try{
            Class.forName("com.amazon.redshift.jdbc41.Driver");
            System.out.println("Connecting to database...");
            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);
            conn = DriverManager.getConnection(dbURL, props);
            conn.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw new Exception("Redshift connection failed");
        }
    }

    public Connection getRedShiftConnection(String dbURL,String username,String password) throws SQLException, IOException , Exception {
        Connection conn = null;
        try{
            Class.forName("com.amazon.redshift.jdbc41.Driver");
            System.out.println("Connecting to database...");
            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);
            conn = DriverManager.getConnection(dbURL, props);
            conn.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw new Exception("Redshift connection failed");
        }
        return conn;
    }
}
