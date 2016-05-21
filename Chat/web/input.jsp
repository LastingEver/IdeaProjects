<HTML>
<HEAD>
    <%@include file="chkSession.jsp" %>
    <%@ page language="java" contentType="text/html; charset=GB2312"
             pageEncoding="GB2312"
    %>
    <META http-equiv="Content-Type" content="text/html; charset=GB2312">
    <link rel="stylesheet" href="chat.css">
    <script language="JavaScript" type="">
        function checkForm() {
            if (document.chatForm.msg.value == "") {
                alert("���Բ���Ϊ��!");
                document.chatForm.msg.focus();
                return false;
            } else {
                document.chatForm.msg.focus();
                return true;
            }
        }

        function userLogout(tag) {
            if (tag == 1) {
                top.window.location = "logout.jsp";
                return true;
            } else if (tag == 2) {
                top.window.location = "login.jsp";
                return true;
            }
        }

        function userChangeRoom() {
            document.body.onunload = "";
            top.window.location = "changeRoom.jsp";
        }

        function openWindow(url) {
            var newWin = window.open(url, "", "toolbar=no,status=no,scrollbars=yes,menubar=no,width=450,height=320");
            return false;
        }
    </script>
</HEAD>
<BODY onUnload="userLogout(1)">
<FORM name="chatForm" method="post" action="sendMsg.jsp" target="theIframe">
    <table border="0" width="100%" align="center" cellspacing="0" cellpadding="0">
        <tr valign="top">
            <td>
                <input type="text" name="msgTo" size="8" style="font-size:9pt">
                ����
                <select name="action" size="1" style="font-size:9pt">
                    <option value="no" selected>˵��</option>
                    <option value="�Ѻõ�����B����˵��">�ʺ�</option>
                    <option value="��B����ص��ͷ,˵��">��ͷ</option>
                    <option value="��B���ĵ�����һЦ��˵��">����</option>
                    <option value="���²������ĵغǺ�Ц�Ŷ�B˵��">����</option>
                    <option value="һ���Ļ�Ц����������ش�����B��˵��">��Ц</option>
                    <option value="��ס��ˮ��ǿ����һ˿Ц�ݶ�B˵��">��Ц</option>
                    <option value="����Bһ�ۣ�ʮ�ֲ�м��˵��">����</option>
                    <option value="�������B�Ķ�ͷ������һ�£������˵��">����</option>
                    <option value="���ҵ�ӵ����B������͸����������˵��">ӵ��</option>
                    <option value="����������������B˵��">����</option>
                    <option value="��������ۿ����ת���������ĵض�B˵��">����</option>
                    <option value="�ߺ�������š�����ӱ�����B˵��">����</option>
                    <option value="����ȫ����������������B��˵��">��ŭ</option>
                    <option value="��B�����֣��м���˸����ǣ�˵��">����</option>
                    <option value="�����ߺ�����ض�B˵��">����</option>
                    <option value="�˷ܵò����ˣ�����B�������裬˵��">�˷�</option>
                    <option value="�鲻�Խ���§��B������������ߴ�������˵��">����</option>
                    <option value="�����������B������д����ί����˵��">ί��</option>
                    <option value="����ֱ��ţ������ܻ��ض�B˵��">�ż�</option>
                    <option value="����Ȫӿ������B������������������˵��">����</option>
                    <option value="����üͷ��ת����ȴ��͵��Bһ�ۣ���������˵��">����
                    </option>
                    <option value="���𶷼��ۿ���Bһ���Ц˵��">��Ц</option>
                    <option value="ͻȻԾ��ɽ�����Bƨ�ɣ�˵��">�ɽ�</option>
                    <option value="���ҿ������B����������˵��">����</option>
                    <option value="��BƤЦ�ⲻЦ��˵��">��Ц</option>
                </select>

                <input type="checkbox" name="secret" value="yes"> ���Ļ�
                �� <a href="advanced.jsp" onclick="return openWindow(this.href);">�߼�����
            </a><br>
                <input type="text" name="msg" size=50 style="font-size:9pt">
                <select name="color" size="1" style="font-size:9pt">
                    <option style="COLOR: #000000" value="#000000">��ɫ</option>
                    <option style="COLOR: #7ec0ee" value="#7ec0ee">����</option>
                    <option style="COLOR: #0088ff" value="#0088ff">����</option>
                    <option style="COLOR: #0000ff" value="#0000ff">����</option>
                    <option style="COLOR: #000088" value="#000088">����</option>
                    <option style="COLOR: #8800ff" value="#8800ff">����</option>
                    <option style="COLOR: #ab82ff" value="#AB82FF">��ɫ</option>
                    <option style="COLOR: #ff88ff" value="#ff88ff">�Ͻ�</option>
                    <option style="COLOR: #ff00ff" value="#ff00ff">����</option>
                    <option style="COLOR: #ff0088" value="#ff0088">õ��</option>
                    <option style="COLOR: #ff0000" value="#ff0000">���</option>
                    <option style="COLOR: #f4a460" value="#f4a460">��ɫ</option>
                    <option style="COLOR: #888800" value="#888800">����</option>
                    <option style="COLOR: #888888" value="#888888">����</option>
                    <option style="COLOR: #90e090" value="#90E090">��ɫ</option>
                    <option style="COLOR: #008800" value="#008800">���</option>
                    <option style="COLOR: #008888" value="#008888">����</option>
                </select>
                <input type="submit" name="Submit" value="send" style="font-size:9pt"
                       onclick="return checkForm();">
                <input type="button" name="logout" value="left" style="font-size:9pt"
                       onclick="return userLogout(2);">
                <input type="button" name="changeRoom" value="change room" style="font-size:9pt"
                       onclick="return userChangeRoom();">
            </td>
            <td>
            </td>
        </tr>
    </table>
    <iframe id="theIframe" name="theIframe" width="0" height="0"></iframe>
</FORM>
</BODY>
</HTML>

