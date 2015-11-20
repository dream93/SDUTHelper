package xyz.myhelper.sduthelper.bean;

/**
 * @author dream
 * @version 1.0
 * Created by dream on 15-11-20.
 * 学生信息类
 */
public class StudentInfo {
    private String name; // 姓名
    private String xh; // 学号
    private String sex; // 性别
    private String gradeClass; // 年级
    private String academy; // 学院
    private String major; // 专业
    private String clase; // 班级

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGradeClass() {
        return gradeClass;
    }

    public void setGradeClass(String gradeClass) {
        this.gradeClass = gradeClass;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }
}
