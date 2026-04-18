import java.sql.*;
public class PortTest {
  public static void main(String[] args) throws Exception {
    String url = "jdbc:sqlserver://DESKTOP-E2HJ4S6:1433;databaseName=FirstJavaApi;encrypt=false";
    try (Connection c = DriverManager.getConnection(url, "java_app", "JavaPass")) {
      System.out.println("PORT JDBC OK");
    }
  }
}
