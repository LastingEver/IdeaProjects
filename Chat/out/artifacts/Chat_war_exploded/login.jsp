<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>chat user login</title>
    <script language="javascript">
        function check() {
            if (document.form1.nickName.value == '') {
                alert("用户昵称不能为空!");
                document.form1.nickName.focus();
                return false;
            } else if (document.form1.userPassword.value == '') {
                alert("登录密码不能为空");
                document.form1.userPassword.focus();
                return false;
            } else {
                return true;
            }
        }
    </script>
</head>
<body>
<form action="chklogin.jsp" method="post" name="form1" onSubmit="return check()">
    <TABLE align="center" cellpadding="0" cellspacing="0" border="0" width="500">
        <tr>
            <td height="100" colspan="2" align="center">
                chat user login
            </td>
        </tr>

        <tr>
            <td width="222" align="right">
                nickname:
            </td>
            <td width="278">
                <input type="text" name="nickName">
            </td>
        </tr>
        <tr>
            <td colspan="2">
            </td>
        </tr>
        <tr>
            <td align="right">
                password:
            </td>
            <td>
                <input type="password" name="userPassword">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" height="30" valign="bottom">
                <input type="submit" value="submit">
                <input type="reset" value="reset">
            </td>
        </tr>

        <tr>
            <td height="50" valign="bottom" colspan="2">
                本聊天系统无须注册，登录成功后系统将自动保存你的用户名和密码。
            </td>
        </tr>
    </TABLE>
</form>
</body>
</html>
