import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * 图形界面类 - 大一Swing基础写法
 * 简单实用，所有组件都是基础款
 */
public class StudentGUI extends JFrame {
    // 业务逻辑对象
    private ScoreManager manager;
    // 输入框
    private JTextField nameField, genderField, birthdayField;
    private JTextField mathField, javaField, peField;
    private JTextField searchField;
    // 表格
    private JTable table;
    private DefaultTableModel tableModel;
    // 构造方法：初始化界面
    public StudentGUI() {
        manager = new ScoreManager();
        initUI();
        refreshTable(); // 启动时刷新表格数据
    }
    /**
     * 初始化界面
     */
    private void initUI() {
        // 窗口基本设置
        setTitle("学生成绩管理系统 - 大一版");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 窗口居中
        // 1. 顶部输入面板
        JPanel inputPanel = new JPanel(new GridLayout(2, 7, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("学生信息输入"));
        inputPanel.add(new JLabel("姓名:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("性别:"));
        genderField = new JTextField();
        inputPanel.add(genderField);
        inputPanel.add(new JLabel("生日(如2005-01-01):"));
        birthdayField = new JTextField();
        inputPanel.add(birthdayField);
        inputPanel.add(new JLabel("数学成绩:"));
        mathField = new JTextField();
        inputPanel.add(mathField);

        inputPanel.add(new JLabel("Java成绩:"));
        javaField = new JTextField();
        inputPanel.add(javaField);

        inputPanel.add(new JLabel("体育成绩:"));
        peField = new JTextField();
        inputPanel.add(peField);
        // 第二行搜索
        inputPanel.add(new JLabel("查询(学号/姓名):"));
        searchField = new JTextField();
        inputPanel.add(searchField);

        // 空标签占位
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel(""));

        // 2. 中间表格面板
        String[] columns = {"学号", "姓名", "性别", "生日", "数学", "Java", "体育", "总分"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 表格不可直接编辑
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // 3. 底部按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addBtn = new JButton("添加学生");
        JButton updateBtn = new JButton("修改选中");
        JButton deleteBtn = new JButton("删除选中");
        JButton searchIdBtn = new JButton("按学号查");
        JButton searchNameBtn = new JButton("按姓名查");
        JButton sortBtn = new JButton("按总分排序");
        JButton exportBtn = new JButton("导出TXT报表");
        JButton exportExcelBtn = new JButton("导出Excel报表");
        JButton generateTestBtn = new JButton("生成10万条测试数据");
        JButton refreshBtn = new JButton("刷新");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(searchIdBtn);
        buttonPanel.add(searchNameBtn);
        buttonPanel.add(sortBtn);
        buttonPanel.add(exportBtn);
        buttonPanel.add(exportExcelBtn);
        buttonPanel.add(generateTestBtn);
        buttonPanel.add(refreshBtn);

        // 4. 底部统计面板
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statsPanel.setBorder(BorderFactory.createTitledBorder("班级统计"));
        JLabel statsLabel = new JLabel();
        updateStatsLabel(statsLabel);
        statsPanel.add(statsLabel);

        // 5. 把所有面板加到窗口
        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.NORTH);
        southPanel.add(statsPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);

        // ========== 按钮事件监听（大一基础写法） ==========
        // 添加学生
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
                updateStatsLabel(statsLabel);
            }
        });

