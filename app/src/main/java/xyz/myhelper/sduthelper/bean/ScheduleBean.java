package xyz.myhelper.sduthelper.bean;

/**
 * Created by dream on 15-11-19.
 * 同一节课，不同天数的课程信息
 */
public class ScheduleBean {

    private int num; // 课程的节数
    private String pitchNumber;
    private LessonBean[] monLesson;
    private LessonBean[] tueLesson;
    private LessonBean[] wedLesson;
    private LessonBean[] thuLesson;
    private LessonBean[] friLesson;
    private LessonBean[] satLesson;
    private LessonBean[] sunLesson;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPitchNumber() {
        return pitchNumber;
    }

    public void setPitchNumber(String pitchNumber) {
        this.pitchNumber = pitchNumber;
    }

    public LessonBean[] getMonLesson() {
        return monLesson;
    }

    public void setMonLesson(LessonBean[] monLesson) {
        this.monLesson = monLesson;
    }

    public LessonBean[] getTueLesson() {
        return tueLesson;
    }

    public void setTueLesson(LessonBean[] tueLesson) {
        this.tueLesson = tueLesson;
    }

    public LessonBean[] getWedLesson() {
        return wedLesson;
    }

    public void setWedLesson(LessonBean[] wedLesson) {
        this.wedLesson = wedLesson;
    }

    public LessonBean[] getThuLesson() {
        return thuLesson;
    }

    public void setThuLesson(LessonBean[] thuLesson) {
        this.thuLesson = thuLesson;
    }

    public LessonBean[] getFriLesson() {
        return friLesson;
    }

    public void setFriLesson(LessonBean[] friLesson) {
        this.friLesson = friLesson;
    }

    public LessonBean[] getSatLesson() {
        return satLesson;
    }

    public void setSatLesson(LessonBean[] satLesson) {
        this.satLesson = satLesson;
    }

    public LessonBean[] getSunLesson() {
        return sunLesson;
    }

    public void setSunLesson(LessonBean[] sunLesson) {
        this.sunLesson = sunLesson;
    }
}