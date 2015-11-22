import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentInfo extends JFrame{
    Connection connection;
    Statement mysql;

    JPanel 学号=new JPanel();
    JLabel 学号标签=new JLabel("学号");
    JTextField 学号输入=new JTextField(13);

    JPanel 姓名=new JPanel();
    JLabel 姓名标签=new JLabel("姓名");
    JTextField 姓名输入=new JTextField(13);

    JPanel 性别=new JPanel();
    JLabel 性别标签=new JLabel("性别");
    JTextField 性别输入=new JTextField(13);

    JPanel 出生年月=new JPanel();
    JLabel 出生年月标签=new JLabel("出生年月");
    JTextField 出生年月输入=new JTextField(13);

    JPanel 学院=new JPanel();
    JLabel 学院标签=new JLabel("学院");
    JTextField 学院输入=new JTextField(13);

    JPanel 专业=new JPanel();
    JLabel 专业标签=new JLabel("专业");
    JTextField 专业输入=new JTextField(13);

    JPanel operation=new JPanel();
    JButton admit=new JButton("提交");
    JButton exit=new JButton("返回");

    StudentInfo(int permission, String sID){
        super("学生信息");
        setLayout(new FlowLayout());
        add(学号);
        add(姓名);
        add(性别);
        add(出生年月);
        add(学院);
        add(专业);
        add(operation);

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

        学号.add(学号标签);
        学号.add(学号输入);
        学号输入.setEditable(false);
        学号输入.setText(sID);

        姓名.add(姓名标签);
        姓名.add(姓名输入);
        姓名输入.setEditable(false);
        try {
            ResultSet rs=mysql.executeQuery("SELECT 姓名 FROM sInfo WHERE 学号=" + sID);
            rs.next();
            姓名输入.setText(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        性别.add(性别标签);
        性别.add(性别输入);
        try {
            ResultSet rs=mysql.executeQuery("SELECT 性别 FROM sInfo WHERE 学号=" + sID);
            rs.next();
            性别输入.setText(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        出生年月.add(出生年月标签);
        出生年月.add(出生年月输入);
        try {
            ResultSet rs=mysql.executeQuery("SELECT 出生年月 FROM sInfo WHERE 学号=" + sID);
            rs.next();
            出生年月输入.setText(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        学院.add(学院标签);
        学院.add(学院输入);
        try {
            ResultSet rs=mysql.executeQuery("SELECT 学院 FROM sInfo WHERE 学号=" + sID);
            rs.next();
            学院输入.setText(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        专业.add(专业标签);
        专业.add(专业输入);
        try {
            ResultSet rs=mysql.executeQuery("SELECT 专业 FROM sInfo WHERE 学号=" + sID);
            rs.next();
            专业输入.setText(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        operation.add(admit);
        operation.add(exit);
        admit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (性别输入.getText().trim().equals("") || 出生年月输入.getText().trim().equals("") || 学院输入.getText().trim().equals("") || 专业输入.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "个人信息不得为空！请重新输入!");
                } else {
                    try {
                        mysql.executeUpdate("UPDATE sInfo SET 性别=" + "'" + 性别输入.getText().trim() + "'" + ",出生年月=" + 出生年月输入.getText().trim() + ",学院=" + "'" + 学院输入.getText().trim() + "'" + ",专业=" + "'" + 专业输入.getText().trim() + "'" + " WHERE 学号=" + sID);
                        JOptionPane.showMessageDialog(null, "个人信息修改成功!");
                        dispose();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "是否返回", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION)
                    dispose();
            }
        });

        setSize(250, 350);
        setResizable(false);
        setVisible(true);
    }
}
