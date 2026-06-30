import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JTextField usernameField;    // 用户名输入框
    private JPasswordField passwordField; // 密码输入框

    public LoginGUI() {
        initUI();
    }

    // 登录窗口所有组件
    private void initUI() {
        // 窗口基本设置
        setTitle("系统登录");
        setSize(350, 200);                         // 设置窗口宽度350像素，高度200像素
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //点击窗口关闭按钮时退出程序
        setLocationRelativeTo(null);               // 让窗口在屏幕正中央显示
        setResizable(true);                       // 是否可调窗口大小

        // 面板：使用3行2列的网格布局，行列间距各10像素
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 留空白边距

        //用户名标签 + 用户名输入框
        panel.add(new JLabel("用户名:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        //密码标签 + 密码输入框（JPasswordField让输入内容显示为●）
        panel.add(new JLabel("密码:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // 登录按钮 + 退出按钮
        JButton loginBtn = new JButton("登录");
        JButton exitBtn = new JButton("退出");
        panel.add(loginBtn);
        panel.add(exitBtn);
        add(panel);  // 把面板加到窗口上

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();           // 获取用户名，trim()去掉首尾空格
                String password = new String(passwordField.getPassword()).trim(); // 从密码框获取密码（char[]转为String）

                if (username.equals("why") && password.equals("123456")) {
                    JOptionPane.showMessageDialog(null, "登录成功！");  // 弹窗提示登录成功
                    dispose();                        // 关闭当前登录窗口
                    new StudentGUI().setVisible(true); // 创建并显示主界面窗口
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误！");
                }
            }
        });

        // 点击退出按钮 → 直接结束程序运行
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  //立即终止Java虚拟机，参数0表示正常退出
            }
        });
    }
}
