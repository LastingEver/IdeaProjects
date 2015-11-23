import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DbManager extends JFrame{
    Connection connection;
    Statement mysql;

    JTextField 课程编号输入=new JTextField(1);
    JButton 显示课程=new JButton("显示课程");
    JButton exit = new JButton("返回");
    JTextArea 课程信息=new JTextArea();

    DbManager(int permission) {
        super("数据库管理");
        setLayout(new FlowLayout());
        add(课程编号输入);
        add(显示课程);
        add(exit);
        add(课程信息);

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

        显示课程.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (课程编号输入.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null, "课程编号不得为空！请重新输入!");
                } else {
                    try {
                        if (!课程信息.getText().trim().equals("")){
                            课程信息.setText("");
                        }
                        ResultSet rs=mysql.executeQuery("SELECT * FROM mInfo WHERE 课程编号=" + 课程编号输入.getText().trim() + " ORDER BY 成绩");
                        if (!rs.next()){
                            JOptionPane.showMessageDialog(null, "课程编号有误！请重新输入!");
                        } else {
                            课程信息.append(rs.getString(4) + "\t" + rs.getString(3) + "\n");
                            while (rs.next()){
                                课程信息.append(rs.getString(4) + "\t" + rs.getString(3) + "\n");
                            }
                        }
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

        setSize(200,200);
        setResizable(false);
        setVisible(true);
    }
}
