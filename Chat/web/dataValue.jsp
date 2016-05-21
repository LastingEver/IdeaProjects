<html>
<%@include file="chkSession.jsp" %>
<%@ page language="java" import="java.sql.PreparedStatement" pageEncoding="GB2312" %>
<%@ page import="java.sql.ResultSet" %>
<head>
    <script language="javascript">
        function GetData() {
            var timeoutid = setTimeout("window.location.reload()", 2000)
        }
    </script>
</head>
<body onload="GetData()">
<jsp:useBean id="conn" class="net.chat.BaseConn" scope="page"/>
<%
    String msgString = "";
    try {
        String sql = "SELECT * FROM msgInfo WHERE ID IN (SELECT TOP 30 ID FROM msgInfo WHERE chatRoom=? ORDER BY chatTime DESC) ORDER BY chatTime";
        String userName = session.getAttribute("_USER").toString();
        PreparedStatement ps = conn.preparedStatement(sql);
        ps.setString(1, session.getAttribute("_CHAT_ROOM").toString());
        ResultSet rs = conn.executeQuery();

        while (rs.next()) {
            String msgFrom = rs.getString("msgFrom");
            String msgTo = rs.getString("msgTo");
            String action = rs.getString("chatAction");
            String msgContent = rs.getString("msgContent");
            int secret = rs.getInt("secret");
            if (msgFrom.equals("system")) {
                msgString = msgString + "<div>system:" + msgContent + "</div>";
            } else if (secret == 0) {
                if (action == null || action.equals("no")) {
                    msgString = msgString + "<div><font color=blue>" + msgFrom + "</font>对<font color=blue>" + msgTo + "</font></span>说：" + msgContent + "</div>";
                } else {
                    msgString = msgString + "<div><font color=blue>" + msgFrom + "</font>" + action.replaceAll("B", "<font color=blue>" + msgTo + "</font>") + msgContent + "</div>";
                }
            } else if (msgFrom.equals(userName) || msgTo.equals(userName)) {
                if (action == null || action.equals("no")) {
                    msgString = msgString + "<div><font color=red>[悄悄话]</font><font color=blue>" + msgFrom + "</font>对<font color=blue>" + msgTo + "</font></span>说：" + msgContent + "</div>";
                } else {
                    msgString = msgString + "<div><font color=red>[悄悄话]</font><font color=blue>" + msgFrom + "</font>" + action.replaceAll("B", "<font color=blue>" + msgTo + "</font>") + msgContent + "</div>";
                }
            }
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        out.println("系统维护");
    } finally {
        conn.closeDB();
    }
%>
<script language="javascript">
    parent.loadContent.innerHTML = "<%=msgString%>";
    parent.location.hash = "position";
</script>
</body>
</html>

