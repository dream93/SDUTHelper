package xyz.myhelper.sduthelper.bean;

/**
 * Created by dream on 15-11-23.
 */
public class CetBean {
    private String num; // 序号
    private String id; // 准考证号
    private String time; // 考试时间
    private String type; // 课程名称
    private String score; // 成绩

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
