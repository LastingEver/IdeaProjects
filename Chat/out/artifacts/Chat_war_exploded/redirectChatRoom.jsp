<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My JSP 'redirectChatRoom.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
</head>
<jsp:useBean id="chatList" class="net.chat.ChatRoomList"/>
<body>
<%
    request.setCharacterEncoding("GB2312");
    String chatRoom = request.getParameter("chatRoom");
    session.setAttribute("_CHAT_ROOM", chatRoom);
    chatList.addOnlineUser(session.getAttribute("_USER").toString(), chatRoom);
    response.sendRedirect("chatRoom.jsp");
%>
</body>
</html>
