<%@ page pageEncoding="UTF-8" %>
<%
    session.setAttribute("_LOGIN", "_SUCCESS");
    if (session.isNew() == true || session.getAttribute("_LOGIN") == null || !session.getAttribute("_LOGIN").equals("_SUCCESS")) {
        out.println("你还没有登录，请先登录后再试");
        return;
    }
%>

