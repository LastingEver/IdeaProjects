import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TeacherInfo extends JFrame {
    Connection connection;
    Statement mysql;

    JPanel 职工编号 = new JPanel();
    JLabel 职工编号标签 = new JLabel("职工编号");
    JTextField 职工编号输入 = new JTextField(13);

    JPanel 姓名 = new JPanel();
    JLabel 姓名标签 = new JLabel("姓名");
    JTextField 姓名输入 = new JTextField(13);

    JPanel 学院 = new JPanel();
    JLabel 学院标签 = new JLabel("学院");
    JTextField 学院输入 = new JTextField(13);

    JPanel 系 = new JPanel();
    JLabel 系标签 = new JLabel("系");
    JTextField 系输入 = new JTextField(13);

    JPanel operation = new JPanel();
    JButton admit = new JButton("提交");
    JButton exit = new JButton("返回");

    TeacherInfo(int permission, String tID) {
        super("老师信息");
        setLayout(new FlowLayout());
        add(职工编号);
        add(姓名);
        add(学院);
        add(系);
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

        职工编号.add(职工编号标签);
        职工编号.add(职工编号输入);
        职工编号输入.setEditable(false);
        职工编号输入.setText(tID);

        姓名.add(姓名标签);
        姓名.add(姓名输入);
        姓名输入.setEditable(false);
        try {
            ResultSet rs=mysql.executeQuery("SELECT 姓名 FROM tInfo WHERE 职工编号=" + tID);
            rs.next();
            姓名输入.setText(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        学院.add(学院标签);
        学院.add(学院输入);
        try {
            ResultSet rs=mysql.executeQuery("SELECT 学院 FROM tInfo WHERE 职工编号=" + tID);
            rs.next();
            学院输入.setText(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        系.add(系标签);
        系.add(系输入);
        try {
            ResultSet rs=mysql.executeQuery("SELECT 系 FROM tInfo WHERE 职工编号=" + tID);
            rs.next();
            系输入.setText(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        operation.add(admit);
        operation.add(exit);
        admit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (学院输入.getText().trim().equals("") || 系输入.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "个人信息不得为空！请重新输入!");
                } else {
                    try {
                        mysql.executeUpdate("UPDATE tInfo SET 学院=" + "'" + 学院输入.getText().trim() + "'" + ",系=" + "'" + 系输入.getText().trim() + "'" + " WHERE 职工编号=" + tID);
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

        setSize(250, 250);
        setResizable(false);
        setVisible(true);
    }
}