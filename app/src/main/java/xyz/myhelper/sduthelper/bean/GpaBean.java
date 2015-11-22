package xyz.myhelper.sduthelper.bean;

/**
 * @author dream
 * @version 1.0
 * 绩点的信息
 * Created by dream on 15-11-20.
 */
public class GpaBean {
    private String serial; // 序号
    private String schoolYear; // 学年 本应用暂未将此数据列出
    private String term; // 学期 本应用暂未将此数据列出
    private String classes; // 类别
    // private String number; // 课程编号 成绩查询系统暂未开通此项
    private String subject; // 课程名称
    // private String perod; // 学时 成绩查询系统暂未开通此项
    private String grade; // 学分
    private String method; // 考核方式 辨别是否为双学位
    private String originalGrade; //原考成绩
    private String newGrade; // 补考成绩
    // private String courseGpa; // 课程绩点 成绩查询系统暂未开通
    // private String teacher; // 教师 成绩查询系统暂未开通


    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOriginalGrade() {
        return originalGrade;
    }

    public void setOriginalGrade(String originalGrade) {
        this.originalGrade = originalGrade;
    }

    public String getNewGrade() {
        return newGrade;
    }

    public void setNewGrade(String newGrade) {
        this.newGrade = newGrade;
    }

}
