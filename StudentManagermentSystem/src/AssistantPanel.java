import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssistantPanel extends JFrame{
    JButton aInfo = new JButton("个人信息维护");
    JButton cInfo = new JButton("课程修改");
    JButton mInfo = new JButton("成绩修改");
    JButton exit = new JButton("返回");

    AssistantPanel(int permission, String aID) {
        super("辅导员面板");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(aInfo);
        add(cInfo);
        add(mInfo);
        add(exit);

        aInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AssistantInfo(permission, aID);
            }
        });

        cInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CourseInfo(permission);
            }
        });

        mInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MarkInfo(permission, aID);
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "是否返回", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION)
                    dispose();
            }
        });

        setSize(400, 100);
        setResizable(false);
        setVisible(true);
    }
}
