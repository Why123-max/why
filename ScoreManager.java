import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class ScoreManager {
    private ArrayList<Student> studentList;
    private int idCounter = 1;

    private static final String DATA_FILE = "student_data.txt";

    private static final String REPORT_FILE = "成绩报表.txt";

    public ScoreManager() {
        studentList = new ArrayList<>();
        loadDataFromFile(); // 启动时加载本地数据
    }
    // 调用loadDataFromFile()从本地文件恢复之前保存的学生数据
    //学号生成
    public String generateId() {
        // 保证学号唯一，找到最大的学号+1
        int maxId = 0;
        for (Student s : studentList) {
            int num = Integer.parseInt(s.getId().substring(4));
            if (num > maxId) {
                maxId = num;
            }
        }
        idCounter = maxId + 1;
        return String.format("2026%05d", idCounter);
    }
    // 自动生成全局唯一的学号，避免手动输入学号重复。
    /**
     * 添加学生
     */
    public boolean addStudent(Student student) {
        // 自动设置学号
        student.setId(generateId());
        studentList.add(student);
        saveDataToFile(); // 添加后自动保存
        return true;
    }

    /**
     * 按学号查询学生
     */
    public Student findStudentById(String id) {
        for (Student s : studentList) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    /**
     * 按姓名模糊查询
     */
    public ArrayList<Student> findStudentByName(String name) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student s : studentList) {
            // 模糊匹配：只要姓名包含输入的内容就算
            if (s.getName().contains(name)) {
                result.add(s);
            }
        }
        return result;
    }

    /**
     * 修改学生信息
     */
    public boolean updateStudent(Student newStudent) {
        for (int i = 0; i < studentList.size(); i++) {
            Student old = studentList.get(i);
            if (old.getId().equals(newStudent.getId())) {
                // 学号不变，其他信息更新
                newStudent.setId(old.getId());
                studentList.set(i, newStudent);
                saveDataToFile(); // 修改后自动保存
                return true;
            }
        }
        return false;
    }

    /**
     * 删除学生
     */
    public boolean deleteStudent(String id) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getId().equals(id)) {
                studentList.remove(i);
                saveDataToFile(); // 删除后自动保存
                return true;
            }
        }
        return false;
    }
    /**
     * 计算数学班级平均分
     */
    public double getMathAvg() {
        if (studentList.isEmpty()) return 0;
        double sum = 0;
        for (Student s : studentList) {
            sum += s.getMathScore();
        }
        return sum / studentList.size();
    }
    /**
     * 计算Java班级平均分
     */
    public double getJavaAvg() {
        if (studentList.isEmpty()) return 0;
        double sum = 0;
        for (Student s : studentList) {
            sum += s.getJavaScore();
        }
        return sum / studentList.size();
    }
    /**
     * 计算体育班级平均分
     */
    public double getPeAvg() {
        if (studentList.isEmpty()) return 0;
        double sum = 0;
        for (Student s : studentList) {
            sum += s.getPeScore();
        }
        return sum / studentList.size();
    }
    /**
     * 计算班级总平均分
     */
    public double getTotalAvg() {
        if (studentList.isEmpty()) return 0;
        double sum = 0;
        for (Student s : studentList) {
            sum += s.getTotalScore();
        }
        return sum / studentList.size();
    }
    /**
     * 按总成绩降序排序
     */
    public void sortByTotalScore() {
        Collections.sort(studentList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                // 降序：s2 - s1
                return Double.compare(s2.getTotalScore(), s1.getTotalScore());
            }
        });
    }
    /**
     * 导出成绩报表到txt文件
     */
    public boolean exportReport() {
        sortByTotalScore(); // 先排序
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(REPORT_FILE));
            // 写表头
            bw.write("==================== 学生成绩报表 ====================");
            bw.newLine();
            bw.write(String.format("班级数学平均分：%.2f | Java平均分：%.2f | 体育平均分：%.2f | 总平均分：%.2f",
                    getMathAvg(), getJavaAvg(), getPeAvg(), getTotalAvg()));
            bw.newLine();
            bw.write("-------------------------------------------------------");
            bw.newLine();
            bw.write(String.format("%-10s %-8s %-6s %-12s %-8s %-8s %-8s %-8s",
                    "学号", "姓名", "性别", "生日", "数学", "Java", "体育", "总分"));
            bw.newLine();
            bw.write("-------------------------------------------------------");
            bw.newLine();
            // 写每个学生
            for (Student s : studentList) {
                bw.write(String.format("%-10s %-8s %-6s %-12s %-8.1f %-8.1f %-8.1f %-8.1f",
                        s.getId(), s.getName(), s.getGender(), s.getBirthday(),
                        s.getMathScore(), s.getJavaScore(), s.getPeScore(), s.getTotalScore()));
                bw.newLine();
            }
            bw.write("=======================================================");
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 生成测试数据（
     */
    public boolean generateTestData(int count) {
        Random random = new Random();
        String[] names = {"张三", "李四", "王五", "赵六", "钱七", "孙八", "周九", "吴十",
                "郑一", "王二", "冯三", "陈四", "褚五", "卫六", "蒋七", "沈八"};
        String[] genders = {"男", "女"};
        for (int i = 0; i < count; i++) {
            Student s = new Student();
            s.setId(generateId());
            s.setName(names[random.nextInt(names.length)] + (i+1));
            s.setGender(genders[random.nextInt(2)]);
            s.setBirthday("2005-" + (random.nextInt(12)+1) + "-" + (random.nextInt(28)+1));

            s.setMathScore(limitScore(80 + 10 * random.nextGaussian()));
            s.setJavaScore(limitScore(80 + 10 * random.nextGaussian()));
            s.setPeScore(limitScore(80 + 10 * random.nextGaussian()));

            studentList.add(s);
        }
        saveDataToFile();
        return true;
    }

    private double limitScore(double score) {
        if (score < 0) return 0;
        if (score > 100) return 100;
        return Math.round(score * 10) / 10.0; // 保留1位小数
    }


    /**
     * 保存数据到本地txt文件（关闭程序再打开还在）
     */
    private void saveDataToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE));
            for (Student s : studentList) {
                // 每行格式：学号,姓名,性别,生日,数学,Java,体育
                bw.write(s.getId() + "," + s.getName() + "," + s.getGender() + "," + s.getBirthday() + ","
                        + s.getMathScore() + "," + s.getJavaScore() + "," + s.getPeScore());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从本地文件加载数据
     */
    private void loadDataFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return; // 文件不存在就不加载

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    Student s = new Student();
                    s.setId(parts[0]);
                    s.setName(parts[1]);
                    s.setGender(parts[2]);
                    s.setBirthday(parts[3]);
                    s.setMathScore(Double.parseDouble(parts[4]));
                    s.setJavaScore(Double.parseDouble(parts[5]));
                    s.setPeScore(Double.parseDouble(parts[6]));
                    studentList.add(s);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //界面用
    public ArrayList<Student> getAllStudents() {
        return studentList;
    }
}