        // 修改学生
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
                updateStatsLabel(statsLabel);
            }
        });

        // 删除学生
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
                updateStatsLabel(statsLabel);
            }
        });

        // 按学号查询
        searchIdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchById();
            }
        });

        // 按姓名查询
        searchNameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchByName();
            }
        });

        // 排序
        sortBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.sortByTotalScore();
                refreshTable();
                JOptionPane.showMessageDialog(null, "已按总分降序排序！");
            }
        });

        // 导出TXT报表
        exportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manager.exportReport()) {
                    JOptionPane.showMessageDialog(null, "导出成功！文件名为：成绩报表.txt");
                } else {
                    JOptionPane.showMessageDialog(null, "导出失败！");
                }
            }
        });

        // 导出Excel报表
        exportExcelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manager.exportExcel()) {
                    JOptionPane.showMessageDialog(null, "Excel导出功能已预留！\n请先导入POI依赖包，然后替换ScoreManager中的exportExcel方法代码\n详细步骤见README.txt");
                } else {
                    JOptionPane.showMessageDialog(null, "导出失败！");
                }
            }
        });

        // 生成10万条测试数据
        generateTestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "确定要生成10万条测试数据吗？\n（成绩以80分为中心正态分布）");
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.generateTestData(100000);
                    refreshTable();
                    updateStatsLabel(statsLabel);
                    JOptionPane.showMessageDialog(null, "10万条测试数据生成成功！\n学号唯一，成绩正态分布，已自动保存");
                }
            }
        });

        // 刷新
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
                clearInput();
                updateStatsLabel(statsLabel);
            }
        });

        // 表格点击选中，自动填充到输入框（大一匿名内部类写法，兼容所有JDK版本）
        table.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    nameField.setText(table.getValueAt(row, 1).toString());
                    genderField.setText(table.getValueAt(row, 2).toString());
                    birthdayField.setText(table.getValueAt(row, 3).toString());
                    mathField.setText(table.getValueAt(row, 4).toString());
                    javaField.setText(table.getValueAt(row, 5).toString());
                    peField.setText(table.getValueAt(row, 6).toString());
                }
            }
        });
    }

    /**
     * 更新统计信息
     */
    private void updateStatsLabel(JLabel label) {
        label.setText(String.format("班级人数：%d人 | 数学平均分：%.2f | Java平均分：%.2f | 体育平均分：%.2f | 总平均分：%.2f",
                manager.getAllStudents().size(),
                manager.getMathAvg(),
                manager.getJavaAvg(),
                manager.getPeAvg(),
                manager.getTotalAvg()));
    }

    /**
     * 添加学生
     */
    private void addStudent() {
        try {
            Student s = getInputStudent();
            manager.addStudent(s);
            refreshTable();
            clearInput();
            JOptionPane.showMessageDialog(null, "添加成功！自动生成学号：" + s.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "输入错误！请检查成绩是否为数字");
        }
    }

    /**
     * 修改学生
     */
    private void updateStudent() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "请先选中要修改的学生！");
            return;
        }
        try {
            String id = table.getValueAt(row, 0).toString();
            Student s = getInputStudent();
            s.setId(id);
            manager.updateStudent(s);
            refreshTable();
            clearInput();
            JOptionPane.showMessageDialog(null, "修改成功！");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "输入错误！请检查成绩是否为数字");
        }
    }

    /**
     * 删除学生
     */
    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "请先选中要删除的学生！");
            return;
        }
        String id = table.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(null, "确定要删除学号为" + id + "的学生吗？");
        if (confirm == JOptionPane.YES_OPTION) {
            manager.deleteStudent(id);
            refreshTable();
            clearInput();
            JOptionPane.showMessageDialog(null, "删除成功！");
        }
    }

    /**
     * 按学号查询
     */
    private void searchById() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "请输入学号！");
            return;
        }
        Student s = manager.findStudentById(id);
        if (s == null) {
            JOptionPane.showMessageDialog(null, "学号不存在！");
            return;
        }
        // 只显示查询结果
        tableModel.setRowCount(0);
        addStudentToTable(s);
    }

    /**
     * 按姓名模糊查询
     */
    private void searchByName() {
        String name = searchField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "请输入姓名！");
            return;
        }
        ArrayList<Student> result = manager.findStudentByName(name);
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(null, "没有找到匹配的学生！");
            return;
        }
        // 显示查询结果
        tableModel.setRowCount(0);
        for (Student s : result) {
            addStudentToTable(s);
        }
    }

    /**
     * 从输入框获取学生对象
     */
    private Student getInputStudent() {
        String name = nameField.getText().trim();
        String gender = genderField.getText().trim();
        String birthday = birthdayField.getText().trim();
        double math = Double.parseDouble(mathField.getText().trim());
        double java = Double.parseDouble(javaField.getText().trim());
        double pe = Double.parseDouble(peField.getText().trim());
        return new Student(null, name, gender, birthday, math, java, pe);
    }

    /**
     * 刷新表格数据
     */
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : manager.getAllStudents()) {
            addStudentToTable(s);
        }
    }

    /**
     * 把单个学生加到表格
     */
    private void addStudentToTable(Student s) {
        Object[] row = {
                s.getId(),
                s.getName(),
                s.getGender(),
                s.getBirthday(),
                s.getMathScore(),
                s.getJavaScore(),
                s.getPeScore(),
                String.format("%.1f", s.getTotalScore())
        };
        tableModel.addRow(row);
    }

    /**
     * 清空输入框
     */
    private void clearInput() {
        nameField.setText("");
        genderField.setText("");
        birthdayField.setText("");
        mathField.setText("");
        javaField.setText("");
        peField.setText("");
        searchField.setText("");
    }
}
