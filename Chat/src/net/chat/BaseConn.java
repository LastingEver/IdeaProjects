package net.chat;

import java.sql.*;

public class BaseConn {
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public BaseConn() throws SQLException, ClassNotFoundException {
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=ChatRoom";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(url, "sa", "windboy");
        stmt = conn.createStatement();
    }

    public PreparedStatement preparedStatement(String sql) throws SQLException {
        ps = conn.prepareStatement(sql);
        return ps;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        rs = null;
        rs = stmt.executeQuery(sql);
        return rs;
    }

    public int executeUpdate(String sql) throws SQLException {
        conn.setAutoCommit(false);
        int r = stmt.executeUpdate(sql);
        conn.commit();
        return r;
    }

    public ResultSet executeQuery() throws SQLException {
        return ps.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        conn.setAutoCommit(false);
        int r = ps.executeUpdate();
        conn.commit();
        return r;
    }

    public boolean closeDB() throws SQLException {
        if (this.rs != null){
            rs.close();
        }

        if (this.stmt != null){
            this.stmt.close();
        }

        if (this.ps != null){
            this.ps.close();
        }

        if (this.conn != null){
            conn.close();
        }

        return true;
    }
}
