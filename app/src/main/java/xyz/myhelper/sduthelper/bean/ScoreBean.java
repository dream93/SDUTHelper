package xyz.myhelper.sduthelper.bean;

/**
 * @version 1.0
 * @author dream
 * 成绩的内容
 * Created by dream on 15-11-23.
 */
public class ScoreBean {
    private String subject; //课程名称
    private String type; // 课程性质
    private String grade; // 学分
    private String ordinary; // 平时成绩
    private String midterm; //期中成绩
    private String end; //期末成绩
    private String test; // 实验成绩
    private String score; // 成绩
    private String isAgain; // 是否重修
    private String college; // 开课学院

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getOrdinary() {
        return ordinary;
    }

    public void setOrdinary(String ordinary) {
        this.ordinary = ordinary;
    }

    public String getMidterm() {
        return midterm;
    }

    public void setMidterm(String midterm) {
        this.midterm = midterm;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getIsAgain() {
        return isAgain;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setIsAgain(String isAgain) {
        this.isAgain = isAgain;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
