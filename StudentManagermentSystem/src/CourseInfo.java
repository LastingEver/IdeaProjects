import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CourseInfo extends JFrame {
    Connection connection;
    Statement mysql;

    JPanel 课程编号 = new JPanel();
    JLabel 课程编号标签 = new JLabel("课程编号");
    JTextField 课程编号输入 = new JTextField(13);

    JPanel 课程名称 = new JPanel();
    JLabel 课程名称标签 = new JLabel("课程名称");
    JTextField 课程名称输入 = new JTextField(13);

    JPanel 学分 = new JPanel();
    JLabel 学分标签 = new JLabel("学分");
    JTextField 学分输入 = new JTextField(13);

    JPanel 学时数 = new JPanel();
    JLabel 学时数标签 = new JLabel("学时数");
    JTextField 学时数输入 = new JTextField(13);

    JPanel operation = new JPanel();
    JButton jButton;
    JButton exit = new JButton("返回");

    CourseInfo(int permission) {
        super("课程信息");
        switch (permission) {
            case 1: {
                jButton = new JButton("查询");
                JOptionPane.showMessageDialog(null, "请输入课程编号，并点击查询搜索！");
                课程名称输入.setEditable(false);
                学分输入.setEditable(false);
                学时数输入.setEditable(false);
                break;
            }

            case 2: {
                jButton = new JButton("提交");
                JOptionPane.showMessageDialog(null, "请输入课程信息，并点击提交修改！");
                break;
            }

            case 3: {
                jButton = new JButton("添加");
                JOptionPane.showMessageDialog(null, "请输入课程内容，并点击添加录入！");
                break;
            }
        }

        setLayout(new FlowLayout());
        add(课程编号);
        add(课程名称);
        add(学分);
        add(学时数);
        add(operation);

        课程名称.add(课程名称标签);
        课程名称.add(课程名称输入);

        课程编号.add(课程编号标签);
        课程编号.add(课程编号输入);

        学分.add(学分标签);
        学分.add(学分输入);

        学时数.add(学时数标签);
        学时数.add(学时数输入);

        operation.add(jButton);
        operation.add(exit);

        setSize(250, 250);
        setResizable(false);
        setVisible(true);

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

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (permission) {
                    case 1: {
                        if (课程编号输入.getText().trim().equals("")) {
                            JOptionPane.showMessageDialog(null, "课程编号不得为空！请重新输入!");
                        } else {
                            try {
                                ResultSet rs = mysql.executeQuery("SELECT * FROM cInfo WHERE 课程编号=" + 课程编号输入.getText().trim());
                                if (rs.next()) {
                                    课程名称输入.setText(rs.getString(2));
                                    学分输入.setText(rs.getString(3));
                                    学时数输入.setText(rs.getString(4));
                                } else {
                                    课程名称输入.setText("");
                                    学分输入.setText("");
                                    学时数输入.setText("");
                                    JOptionPane.showMessageDialog(null, "课程编号有误！请重新输入!");
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                        break;
                    }

                    case 2: {
                        if (课程编号输入.getText().trim().equals("") || 课程名称输入.getText().trim().equals("") || 学分输入.getText().trim().equals("") || 学时数输入.getText().trim().equals("")) {
                            JOptionPane.showMessageDialog(null, "课程信息不得为空！请重新输入!");
                        } else {
                            try {
                                ResultSet rs = mysql.executeQuery("SELECT * FROM cInfo WHERE 课程编号=" + 课程编号输入.getText().trim());
                                if (!rs.next()) {
                                    JOptionPane.showMessageDialog(null, "课程编号有误！请重新输入!");
                                } else {
                                    mysql.executeUpdate("UPDATE cInfo SET 课程名称=" + "'" + 课程名称输入.getText().trim() + "'" + ",学分=" + 学分输入.getText().trim() + ",学时数=" + 学时数输入.getText().trim() + " WHERE 课程编号=" + 课程编号输入.getText().trim());
                                    JOptionPane.showMessageDialog(null, "课程信息修改成功!");
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                        break;
                    }

                    case 3: {
                        if (课程编号输入.getText().trim().equals("") || 课程名称输入.getText().trim().equals("") || 学分输入.getText().trim().equals("") || 学时数输入.getText().trim().equals("")) {
                            JOptionPane.showMessageDialog(null, "课程信息不得为空！请重新输入!");
                        } else {
                            try {
                                mysql.executeUpdate("INSERT INTO cInfo (课程编号,课程名称,学分,学时数) VALUES (" + 课程编号输入.getText().trim() + ",'" + 课程名称输入.getText().trim() + "'," + 学分输入.getText().trim() + "," + 学时数输入.getText().trim() + ")");
                                课程名称输入.setText("");
                                学分输入.setText("");
                                学时数输入.setText("");
                                JOptionPane.showMessageDialog(null, "课程内容添加成功!");
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                        break;
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
    }
}
