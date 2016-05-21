<%@page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="chatRoom" scope="page" class="net.chat.ChatRoom"/>
<html>
<head>
    <title>用户升级</title>
</head>
<body>
<%
    request.setCharacterEncoding("gb2312");
    String[] userNames = request.getParameterValues("userName");

    if (userNames != null) {
        for (int i = 0; i < userNames.length; i++) {
            chatRoom.deleteUser(userNames[i]);
        }
    }

    out.println("<script>window.opener.location.reload();alert('删除成功');location.href='javascript:window.close()'</script>");
%>
</body>
</html>

