
public class Student {
    // 学生属性（封装：私有属性）
    private String id;          // 学号（自动生成，唯一）
    private String name;        // 姓名
    private String gender;      // 性别
    private String birthday;    // 出生年月日
    private double mathScore;   // 数学成绩
    private double javaScore;   // Java成绩
    private double peScore;     // 体育成绩

    // 无参构造方法（大一必学）
    public Student() {
    }

    // 带参构造方法（快速创建学生对象）
    public Student(String id, String name, String gender, String birthday, 
                   double mathScore, double javaScore, double peScore) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.mathScore = mathScore;
        this.javaScore = javaScore;
        this.peScore = peScore;
    }

    // 计算总成绩的方法
    public double getTotalScore() {
        return mathScore + javaScore + peScore;
    }

    // 计算个人平均分的方法
    public double getAverageScore() {
        return getTotalScore() / 3;
    }

    // ========== 以下是Getter和Setter方法（封装必备，大一必学） ==========
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public double getMathScore() {
        return mathScore;
    }

    public void setMathScore(double mathScore) {
        this.mathScore = mathScore;
    }

    public double getJavaScore() {
        return javaScore;
    }

    public void setJavaScore(double javaScore) {
        this.javaScore = javaScore;
    }

    public double getPeScore() {
        return peScore;
    }

    public void setPeScore(double peScore) {
        this.peScore = peScore;
    }

    // toString方法，方便打印学生信息
    @Override
    public String toString() {
        return "Student{" +
                "学号='" + id + '\'' +
                ", 姓名='" + name + '\'' +
                ", 性别='" + gender + '\'' +
                ", 生日='" + birthday + '\'' +
                ", 数学=" + mathScore +
                ", Java=" + javaScore +
                ", 体育=" + peScore +
                ", 总分=" + getTotalScore() +
                '}';
    }
}
