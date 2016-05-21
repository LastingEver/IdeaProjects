<HTML>
<HEAD>
    <%@ page language="java" contentType="text/html; charset=GB2312"
             pageEncoding="GB2312"
    %>
    <%@include file="chkSession.jsp" %>
    <META http-equiv="Content-Type" content="text/html; charset=GB2312">
    <%@page import="java.sql.ResultSet" %>
    <jsp:useBean id="conn" class="net.chat.BaseConn" scope="page"/>
    <jsp:useBean id="list" class="net.chat.ChatRoomList" scope="page"/>
    <jsp:useBean id="chatRoomObj" class="net.chat.ChatRoom"/>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <META http-equiv="refresh" content="8">
    <script language="JavaScript" type="">
        function PerformSubmit(user) {
            parent.inputFrame.chatForm.msgTo.value = user;
            return false;
        }
    </script>
</HEAD>
<BODY>
<%
    if (chatRoomObj.denyUser(session.getAttribute("_USER").toString(), session.getAttribute("_CHAT_ROOM").toString())) {
        out.println("<script>alert(\"对不起，你已经被踢出本聊天室\")</script>");
        out.println("<script>parent.document.location.href='chatRoomList.jsp'</script>");
        return;
    }

    String userName = session.getAttribute("_USER").toString();

    if (userName == null) {
        out.println("<script>alert(\"你已经和聊天室断开\")</script>");
        out.println("<script>parent.document.location.href='login.jsp'</script>");
        return;
    }

    String chatRoom = (String) session.getAttribute("_CHAT_ROOM").toString();
%>
<font color="#0000ff">online user</font>
[<font color="#ff0000"><%=list.countUser(chatRoom)%>
</font>]
<a href="#" onClick=PerformSubmit("all")>all</a><br>
<%
    try {
        String sql = "SELECT * FROM onlineUser WHERE chatRoom='" + session.getAttribute("_CHAT_ROOM") + "'";
        ResultSet rs = conn.executeQuery(sql);
        while (rs.next()) {
%>
[<a href="#" class="l1" onClick=PerformSubmit("<%=rs.getString("nickName")%>")>
    <%=rs.getString("nickName")%>
</a>]<br>
<%
        }
    } catch (Exception ex) {
        out.println("数据库维护");
    }
%>
</BODY>
</HTML>
