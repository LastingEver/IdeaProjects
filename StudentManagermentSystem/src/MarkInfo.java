import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MarkInfo extends JFrame {
    Connection connection;
    Statement mysql;

    JPanel 课程编号 = new JPanel();
    JLabel 课程编号标签 = new JLabel("课程编号");
    JTextField 课程编号输入 = new JTextField(13);

    JPanel 职工编号 = new JPanel();
    JLabel 职工编号标签 = new JLabel("职工编号");
    JTextField 职工编号输入 = new JTextField(13);

    JPanel 学号 = new JPanel();
    JLabel 学号标签 = new JLabel("学号");
    JTextField 学号输入 = new JTextField(13);

    JPanel 成绩 = new JPanel();
    JLabel 成绩标签 = new JLabel("成绩");
    JTextField 成绩输入 = new JTextField(13);

    JPanel operation = new JPanel();
    JButton jButton;
    JButton exit = new JButton("返回");

    MarkInfo(int permission, String ID) {
        super("成绩信息");
        switch (permission) {
            case 1: {
                jButton = new JButton("查询");
                JOptionPane.showMessageDialog(null, "请输入成绩编号，并点击查询搜索！");
                职工编号输入.setEditable(false);
                成绩输入.setEditable(false);
                break;
            }

            case 2: {
                jButton = new JButton("提交");
                JOptionPane.showMessageDialog(null, "请输入成绩信息，并点击提交修改！");
                break;
            }

            case 3: {
                jButton = new JButton("添加");
                JOptionPane.showMessageDialog(null, "请输入成绩内容，并点击添加录入！");
                break;
            }
        }

        setLayout(new FlowLayout());
        add(课程编号);
        add(职工编号);
        add(学号);
        add(成绩);
        add(operation);

        职工编号.add(职工编号标签);
        职工编号.add(职工编号输入);

        课程编号.add(课程编号标签);
        课程编号.add(课程编号输入);

        学号.add(学号标签);
        学号.add(学号输入);

        成绩.add(成绩标签);
        成绩.add(成绩输入);

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
                        if (课程编号输入.getText().trim().equals("") || 学号输入.getText().trim().equals("")) {
                            JOptionPane.showMessageDialog(null, "课程编号或学号不得为空！请重新输入!");
                        } else {
                            try {
                                ResultSet rs = mysql.executeQuery("SELECT * FROM mInfo WHERE 课程编号=" + 课程编号输入.getText().trim() + "&&" + "学号=" + 学号输入.getText().trim());
                                if (rs.next()) {
                                    职工编号输入.setText(rs.getString(2));
                                    学号输入.setText(rs.getString(3));
                                    成绩输入.setText(rs.getString(4));
                                } else {
                                    职工编号输入.setText("");
                                    学号输入.setText("");
                                    成绩输入.setText("");
                                    JOptionPane.showMessageDialog(null, "课程编号或学号有误！请重新输入!");
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                        break;
                    }

                    case 2: {
                        if (课程编号输入.getText().trim().equals("") || 职工编号输入.getText().trim().equals("") || 学号输入.getText().trim().equals("") || 成绩输入.getText().trim().equals("")) {
                            JOptionPane.showMessageDialog(null, "成绩信息不得为空！请重新输入!");
                        } else {
                            try {
                                ResultSet rs = mysql.executeQuery("SELECT * FROM mInfo WHERE 课程编号=" + 课程编号输入.getText().trim());
                                if (!rs.next()) {
                                    JOptionPane.showMessageDialog(null, "课程编号有误！请重新输入!");
                                } else {
                                    mysql.executeUpdate("UPDATE mInfo SET 职工编号=" + "'" + 职工编号输入.getText().trim() + "'" + ",学号=" + 学号输入.getText().trim() + ",成绩=" + 成绩输入.getText().trim() + " WHERE 课程编号=" + 课程编号输入.getText().trim());
                                    职工编号输入.setText("");
                                    学号输入.setText("");
                                    成绩输入.setText("");
                                    JOptionPane.showMessageDialog(null, "成绩信息修改成功!");
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                        break;
                    }

                    case 3: {
                        if (课程编号输入.getText().trim().equals("") || 职工编号输入.getText().trim().equals("") || 学号输入.getText().trim().equals("") || 成绩输入.getText().trim().equals("")) {
                            JOptionPane.showMessageDialog(null, "成绩信息不得为空！请重新输入!");
                        } else {
                            try {
                                mysql.executeUpdate("INSERT INTO mInfo (课程编号,职工编号,学号,成绩) VALUES (" + 课程编号输入.getText().trim() + ",'" + 职工编号输入.getText().trim() + "'," + 学号输入.getText().trim() + "," + 成绩输入.getText().trim() + ")");
                                职工编号输入.setText("");
                                学号输入.setText("");
                                成绩输入.setText("");
                                JOptionPane.showMessageDialog(null, "成绩内容添加成功!");
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
