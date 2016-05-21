<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>聊天用户登录</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <%request.setCharacterEncoding("utf-8"); %>
</head>
<jsp:useBean id="check" class="net.chat.CheckLogin"/>
<body>
<%
    String nickName = request.getParameter("nickName");
    System.out.println(nickName);
    String userPassword = request.getParameter("userPassword");
    System.out.println(userPassword);

    String loginMsg = check.checklogin(nickName, userPassword);
    if (loginMsg.equals("SUCCESS_LOGIN")) {
        session.setAttribute("_USER", nickName);
        session.setAttribute("_LOGIN", "_SUCCESS");
        response.sendRedirect("chatRoomList.jsp");
    } else if (loginMsg.equals("WRONG_PASSWORD")) {
        out.println("你输入的用户名或密码错误，请检正后重新输入");
        if (session.getAttribute("_LOGIN") != null)
            session.removeAttribute("_LOGIN");
    } else if (loginMsg.equals("NONE_USER")) {
        check.saveToDataBase(nickName, userPassword);
        session.setAttribute("_USER", nickName);
        session.setAttribute("_LOGIN", "_SUCCESS");
        response.sendRedirect("chatRoomList.jsp");
    }
%>
</body>
</html>
