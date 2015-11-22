import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherPanel extends JFrame{
    JButton tInfo = new JButton("个人信息维护");
    JButton cInfo = new JButton("课程录入");
    JButton mInfo = new JButton("成绩录入");
    JButton exit = new JButton("返回");

    TeacherPanel(int permission, String tID) {
        super("老师面板");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(tInfo);
        add(cInfo);
        add(mInfo);
        add(exit);

        tInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TeacherInfo(permission, tID);
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
                new MarkInfo(permission, tID);
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