<%@ page contentType="text/html;charset=gb2312" %>
<jsp:useBean id="chatRoom" scope="page" class="net.chat.ChatRoom"/>
<html>
<head>
    <title>踢人</title>
</head>
<body>
<%
    request.setCharacterEncoding("gb2312");
    String[] userNames = request.getParameterValues("userName");

    if (userNames != null) {
        for (int i = 0; i < userNames.length; i++) {
            chatRoom.kickUser(userNames[i], session.getAttribute("_CHAT_ROOM").toString());
        }
    }

    out.println("<script>window.opener.location.reload();alert('踢出成功');location.href='javascript:window.close()'</script>");
%>
</body>
</html>

