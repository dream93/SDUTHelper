package xyz.myhelper.sduthelper.dataparse;

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
        for (int i = 1; i < trElements.size(); i++) {
            Elements subjectInfo = trElements.get(i).select("td");
            GpaBean gpa = new GpaBean();
            gpa.setSerial(subjectInfo.get(0).text().replaceAll("\\s*", ""));
            gpa.setSchoolYear(subjectInfo.get(1).text().replaceAll("\\s*", ""));
            gpa.setTerm(subjectInfo.get(2).text().replaceAll("\\s*", ""));
            gpa.setClasses(subjectInfo.get(3).text().replaceAll("\\s*", ""));
            gpa.setSubject(subjectInfo.get(5).text().replaceAll("\\s*", ""));
            gpa.setGrade(subjectInfo.get(7).text().replaceAll("\\s*", ""));
            gpa.setMethod(subjectInfo.get(8).text().replaceAll("\\s*", ""));
            gpa.setOriginalGrade(subjectInfo.get(9).text().replaceAll("\\s*", ""));
            gpa.setNewGrade(subjectInfo.get(10).text().replaceAll("\\s*", ""));
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
