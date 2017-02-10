import java.sql.*;

public class HiveTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        Connection conn = DriverManager.getConnection("jdbc:hive2://localhost:10000/default", "lasting", "Windboy0");
        Statement stat = conn.createStatement();
        String query = "SELECT * FROM default.t1";
        ResultSet rs = stat.executeQuery(query);
        while (rs.next()){
            System.out.println(rs.getString("id"));
        }
    }
}
