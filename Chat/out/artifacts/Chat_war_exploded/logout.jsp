<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
    <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
    <META http-equiv="Content-Type" content="text/html; charset=GBK">
    <META http-equiv="Content-Style-Type" content="text/css">
    <TITLE>logout.jsp</TITLE>
</HEAD>
<BODY>
<jsp:useBean id="chatRoomObj" class="net.chat.ChatRoom"/>
<%
    try {
        chatRoomObj.logout(session.getAttribute("_USER").toString());
        session.invalidate();
        response.sendRedirect("login.jsp");
    } catch (Exception ex) {
        ex.printStackTrace();
        out.println("error!");
    }
%>
</BODY>
</HTML>

