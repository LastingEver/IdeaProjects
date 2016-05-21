<%@ page language="java" pageEncoding="utf-8" %>
<%@include file="chkSession.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>chatroon list</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <jsp:useBean id="chatList" class="net.chat.ChatRoomList"/>
</head>
<body>
welcome:<span style="color:red"><%=session.getAttribute("_USER")%></span> hope you chatting happy here!
<form action="redirectChatRoom.jsp" method="post">
    <table border=0>
        <tr>
            <td style="color:deeppink"><input type="radio" name="chatRoom" value="speak your feeling">speak your feeling
                [<%=chatList.countUser("speak your feeling")%>]people
            </td>
        </tr>
        <tr>
            <td style="color:blue"><input type="radio" name="chatRoom" value="making friends">making friends
                [<%=chatList.countUser("making friends")%>]people
            </td>
        </tr>
        <tr>
            <td style="color:red"><input type="radio" name="chatRoom" value="discussion" checked>discussion
                [<%=chatList.countUser("discussion")%>]people
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="enter"></td>
        </tr>
    </table>
</form>
</body>
</html>
