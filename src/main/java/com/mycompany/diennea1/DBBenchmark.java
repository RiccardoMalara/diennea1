/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.diennea1;

import java.sql.DriverManager;
import java.sql.*;
import java.util.Date;

/**
 *
 * @author Ricky
 */
public class DBBenchmark {
    
    
    public static void main(String[] args) throws ClassNotFoundException
    {        
        System.out.println("Init...");
        
        //normalmente questi valori dovrebbero essere caricati da un file di configurazione
        //specialmente la connectionString
        int commitSize = 20;
        int testSize = 10000;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");        
        String connectionString = "jdbc:sqlserver://ASUS\\SQLEXPRESS;databaseName=diennea;user=alpha;password=alpha1";
        
        try(Connection connection = DriverManager.getConnection(connectionString))
        {   
            connection.setAutoCommit(false);

            cleanDB(connection);        

            insertBench(connection, testSize, commitSize);

            selectBench(connection, testSize, commitSize);
            
            connection.close();
        }
        catch(SQLException ex)
        {
            System.out.println("\nERROR: " + ex.getMessage());
        }
        
        System.out.println("\n...end");
    }
    
    public static void cleanDB(Connection connection) throws SQLException
    {           
        System.out.println("\nCleaning DB");
        PreparedStatement cleanStatement = connection.prepareStatement("delete from Product");        
        cleanStatement.execute();
        connection.commit();
    }
    
    public static void insertBench(Connection connection, int testSize, int commitSize) throws SQLException
    {
        PreparedStatement insertStatement =connection.prepareStatement("insert into Product values(?,?,?)");
        
        long[] insertTimes = new long[testSize];
        
        int i = 0;
        while(i <testSize)
        {   
            Date start;
            Date end;
            
            insertStatement.setInt(1, i);
            insertStatement.setString(2, "Product name " + i);
            insertStatement.setDouble(3, 0.05 * i);
                     
            start = new Date();
            insertStatement.execute();
            end = new Date();
            
            long diff = end.getTime() - start.getTime();
            
            insertTimes[i] = diff;
            
            i++;
            
            if(i%commitSize == 0)
            {
                connection.commit();
            }
        }
        connection.commit();
               
        long min = Long.MAX_VALUE;
        long max = 0;
        long sum = 0;
        long avg = 0;
        
        for(long time : insertTimes)
        {            
            if(time<min) { min = time; }
            if(time>max) {max = time;}
            sum += time;
        }
        
        avg = sum / testSize;            
            
        System.out.println("\nMin Insert time: " + min);
        System.out.println("Max Insert time: " + max);
        System.out.println("Avg Insert time: " + avg);
    }
    
    public static void selectBench(Connection connection, int testSize, int commitSize) throws SQLException
    {
        PreparedStatement selectStatement = connection.prepareStatement("select * from Product where ProductId = ?");
        
        long[] selectTimes = new long[testSize];
        
        int i = 0;
        while(i <testSize)
        {   
            Date start;
            Date end;
            
            selectStatement.setInt(1, i);            
                     
            start = new Date();
            selectStatement.execute();
            end = new Date();
            
            long diff = end.getTime() - start.getTime();
            
            selectTimes[i] = diff;
            
            i++;
            
            if(i%commitSize == 0)
            {
                connection.commit();
            }
        }
        connection.commit();
               
        long min = Long.MAX_VALUE;
        long max = 0;
        long sum = 0;
        long avg = 0;
        
        for(long time : selectTimes)
        {            
            if(time<min) { min = time; }
            if(time>max) {max = time;}
            sum += time;
        }
        
        avg = sum / testSize;            
            
        System.out.println("\nMin Select time: " + min);
        System.out.println("Max Select time: " + max);
        System.out.println("Avg Select time: " + avg);
    }
}
