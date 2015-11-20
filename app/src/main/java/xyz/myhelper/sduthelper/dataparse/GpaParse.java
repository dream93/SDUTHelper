package xyz.myhelper.sduthelper.dataparse;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.myhelper.sduthelper.bean.GpaBean;
import xyz.myhelper.sduthelper.bean.StudentInfo;

/**
 * @author dream
 * @version 1.0
 * 绩点的解析类
 * Created by dream on 15-11-20.
 */
public class GpaParse {
    public static List<GpaBean> getGPAList(File html, StudentInfo myInfo) {
        List<GpaBean> gpaLists = new ArrayList<>();
        Document cjdoc = null;
        try {
            cjdoc = Jsoup.parse(html, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 解析成绩数据
        Element gradeInfo = cjdoc.select("table[width=100%][border=1][align=center][cellspacing=0][cellpadding=1]").get(2);
        Elements trElements = gradeInfo.select("tr");
        Log.i("MyTag", "the size = " + trElements.size());
        for (int i = 1; i < trElements.size(); i++) {
            Elements subjectInfo = trElements.get(i).select("td");
            if (subjectInfo.get(0).text().contains("没有")){
                return null;
            }
            GpaBean gpa = new GpaBean();
            gpa.setSerial(subjectInfo.get(0).text());
            gpa.setSchoolYear(subjectInfo.get(1).text());
            gpa.setTerm(subjectInfo.get(2).text());
            gpa.setClasses(subjectInfo.get(3).text());
            gpa.setSubject(subjectInfo.get(5).text());
            gpa.setGrade(subjectInfo.get(7).text());
            gpa.setOriginalGrade(subjectInfo.get(9).text());
            gpa.setNewGrade(subjectInfo.get(10).text());
            gpaLists.add(gpa);
        }

        Element info = cjdoc.select("table[width=100%][border=1][align=center][cellspacing=0][cellpadding=1]").get(0);
        Elements getInfo = info.select("tr").get(1).select("td");

        // 解析个人数据
        myInfo.setXh(getInfo.get(0).text());
        myInfo.setName(getInfo.get(1).text());
        myInfo.setSex(getInfo.get(2).text());
        myInfo.setGradeClass(getInfo.get(3).text());
        myInfo.setMajor(getInfo.get(5).text());
        myInfo.setClase(getInfo.get(6).text());
        return gpaLists;
    }
}
