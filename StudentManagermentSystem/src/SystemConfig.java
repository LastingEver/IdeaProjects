import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SystemConfig extends JFrame{
    Connection connection;
    Statement mysql;

    JButton 清空课程信息=new JButton("清空课程信息");
    JButton 清空成绩信息=new JButton("清空成绩信息");
    JButton 清空学生信息=new JButton("清空学生信息");
    JButton 清空辅导员信息=new JButton("清空辅导员信息");
    JButton 清空老师信息=new JButton("清空老师信息");
    JButton exit = new JButton("返回");

    SystemConfig(int permission) {
        super("系统设置");
        setLayout(new FlowLayout());
        add(清空课程信息);
        add(清空成绩信息);
        add(清空学生信息);
        add(清空辅导员信息);
        add(清空老师信息);
        add(exit);

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

        清空课程信息.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mysql.execute("TRUNCATE cInfo");
                    JOptionPane.showMessageDialog(null, "清空课程信息成功！");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        清空成绩信息.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mysql.execute("TRUNCATE mInfo");
                    JOptionPane.showMessageDialog(null, "清空成绩信息成功！");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        清空学生信息.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mysql.execute("TRUNCATE sInfo");
                    JOptionPane.showMessageDialog(null, "清空学生信息成功！");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        清空辅导员信息.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mysql.execute("TRUNCATE aInfo");
                    JOptionPane.showMessageDialog(null, "清空辅导员信息成功！");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        清空老师信息.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mysql.execute("TRUNCATE tInfo");
                    JOptionPane.showMessageDialog(null, "清空老师信息成功！");
                } catch (SQLException e1) {
                    e1.printStackTrace();
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

        setSize(400, 120);
        setResizable(false);
        setVisible(true);
    }
}
