import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorLogin extends JFrame{
    JPanel usernamePanel=new JPanel();
    JLabel usernameLabel=new JLabel("管理员账号");
    JTextField username=new JTextField(13);

    JPanel passwordPanel=new JPanel();
    JLabel passwordLabel=new JLabel("管理员密码");
    JTextField password=new JTextField(13);

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
                //TODO完成数据库查询操作返回登陆状态

                new AdministratorPanel(permission);
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