package net.chat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ChatRoom {

    public boolean denyUser(String userName, String chatRoom) throws SQLException, ClassNotFoundException {
        BaseConn conn = null;
        try {
            conn = new BaseConn();
            String sql = "SELECT * FROM onlineUser WHERE nickName=? AND chatRoom=?";
            PreparedStatement ps = conn.preparedStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, chatRoom);
            ResultSet rs = conn.executeQuery();
            if (rs.next()) {
                return false;
            } else {
                return true;
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

    public void logout(String userName) throws SQLException, ClassNotFoundException {
        BaseConn conn = null;
        try {
            conn = new BaseConn();
            String sql = "DELETE FROM onlineUser WHERE nickName=?";
            PreparedStatement ps = conn.preparedStatement(sql);
            ps.setString(1, userName);
            conn.executeUpdate();
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

    public void changeRoom(String userName, String chatRoom) throws SQLException, ClassNotFoundException {
        BaseConn conn = null;
        try {
            conn = new BaseConn();
            String sql = "DELETE FROM onlineUser WHERE nickName=? AND chatRoom=?";
            PreparedStatement ps = conn.preparedStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, chatRoom);
            conn.executeUpdate();
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

    public boolean checkAdmin(String userName) throws SQLException, ClassNotFoundException {
        BaseConn conn = null;
        try {
            conn = new BaseConn();
            String sql = "SELECT role FROM userInfo WHERE nickName=?";
            PreparedStatement ps = conn.preparedStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = conn.executeQuery();
            if (rs.next()) {
                if (rs.getInt("role") == 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
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

    public void kickUser(String userName, String chatRoom) throws SQLException, ClassNotFoundException {
        BaseConn conn = null;
        try {
            conn = new BaseConn();
            String sql = "DELETE FROM onlineUser WHERE nickName=? AND chatRoom = ?";
            PreparedStatement ps = conn.preparedStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, chatRoom);
            conn.executeUpdate();
            SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = cal.format(new java.util.Date());
            sql = "INSERT INTO msgInfo(chatRoom,msgFrom,msgTo,chatTime,msgContent) VALUES (?,?,?,?,?)";
            ps = conn.preparedStatement(sql);
            ps.setString(1, chatRoom);
            ps.setString(2, "system");
            ps.setString(3, "all");
            ps.setString(4, time);
            ps.setString(5, "<font color=red>" + userName + "</font> is kicked out of chatroom by admin !!!");
            conn.executeUpdate();
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

    public void deleteUser(String userName) throws SQLException, ClassNotFoundException {
        BaseConn conn = null;
        try {
            conn = new BaseConn();
            String sql = "DELETE FROM onlineUser WHERE nickName=?";
            PreparedStatement ps = conn.preparedStatement(sql);
            ps.setString(1, userName);
            conn.executeUpdate();
            sql = "DELETE FROM userInfo WHERE nickName=?";
            ps = conn.preparedStatement(sql);
            ps.setString(1, userName);
            conn.executeUpdate();
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
