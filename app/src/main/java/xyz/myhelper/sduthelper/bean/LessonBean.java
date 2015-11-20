package xyz.myhelper.sduthelper.bean;

/**
 * 每节课的上课信息
 * Created by dream on 15-11-20.
 */
public class LessonBean {
    private String subject; // 课程名字
    private String time; // 课程时间
    private String techer; // 教师名字
    private String place; // 上课地点

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTecher() {
        return techer;
    }

    public void setTecher(String techer) {
        this.techer = techer;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
