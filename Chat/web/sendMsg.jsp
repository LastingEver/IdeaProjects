<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My JSP 'sendMsg.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
</head>
<jsp:useBean id="chatRoom" class="net.chat.ChatRoom"/>
<jsp:useBean id="message" class="net.chat.Message"/>
<body>
<%
    if (chatRoom.denyUser(session.getAttribute("_USER").toString(),
            session.getAttribute("_CHAT_ROOM").toString())) {
        out.println("<script>parent.alert(\"对不起，你已经被踢出本聊天室\")</script>");
        out.println("<script>parent.parent.document.location.href='chatRoomList.jsp'</script>");
        return;
    }
    request.setCharacterEncoding("GB2312");
    String msgContent = (String) request.getParameter("msg");
    String action = (String) request.getParameter("action");
    String color = (String) request.getParameter("color");
    String msgTo = (String) request.getParameter("msgTo");
    int secret = 0;

    if (msgContent == null || msgContent.equals("")){
        msgContent = "all";
    }

    if (request.getParameter("secret") != null) {
        secret = 1;
    }

    message.setMsgFrom(session.getAttribute("_USER").toString());
    message.setChatAction(action);
    message.setChatRoom(session.getAttribute("_CHAT_ROOM").toString());
    message.setMsgTo(msgTo);
    message.setSecret(secret);
    message.setMsgContent("<font color=" + color + ">" + msgContent + "</font>");
    message.saveToDataBase();
    out.println("<script>parent.document.chatForm.msg.value = \"\"</script>");
    out.println("<script>parent.parent.mainFrame.dataValue.location.reload()</script>");
%>
</body>
</html>

