import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorPanel extends JFrame{
    JButton systemConfig = new JButton("系统管理");
    JButton dbManager = new JButton("数据库管理");
    JButton exit = new JButton("返回");

    AdministratorPanel(int permission) {
        super("管理员面板");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(systemConfig);
        add(dbManager);
        add(exit);

        systemConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SystemConfig(permission);
            }
        });

        dbManager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DbManager(permission);
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "是否返回", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION)
                    dispose();
            }
        });

        setSize(300, 100);
        setResizable(false);
        setVisible(true);
    }
}
