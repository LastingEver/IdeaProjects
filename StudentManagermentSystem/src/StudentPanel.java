import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentPanel extends JFrame {
    JButton sInfo = new JButton("个人信息维护");
    JButton cInfo = new JButton("课程查询");
    JButton mInfo = new JButton("成绩查询");
    JButton exit = new JButton("返回");

    StudentPanel(int permission, String sID) {
        super("学生面板");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(sInfo);
        add(cInfo);
        add(mInfo);
        add(exit);

        sInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentInfo(permission,sID);
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
                new MarkInfo(permission,sID);
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