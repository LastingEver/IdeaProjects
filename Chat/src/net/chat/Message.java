package net.chat;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Message {
    private String chatRoom;
    private String msgFrom;
    private String msgTo;
    private String chatAction;
    private String msgContent;
    private int secret;

    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void setMsgFrom(String msgFrom) {
        this.msgFrom = msgFrom;
    }

    public void setMsgTo(String msgTo) {
        this.msgTo = msgTo;
    }

    public void setChatAction(String chatAction) {
        this.chatAction = chatAction;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public void setSecret(int secret) {
        this.secret = secret;
    }

    public boolean saveToDataBase() throws SQLException, ClassNotFoundException {
        BaseConn conn = null;
        try {
            conn = new BaseConn();
            SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = cal.format(new java.util.Date());
            String sql = "INSERT INTO msgInfo(chatRoom,msgFrom,msgTo,chatTime,chatAction,msgContent,secret) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.preparedStatement(sql);
            ps.setString(1, chatRoom);
            ps.setString(2, msgFrom);
            ps.setString(3, msgTo);
            ps.setString(4, time);
            ps.setString(5, chatAction);
            ps.setString(6, msgContent);
            ps.setInt(7, secret);
            conn.executeUpdate();
            sql = "UPDATE onlineUser SET lastChatTime = ? WHERE nickName=? AND chatRoom= ?";
            ps = conn.preparedStatement(sql);
            ps.setString(1, time);
            ps.setString(2, msgFrom);
            ps.setString(3, chatRoom);
            conn.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}