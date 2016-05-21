package net.chat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class CheckLogin {
    public String checklogin(String userName, String userPassword) throws SQLException, ClassNotFoundException {
        BaseConn conn = null;
        try {
            conn = new BaseConn();
            String sql = "SELECT * FROM userInfo WHERE nickName=?";
            PreparedStatement ps = conn.preparedStatement(sql);
            ps.setString(1, userName);

            ResultSet rs = conn.executeQuery();
            if (rs.next()) {
                if (rs.getString("userPassword").equals(userPassword)) {
                    SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = cal.format(new java.util.Date());
                    sql = "UPDATE userInfo SET lastLoginTime=? WHERE nickName=?";
                    ps = conn.preparedStatement(sql);
                    ps.setString(1, time);
                    ps.setString(2, userName);
                    conn.executeUpdate();
                    return "SUCCESS_LOGIN";
                } else {
                    return "WRONG_PASSWORD";
                }
            } else {
                return "NONE_USER";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            conn.closeDB();
        }
    }

    public boolean saveToDataBase(String userName, String userPassword) throws SQLException, ClassNotFoundException {
        BaseConn conn = null;
        try {
            conn = new BaseConn();
            SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = cal.format(new java.util.Date());
            String sql = "INSERT INTO userInfo(nickName,userPassword,lastLoginTime,role) VALUES (?,?,?,?)";
            PreparedStatement ps = conn.preparedStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, userPassword);
            ps.setString(3, time);
            ps.setInt(4, 0);
            conn.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            conn.closeDB();
        }
    }
}
