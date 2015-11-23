import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdministratorLogin extends JFrame{
    Connection connection;
    Statement mysql;

    JPanel usernamePanel=new JPanel();
    JLabel usernameLabel=new JLabel("管理员账号");
    JTextField username=new JTextField(13);

    JPanel passwordPanel=new JPanel();
    JLabel passwordLabel=new JLabel("管理员密码");
    JPasswordField password=new JPasswordField(13);

    JPanel operation=new JPanel();
    JButton login=new JButton("登陆");
    JButton back=new JButton("返回");

    AdministratorLogin(int permission){
        super("管理员登陆");
        setLayout(new BorderLayout());
        add(usernamePanel, BorderLayout.NORTH);
        add(passwordPanel, BorderLayout.CENTER);
        add(operation, BorderLayout.SOUTH);

        usernamePanel.add(usernameLabel);
        usernamePanel.add(username);

        passwordPanel.add(passwordLabel);
        passwordPanel.add(password);

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
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", username.getText().trim(), String.valueOf(password.getPassword()));
                    mysql = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    System.out.println("数据库连接成功");
                    JOptionPane.showMessageDialog(null, "登陆成功！");
                    dispose();
                    new AdministratorPanel(permission);
                } catch (SQLException exception) {
                    System.out.println("数据库连接异常！");
                    JOptionPane.showMessageDialog(null, "MySQL数据源连接失败！请检查！");
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