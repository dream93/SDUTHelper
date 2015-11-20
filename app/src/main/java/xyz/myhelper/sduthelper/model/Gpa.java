package xyz.myhelper.sduthelper.model;

/**
 * Created by dream on 15-11-19.
 */
public class Gpa {
    private int id;
    private String subject;
    private String grade;
    private String score;
    private String newScore;
    private String userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNewScore() {
        return newScore;
    }

    public void setNewScore(String newScore) {
        this.newScore = newScore;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
