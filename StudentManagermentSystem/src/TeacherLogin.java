import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TeacherLogin extends JFrame{
    Connection connection;
    Statement mysql;

    JPanel namePanel=new JPanel();
    JLabel nameLabel=new JLabel("姓名");
    JTextField name=new JTextField(13);

    JPanel tIDPanel=new JPanel();
    JLabel tIDLabel=new JLabel("职工编号");
    JTextField tID=new JTextField(13);

    JPanel operation=new JPanel();
    JButton login=new JButton("登陆");
    JButton back=new JButton("返回");

    TeacherLogin(int permission){
        super("老师登陆");
        setLayout(new BorderLayout());
        add(namePanel, BorderLayout.NORTH);
        add(tIDPanel, BorderLayout.CENTER);
        add(operation, BorderLayout.SOUTH);

        namePanel.add(nameLabel);
        namePanel.add(name);

        tIDPanel.add(tIDLabel);
        tIDPanel.add(tID);

        operation.add(login);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("驱动加载成功");
                } catch (ClassNotFoundException exception) {
                    JOptionPane.showMessageDialog(null, "MySQL驱动加载失败！请检查");
                }

                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "windboy");
                    mysql = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    System.out.println("数据库连接成功");
                } catch (SQLException exception) {
                    System.out.println("数据库连接异常！");
                    JOptionPane.showMessageDialog(null, "MySQL数据源连接失败！请检查！");
                }

                if (name.getText().trim().equals("") || tID.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "姓名或职工编号为空！请重新输入!");
                } else {
                    try {
                        ResultSet rs = mysql.executeQuery("SELECT 姓名 FROM tInfo WHERE 职工编号=" + tID.getText().trim());
                        if (rs.next() && name.getText().trim().equals(rs.getString(1))) {
                            JOptionPane.showMessageDialog(null, "登陆成功!");
                            dispose();
                            new TeacherPanel(permission, tID.getText().trim());
                        } else {
                            JOptionPane.showMessageDialog(null, "姓名或职工编号有误！请重新输入!");
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        operation.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "是否返回", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION)
                    dispose();
            }
        });

        setBounds(100, 100, 300, 150);
        setResizable(false);
        setVisible(true);
    }
}