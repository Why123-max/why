import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentGUI extends JFrame {

    private ScoreManager manager;
    // 输入框 —— 用于输入学生信息
    private JTextField nameField, genderField, birthdayField;
    private JTextField mathField, javaField, peField;
    private JTextField searchField;   // 搜索框 —— 输入学号或姓名关键词

    // 表格 —— 用于展示学生数据
    private JTable table;
    private DefaultTableModel tableModel;  // 表格数据模型，存储表格的行数据

    // 构造方法：初始化界面
    // 【作用】：创建主窗口时 ①创建ScoreManager业务对象并加载数据 ②搭建界面 ③刷新表格显示已有数据
    public StudentGUI() {
        manager = new ScoreManager();  // 创建业务对象（构造方法中会自动调用loadDataFromFile加载历史数据）
        initUI();                      // 搭建界面组件
        refreshTable();                // 将数据填充到表格中显示
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        setTitle("学生成绩管理系统");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // 【作用】：提供学生信息的输入框区域，2行7列的网格布局
        JPanel inputPanel = new JPanel(new GridLayout(2, 7, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("学生信息输入")); // 给面板加带标题的边框

        // 第一行：姓名、性别、生日、数学成绩、Java成绩、体育成绩、空占位
        inputPanel.add(new JLabel("姓名:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("性别:"));
        genderField = new JTextField();
        inputPanel.add(genderField);

        inputPanel.add(new JLabel("生日:"));
        birthdayField = new JTextField();
        inputPanel.add(birthdayField);

        inputPanel.add(new JLabel("数学成绩:"));
        mathField = new JTextField();
        inputPanel.add(mathField);

        // 第二行：Java成绩、体育成绩、搜索标签、搜索框、三个空占位
        inputPanel.add(new JLabel("Java成绩:"));
        javaField = new JTextField();
        inputPanel.add(javaField);

        inputPanel.add(new JLabel("体育成绩:"));
        peField = new JTextField();
        inputPanel.add(peField);

        inputPanel.add(new JLabel("查询(学号/姓名):"));
        searchField = new JTextField();
        inputPanel.add(searchField);

        // 空标签占位：填充剩余格子，保持布局整齐
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel(""));


        // 用JTable表格展示所有学生数据，8列分别对应学号/姓名/性别/生日/数学/Java/体育/总分
        String[] columns = {"学号", "姓名", "性别", "生日", "数学", "Java", "体育", "总分"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // 【作用】：设置表格单元格不可编辑，只能通过输入框修改
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table); // 【作用】：给表格加滚动条，数据多时可以上下滚动

        // ==================== 3. 底部按钮面板 ====================
        // 【作用】：所有操作按钮，使用流式布局居中排列
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addBtn = new JButton("添加学生");           // 点击→添加新学生
        JButton updateBtn = new JButton("修改选中");         // 点击→修改选中的学生
        JButton deleteBtn = new JButton("删除选中");         // 点击→删除选中的学生
        JButton searchIdBtn = new JButton("按学号查");       // 点击→按学号精确查找
        JButton searchNameBtn = new JButton("按姓名查");     // 点击→按姓名模糊查找
        JButton sortBtn = new JButton("按总分排序");         // 点击→按总成绩降序排列
        JButton exportBtn = new JButton("导出TXT报表");      // 点击→导出格式化文本报表
        /*JButton exportExcelBtn = new JButton("导出Excel报表");*/// 点击→导出Excel（预留接口）
        JButton generateTestBtn = new JButton("生成100条测试数据"); // 点击→批量生成正态分布测试数据
        JButton refreshBtn = new JButton("刷新");            // 点击→刷新表格和统计信息

        // 将10个按钮依次加入按钮面板
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(searchIdBtn);
        buttonPanel.add(searchNameBtn);
        buttonPanel.add(sortBtn);
        buttonPanel.add(exportBtn);
        /*buttonPanel.add(exportExcelBtn);*/
        buttonPanel.add(generateTestBtn);
        buttonPanel.add(refreshBtn);

        //  底部统计面板
        // 【作用】：实时显示班级人数、各科平均分、总平均分
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statsPanel.setBorder(BorderFactory.createTitledBorder("班级统计"));
        JLabel statsLabel = new JLabel();
        updateStatsLabel(statsLabel);  // 初始化统计信息
        statsPanel.add(statsLabel);

        //  把所有面板组装到窗口
        setLayout(new BorderLayout(10, 10));               // 主窗口使用边界布局
        add(inputPanel, BorderLayout.NORTH);               // 输入面板放顶部
        add(scrollPane, BorderLayout.CENTER);              // 表格面板放中间（占最大空间）

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.NORTH);   // 按钮面板放南区的上面
        southPanel.add(statsPanel, BorderLayout.SOUTH);    // 统计面板放南区的下面
        add(southPanel, BorderLayout.SOUTH);               // 整个南区放窗口底部

        // 6. 按钮事件监听
        // 【添加学生按钮】：获取输入框内容 → 创建学生对象 → 调用ScoreManager添加 → 刷新表格和统计
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
                updateStatsLabel(statsLabel);
            }
        });

        // 【修改学生按钮】：获取表格选中行和输入框新值 → 调用ScoreManager更新 → 刷新表格和统计
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
                updateStatsLabel(statsLabel);
            }
        });

        // 【删除学生按钮】：获取选中行学号 → 二次确认 → 调用ScoreManager删除 → 刷新表格和统计
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
                updateStatsLabel(statsLabel);
            }
        });

        // 【按学号查询按钮】：取搜索框内容 → 精确查找 → 表格只显示查询结果
        searchIdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchById();
            }
        });

        // 【按姓名查询按钮】：取搜索框内容 → 模糊查找 → 表格显示所有匹配学生
        searchNameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchByName();
            }
        });

        // 【排序按钮】：调用ScoreManager按总分降序排序 → 刷新表格 → 弹窗提示
        sortBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.sortByTotalScore();
                refreshTable();
                JOptionPane.showMessageDialog(null, "已按总分降序排序！");
            }
        });

        // 【导出TXT按钮】：调用ScoreManager导出报表 → 弹窗提示成功或失败
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

        // 【导出Excel按钮】：调用预留的Excel导出接口 → 提示用户需先导入POI依赖
        /*exportExcelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manager.exportExcel()) {
                    JOptionPane.showMessageDialog(null, "Excel导出功能已预留！\n请先导入POI依赖包，然后替换ScoreManager中的exportExcel方法代码\n详细步骤见README.txt");
                } else {
                    JOptionPane.showMessageDialog(null, "导出失败！");
                }
            }
        });
*/
        // 【生成测试数据按钮】：二次确认 → 调用ScoreManager生成10万条正态分布数据 → 刷新
        generateTestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "确定要生成100条测试数据吗？\n（成绩以80分为中心正态分布）");
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.generateTestData(100);
                    refreshTable();
                    updateStatsLabel(statsLabel);
                    JOptionPane.showMessageDialog(null, "100条测试数据生成成功！\n学号唯一，成绩正态分布，已自动保存");
                }
            }
        });

        // 【刷新按钮】：重新加载表格数据 + 清空输入框 + 更新统计信息
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
                clearInput();
                updateStatsLabel(statsLabel);
            }
        });

        // 【表格行选中监听】：当用户点击表格某一行时，自动将该行数据回填到输入框中（方便修改）
        table.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                int row = table.getSelectedRow();   // 获取当前选中行号
                if (row >= 0) {
                    // 将表格各列的值取出，填入对应的输入框
                    nameField.setText(table.getValueAt(row, 1).toString());     // 列1=姓名
                    genderField.setText(table.getValueAt(row, 2).toString());   // 列2=性别
                    birthdayField.setText(table.getValueAt(row, 3).toString()); // 列3=生日
                    mathField.setText(table.getValueAt(row, 4).toString());     // 列4=数学
                    javaField.setText(table.getValueAt(row, 5).toString());     // 列5=Java
                    peField.setText(table.getValueAt(row, 6).toString());       // 列6=体育
                }
            }
        });
    }

    /**
     * 更新统计信息
     * 【作用】：从ScoreManager获取最新的班级人数和各科平均分，更新底部统计标签的文字
     */
    private void updateStatsLabel(JLabel label) {
        label.setText(String.format("班级人数：%d人 | 数学平均分：%.2f | Java平均分：%.2f | 体育平均分：%.2f | 总平均分：%.2f",
                manager.getAllStudents().size(),  // 学生总数
                manager.getMathAvg(),             // 数学平均分
                manager.getJavaAvg(),             // Java平均分
                manager.getPeAvg(),               // 体育平均分
                manager.getTotalAvg()));          // 总平均分
    }

    /**
     * 添加学生
     * 【作用】：①从输入框获取学生信息 → ②调用ScoreManager.addStudent()添加 → ③刷新表格 → ④清空输入框 → ⑤提示成功
     */
    private void addStudent() {
        try {
            Student s = getInputStudent();       // 从输入框读取数据创建Student对象
            manager.addStudent(s);               // 交给ScoreManager处理（自动生成学号+保存）
            refreshTable();                      // 刷新表格显示
            clearInput();                        // 清空所有输入框
            JOptionPane.showMessageDialog(null, "添加成功！自动生成学号：" + s.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "输入错误！请检查成绩是否为数字");
        }
    }

    /**
     * 修改学生
     * 【作用】：①检查是否选中了表格行 → ②获取该行的学号 → ③从输入框获取修改后的信息 → ④调用ScoreManager更新 → ⑤刷新界面
     */
    private void updateStudent() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "请先选中要修改的学生！");
            return;  // 没选中就返回，不继续执行
        }
        try {
            String id = table.getValueAt(row, 0).toString();  // 从表格获取选中学生的学号（列0）
            Student s = getInputStudent();                     // 从输入框获取修改后的信息
            s.setId(id);                                       // 保留原学号不变
            manager.updateStudent(s);                          // 交给ScoreManager更新
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
            manager.deleteStudent(id);  // 交给ScoreManager删除
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
        // 先清空表格，再只添加查到的学生（实现"只显示查询结果"的效果）
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
        ArrayList<Student> result = manager.findStudentByName(name); // 模糊匹配，返回所有包含关键词的学生
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(null, "没有找到匹配的学生！");
            return;
        }
        // 清空表格，显示所有匹配结果
        tableModel.setRowCount(0);
        for (Student s : result) {
            addStudentToTable(s);
        }
    }

    /**
     * 从输入框获取学生对象
     */
    private Student getInputStudent() {
        String name = nameField.getText().trim();                             // 获取姓名
        String gender = genderField.getText().trim();                         // 获取性别
        String birthday = birthdayField.getText().trim();                     // 获取生日
        double math = Double.parseDouble(mathField.getText().trim());         // 获取数学成绩（字符串转double）
        double java = Double.parseDouble(javaField.getText().trim());         // 获取Java成绩
        double pe = Double.parseDouble(peField.getText().trim());             // 获取体育成绩
        return new Student(null, name, gender, birthday, math, java, pe);    // 学号=null，由addStudent自动生成
    }

    /**
     * 刷新表格数据
     */
    private void refreshTable() {
        tableModel.setRowCount(0);            // 先清空表格
        for (Student s : manager.getAllStudents()) {
            addStudentToTable(s);             // 逐个添加学生到表格
        }
    }

    /**
     * 把单个学生加到表格
     */
    private void addStudentToTable(Student s) {
        Object[] row = {
                s.getId(),                          // 列0：学号
                s.getName(),                        // 列1：姓名
                s.getGender(),                      // 列2：性别
                s.getBirthday(),                    // 列3：生日
                s.getMathScore(),                   // 列4：数学成绩
                s.getJavaScore(),                   // 列5：Java成绩
                s.getPeScore(),                     // 列6：体育成绩
                String.format("%.1f", s.getTotalScore())  // 列7：总分（保留1位小数）
        };
        tableModel.addRow(row);  // 将行数据添加到表格模型
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
