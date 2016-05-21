<%@include file="chkSession.jsp" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My JSP 'changeRoom.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
</head>
<jsp:useBean id="chatRoomObj" class="net.chat.ChatRoom"/>
<body>
<%
    chatRoomObj.changeRoom(session.getAttribute("_USER").toString(), session.getAttribute("_CHAT_ROOM").toString());
    response.sendRedirect("chatRoomList.jsp");
%> <br>
</body>
</html>
