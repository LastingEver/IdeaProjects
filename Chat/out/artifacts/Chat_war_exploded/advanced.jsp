<%@ page language="java" import="java.sql.ResultSet" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>高级管理页面</title>
    <script language="javascript">
        function kick() {
            document.form1.action = "kick.jsp";
            document.form1.submit();
        }

        function del() {
            document.form1.action = "delete.jsp";
            document.form1.submit();
        }

        function list() {
            document.location.href = "advanced.jsp?list=true";
        }
    </script>
    <jsp:useBean id="chatRoom" class="net.chat.ChatRoom"/>
    <jsp:useBean id="conn" class="net.chat.BaseConn"/>
</head>
<body>
<%
    try {
        if (chatRoom.checkAdmin(session.getAttribute("_USER").toString())) {
%>
<form method="post" name="form1" target="_blank">
    <table>
        <%
            if (request.getParameter("list") == null) {
        %>
        <tr>
            <td>
                用户
            </td>
            <td>
                最后发言时间
            </td>
            <td>
                最近登录时间
            </td>
        </tr>
        <%
            String sql = "SELECT nickName,lastChatTime,lastLoginTime FROM onlineUser WHERE chatRoom='" + session.getAttribute("_CHAT_ROOM").toString() + "'";
            ResultSet rs = conn.executeQuery(sql);
            while (rs.next()) {
        %>
        <tr>
            <td>
                <input type="checkbox" name="userName" value="<%=rs.getString("nickName")%>">
                <%=rs.getString("nickName")%>
            </td>
            <td>
                <%=rs.getString("lastChatTime") != null ? rs.getString("lastChatTime") : "没有发言"%>
            </td>
            <td>
                <%=rs.getString("lastLoginTime")%>
            </td>
        </tr>
        <%
            }
        %>
        <tr>
            <td>
                <input type="button" value="踢出聊天室" onClick="kick()">
                <input type="button" value="删除用户" onclick="del()">
                <input type="button" value="列出所有用户列表" onclick="list()">
            </td>
        </tr>
        <%
        } else {
        %>
        <tr>
            <td>
                用户
            <td>
                最近登录时间
            </td>
        </tr>
        <%
            String sql = "SELECT nickName,lastLoginTime FROM userInfo ORDER BY lastLoginTime";
            ResultSet rs = conn.executeQuery(sql);
            while (rs.next()) {
                String nickName = rs.getString("nickName");
                if (!nickName.equals("系统公告")) {
        %>
        <tr>
            <td>
                <input type="checkbox" name="userName" value="<%=rs.getString("nickName")%>">
                <%=rs.getString("nickName")%>
            </td>
            <td>
                <%=rs.getString("lastLoginTime")%>
            </td>
        </tr>
        <%
                }
            }
        %>
        <tr>
            <td>
                <input type="button" value="删除用户" onclick="del()">
                <input type="button" value="列出所有用户列表" onclick="list()">
            </td>
        </tr>
    </table>
</form>
<%
            }
        } else {
            out.println("<script>alert('你不是管理，不能操作此项');location.href='javascript:window.close()'</script>");
        }
    } catch (Exception ex) {
        out.println("系统维护");
    } finally {
        conn.closeDB();
    }
%>
<br>
</body>
</html>
