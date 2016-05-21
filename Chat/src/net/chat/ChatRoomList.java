package net.chat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ChatRoomList {
    public int countUser(String chatRoom) throws SQLException, ClassNotFoundException {
        BaseConn conn = null;
        try {
            conn = new BaseConn();
            String sql = "SELECT count(*) AS intCount FROM onlineUser WHERE chatRoom=?";
            PreparedStatement ps = conn.preparedStatement(sql);
            ps.setString(1, chatRoom);
            ResultSet rs = conn.executeQuery();
            if (rs.next()) {
                return rs.getInt("intCount");
            } else {
                return 0;
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

    public boolean addOnlineUser(String userName, String chatRoom) throws SQLException, ClassNotFoundException {
        BaseConn conn = null;
        try {
            conn = new BaseConn();
            String sql = "SELECT * FROM onlineUser WHERE nickName=? AND chatRoom=?";
            PreparedStatement ps = conn.preparedStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, chatRoom);
            ResultSet rs = conn.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                sql = "INSERT INTO onlineUser(nickName,chatRoom) VALUES (?,?)";
                ps = conn.preparedStatement(sql);
                ps.setString(1, userName);
                ps.setString(2, chatRoom);
                conn.executeUpdate();
                ps.close();
                SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = cal.format(new java.util.Date());
                sql = "INSERT INTO msgInfo(chatRoom,msgFrom,msgTo,chatTime,msgContent) VALUES (?,?,?,?,?)";
                ps = conn.preparedStatement(sql);
                ps.setString(1, chatRoom);
                ps.setString(2, "system");
                ps.setString(3, "all");
                ps.setString(4, time);
                ps.setString(5, "<font color=green>" + userName + "</font> enter our chatroom.let us welcome him or her");
                conn.executeUpdate();
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
}
