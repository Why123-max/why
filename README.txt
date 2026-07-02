==================== 学生成绩管理系统（A级完整功能版）使用说明 ====================
一、文件说明
├── Student.java       // 学生实体类（面向对象基础，属性+getter/setter）
├── ScoreManager.java  // 核心业务类（增删改查+文件读写+统计+测试数据生成）
├── StudentGUI.java    // 图形界面类（Swing窗口+按钮+表格）
├── LoginGUI.java      // 用户登录窗口（账号密码验证）
├── Main.java          // 程序启动入口
└── README.txt         // 本说明文件
二、编译运行方法
1. IDEA运行（推荐）：
   - 导入项目后，按Ctrl+Alt+Shift+S打开项目设置
   - SDK选择标准Java JDK（不能选opengrok！），Language level对应JDK版本
   - 右键Main.java → 运行
   - 登录账号：admin  密码：123456
2. 命令行运行：
   javac *.java
   java Main
三、完整功能列表（100%满足A级题目所有要求）
✅ 1. 用户登录验证：启动先进入登录窗口，账号密码正确才能进入系统
✅ 2. 添加学生：输入信息后点「添加学生」，学号自动生成（2026开头，全局唯一）
✅ 3. 修改学生：选中表格里的学生，修改输入框内容后点「修改选中」（学号不可修改）
✅ 4. 删除学生：选中学生后点「删除选中」，二次确认后删除
✅ 5. 按学号精确查询：输入学号点「按学号查」，不存在则提示
✅ 6. 按姓名模糊查询：输入姓名点「按姓名查」，支持同名学生同时显示
✅ 7. 按总成绩降序排序：点「按总分排序」，自动从高到低排列
✅ 8. 导出TXT成绩报表：点「导出TXT报表」，生成格式化报表，包含所有统计信息
✅ 9. 导出Excel成绩报表（加分项）：预留接口，导入POI后即可使用
✅ 10. 生成10万条正态分布测试数据（重要加分项）：
    - 点「生成10万条测试数据」，一键生成
    - 成绩以80分为中心，标准差10，正态分布
    - 学号自动唯一，姓名、性别、生日随机生成
    - 生成后自动保存，关闭程序不丢失
✅ 11. 数据持久化：所有数据自动存在student_data.txt，关闭程序再打开不会丢失
✅ 12. 实时班级统计：底部显示班级人数、各科平均分、总平均分
四、Excel导出功能开启方法（特别加分项）
1. 下载两个POI依赖包：
   - poi-4.1.2.jar
   - poi-ooxml-4.1.2.jar
2. IDEA导入依赖：
   - File → Project Structure → Libraries → + → Java → 选择下载的两个jar包
3. 替换ScoreManager.java中的exportExcel()方法代码：
---------------- 复制以下代码替换原有exportExcel方法 ----------------
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import java.io.FileOutputStream;
public boolean exportExcel() {
    sortByTotalScore();
    try {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("学生成绩表");
        
        // 创建表头
        XSSFRow headerRow = sheet.createRow(0);
        String[] headers = {"学号", "姓名", "性别", "生日", "数学", "Java", "体育", "总分"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        
        // 写入数据
        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            XSSFRow row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(s.getId());
            row.createCell(1).setCellValue(s.getName());
            row.createCell(2).setCellValue(s.getGender());
            row.createCell(3).setCellValue(s.getBirthday());
            row.createCell(4).setCellValue(s.getMathScore());
            row.createCell(5).setCellValue(s.getJavaScore());
            row.createCell(6).setCellValue(s.getPeScore());
            row.createCell(7).setCellValue(s.getTotalScore());
        }
        
        // 自动列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        // 保存文件
        FileOutputStream fos = new FileOutputStream("成绩报表.xlsx");
        workbook.write(fos);
        fos.close();
        workbook.close();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
----------------------------------------------------------------------
五、代码特点（完全大一水平，无超纲内容）
1. 所有语法都是Java基础课内容：类、对象、封装、ArrayList、循环、判断、IO流
2. 所有事件监听都用匿名内部类，无Lambda，兼容所有JDK版本
3. 每个方法都有详细注释，老师问的时候每一行都能讲清楚
4. 符合阿里巴巴Java开发规范：命名规范、格式规范、注释规范
5. 界面与逻辑完全分离，符合面向对象设计思想
六、课程设计答辩准备（老师必问问题）
1. 学号怎么保证唯一？
   答：每次生成学号前，遍历所有已存在的学号，找到最大的序号，加1后作为新学号，从机制上保证不会重复
2. 数据怎么持久化？
   答：使用BufferedWriter将学生数据按逗号分隔的格式写入本地student_data.txt文件，程序启动时用BufferedReader读取文件，按逗号拆分后还原为Student对象，存入ArrayList
3. 模糊查询怎么实现？
   答：使用String类的contains()方法，判断每个学生的姓名是否包含用户输入的关键词，包含则加入结果集
4. 正态分布成绩怎么生成？
   答：使用Java的Random类的nextGaussian()方法生成标准正态分布随机数，公式为：成绩 = 80 + 10 * 随机数，然后限制在0-100区间
5. 排序怎么实现？
   答：使用Collections.sort()方法，自定义Comparator比较器，比较两个学生的总成绩，实现降序排列
6. 登录功能怎么实现？
   答：单独做一个LoginGUI窗口，用户输入账号密码后与预设的admin/123456比对，正确则关闭登录窗口，打开主界面
七、3人团队分工建议
1. 成员1：负责Student类、ScoreManager核心业务逻辑、数据存储、测试数据生成
2. 成员2：负责LoginGUI登录窗口、StudentGUI主界面开发、事件交互
3. 成员3：负责Excel导出功能、文档撰写、答辩PPT、功能测试
=========================================================================
