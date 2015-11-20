package xyz.myhelper.sduthelper.dataparse;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.myhelper.sduthelper.bean.LessonBean;
import xyz.myhelper.sduthelper.bean.ScheduleBean;
import xyz.myhelper.sduthelper.bean.StudentInfo;

/**
 * @author dream
 * @version 1.o
 *          Created by dream on 15-11-19.
 */
public class ScheduleParse {
    // 解析课表数据
    public static List<ScheduleBean> getSchLists(File htmlFile, StudentInfo info) {
        List<ScheduleBean> mLists = new ArrayList<>();
        Document cjdoc = null;
        try {
            cjdoc = Jsoup.parse(htmlFile, "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 得到所有的表格
        if (cjdoc == null) {
            return mLists;
        }
        Elements tables = cjdoc.select("table");

        // 得到学生信息
        Elements infoSpan = tables.get(0).select("tr").get(1).select("td").get(0).select("span");
        info.setXh(infoSpan.get(0).text());
        info.setName(infoSpan.get(1).text());
        info.setAcademy(infoSpan.get(2).text());
        info.setMajor(infoSpan.get(3).text());
        info.setClase(infoSpan.get(4).text());

        // 得到课表信息
        Elements scheduleInfo = tables.get(1).select("tr");
        for (int i = 2; i < scheduleInfo.size() - 2; i += 2) {
            Elements td = scheduleInfo.get(i).select("td");
            ScheduleBean schedule = new ScheduleBean();
            if (i / 2 == 1) {
                schedule.setPitchNumber("第" + "一 二" + "节");
            } else if (i / 2 == 2) {
                schedule.setPitchNumber("第" + "三 四" + "节");
            } else if (i / 2 == 3) {
                schedule.setPitchNumber("第" + "五 六" + "节");
            } else if (i / 2 == 4) {
                schedule.setPitchNumber("第" + "七 八" + "节");
            } else if (i / 2 == 5) {
                schedule.setPitchNumber("第" + "九 十" + "节");
            }

            int t  = 0;
            if (i / 2 == 1 || i / 2 == 3 || i / 2 == 5) {
                t = 1;
            }


            for (int x = 1; x < td.size(); x++) {
                LessonBean[] lessonArr;
                if (x == 8) {
                    break;
                }
                String temp = td.get(x + t).text();
                if ("".equals(temp) || " ".equals(temp) || "  ".equals(temp)) {
                    lessonArr = null;
                } else {
                    String[] strArr = temp.split(" ");
                    int num = strArr.length / 4;
                    lessonArr = new LessonBean[num];
                    for (int j = 0; j < num; j++) {
                        LessonBean lesson = new LessonBean();
                        lesson.setSubject(strArr[j * 4]);
                        lesson.setTime(strArr[j * 4 + 1]);
                        lesson.setTecher(strArr[j * 4 + 2]);
                        lesson.setPlace(strArr[j * 4 + 3]);
                        lessonArr[j] = lesson;
                    }
                }
                switch (x) {
                    case 1:
                        schedule.setMonLesson(lessonArr);
                        break;
                    case 2:
                        schedule.setTueLesson(lessonArr);
                        break;
                    case 3:
                        schedule.setWedLesson(lessonArr);
                        break;
                    case 4:
                        schedule.setThuLesson(lessonArr);
                        break;
                    case 5:
                        schedule.setFriLesson(lessonArr);
                        break;
                    case 6:
                        schedule.setSatLesson(lessonArr);
                        break;
                    case 7:
                        schedule.setSunLesson(lessonArr);
                        break;
                }
            }
            mLists.add(schedule);
        }
        return mLists;
    }
}