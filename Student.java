public class Student {
    private String id;
    private String name;
    private String gender;
    private String birthday;
    private double mathScore;
    private double javaScore;
    private double peScore;

    public Student() {
    }

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

    // 将学生对象转为易读的字符串，方便打印输出和调试（System.out.println(学生对象) 时会自动调用）

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
