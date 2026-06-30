==================== 学生成绩管理系统使用说明 ====================
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

