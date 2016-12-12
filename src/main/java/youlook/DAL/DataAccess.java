package youlook.DAL;

import java.sql.*;
import java.util.*;
import java.io.*;

import org.ini4j.Ini;
import java.io.FileReader;

import youlook.entities.*;

public class DataAccess
{
  private static final String INI_PATH = "/opt/youlook/config.ini";

  private Connection getConnection() {
    Connection conn = null;

    try {
      Class.forName("com.mysql.jdbc.Driver");
    }
    catch (ClassNotFoundException e) {
        System.out.println("Driver not found: " + e.getMessage());
        return null;
    }

    Ini ini = null;

    try {
      ini = new Ini(new FileReader(INI_PATH));
    } catch(IOException e) {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());

      return null;
    }

    Ini.Section section = ini.get("auth");
    String user = section.get("user");
    String password = section.get("password");

    try {
        conn =
           DriverManager.getConnection(
            "jdbc:mysql://localhost/youlook?" +
            "user=" + user + "&password=" + password);
    } catch (SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }

    return conn;
  }

  public ArrayList<Record> getRecords() {
    Connection conn = getConnection();
    ArrayList<Record> list = new ArrayList<Record>();

    if (conn == null) {
      return list;
    }

    String query = "SELECT * FROM strings";

    try {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(query);

      while (rs.next())
      {
        String str = rs.getString("string");
        int count = rs.getInt("count");

        Record record = new Record(str, count);
        list.add(record);
      }
    } catch(SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }

    return list;
  }

  public void writeRecord(String str, int count) {
    Connection conn = getConnection();

    if (conn == null) return;

    try
    {
      String query =
        "insert into strings (string, count) " +
        "values (?, ?)";

      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setString (1, str);
      preparedStmt.setInt (2, count);

      preparedStmt.execute();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
    }
    finally
    {
      try {
        conn.close();
      } catch(SQLException e) {
        System.err.println("Got an exception!");
        System.err.println(e.getMessage());
      }
    }
  }
}
