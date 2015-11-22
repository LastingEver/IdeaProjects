import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentManagerSystem extends JFrame {
    public static final int PERMISSION_STUDENT = 1;
    public static final int PERMISSION_ASSISTANT = 2;
    public static final int PERMISSION_TEACHER = 3;
    public static final int PERMISSION_ADMINISTRATOR = 4;

    JButton studentLogin = new JButton("学生登陆");
    JButton assistantLogin = new JButton("辅导员登陆");
    JButton teacherLogin = new JButton("老师登陆");
    JButton administratorLogin = new JButton("管理员登陆");
    JButton exit = new JButton("退出");
    JLabel info = new JLabel("主菜单：");

    public StudentManagerSystem() {
        super("学生信息管理系统");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(info);
        add(studentLogin);
        add(assistantLogin);
        add(teacherLogin);
        add(administratorLogin);
        add(exit);

        studentLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentLogin(PERMISSION_STUDENT);
            }
        });

        assistantLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AssistantLogin(PERMISSION_ASSISTANT);
            }
        });

        teacherLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TeacherLogin(PERMISSION_TEACHER);
            }
        });

        administratorLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdministratorLogin(PERMISSION_ADMINISTRATOR);
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "是否退出", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION)
                    System.exit(256);//返回值8位integer
            }
        });

        setSize(600, 100);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new StudentManagerSystem();
    }
}