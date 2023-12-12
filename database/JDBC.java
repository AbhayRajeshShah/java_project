package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import struct.DNode;


public class JDBC {
    String url = "jdbc:mysql://localhost:3306/contact";
    String username = "root";
    String password = "root";
    public static Connection connection;
    DNode head=null;
    public JDBC(){
        connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Boolean delete(){
        try{
            String query = "DELETE FROM contacts" ;
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public void update(DNode data){
        DNode temp=data;
        Boolean deleted=delete();
        if(deleted){
            try {
            String query = "INSERT INTO contacts (name, number, gmail) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            while(temp!=null){
                preparedStatement.setString(1, temp.name);
                preparedStatement.setString(2, temp.number);
                preparedStatement.setString(3, temp.gmail);
                preparedStatement.addBatch();
                temp=temp.next;
            }          
            int[] rowsAffected = preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }else{
            System.out.println("Could not update documents");
        }      
    }

    public void insert(DNode data){
        try {
            String query = "INSERT INTO contacts (name, number, gmail) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, data.name);
            preparedStatement.setString(2, data.number);
            preparedStatement.setString(3, data.gmail);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully!");
            } else {
                System.out.println("Failed to insert data!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
