<html>
<%@ page language="java" pageEncoding="utf-8" %>
<head>
    <script language="javascript" type="text/javascript">
        function GetPosition() {
            window.location.hash = "position";
        }
    </script>
</head>
<body onload="GetPosition()">
<iframe src="dataValue.jsp" id="dataValue" style="visibility:hidden; height:0" width="1"></iframe>
<span id="loadContent">data is loading</span>
<a id="position"></a>
</body>
</html>

