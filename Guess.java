import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
public class Guess {
    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog("输入名字：");
        int num = (int) (Math.random() * 100) + 1;
        int count = 0;
        while (true) {
            String s = JOptionPane.showInputDialog("猜1-100：");
            int n = Integer.parseInt(s);
            count++;

            if (n > num)
                JOptionPane.showMessageDialog(null, "大了");
            else if (n < num)
                JOptionPane.showMessageDialog(null, "小了");
            else {
                JOptionPane.showMessageDialog(null, "猜对了！次数：" + count);
                break;
            }
        }
        // 保存记录
        try {
            FileWriter fw = new FileWriter("guess.txt", true);
            fw.write(name + " " + count + "\n");
            fw.close();
        } catch (IOException e) {}
    }
}