import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登录窗口 - 实现用户登录验证功能
 */
public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {
        initUI();
    }

    private void initUI() {
        setTitle("系统登录");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("用户名:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("密码:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginBtn = new JButton("登录");
        JButton exitBtn = new JButton("退出");

        panel.add(loginBtn);
        panel.add(exitBtn);

        add(panel);

        // 登录按钮事件
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                // 默认账号密码，可自行修改
                if (username.equals("admin") && password.equals("123456")) {
                    JOptionPane.showMessageDialog(null, "登录成功！");
                    dispose(); // 关闭登录窗口
                    new StudentGUI().setVisible(true); // 打开主界面
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误！\n默认账号：admin  密码：123456");
                }
            }
        });

        // 退出按钮事件
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
