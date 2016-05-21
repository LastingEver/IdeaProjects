<HTML>
<HEAD>
    <%@ page language="java" pageEncoding="gb2312" %>
    <META http-equiv="Content-Type" content="text/html; charset=GB2312">
    <title>chatroom</title>
    <%@ include file="chkSession.jsp" %>
</head>
<frameset rows="400,*" border="0" framespacing="0" frameborder="NO">
    <frameset cols="1050,*">
        <frame src="show.jsp" name="mainFrame" marginwidth="10" marginheight="10" scrolling="auto" frameborder="0">
        <frame src="userList.jsp" name="userlistFrame" marginwidth="10" marginheight="10" scrolling="no"
               frameborder="0">
    </frameset>
    <frame src="input.jsp?username=<%= session.getAttribute("_USER") %>" name="inputFrame" marginwidth="10"
           marginheight="10" scrolling="no" frameborder="0">
</frameset>
<noframes>
    <body>
    您的浏览器不支持框架，对不起！
    </body>
</noframes>
</html>
