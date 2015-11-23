package xyz.myhelper.sduthelper.dataparse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import xyz.myhelper.sduthelper.bean.ScoreBean;

/**
 * 成绩解析类
 * Created by dream on 15-11-23.
 */
public class ScoreParse {
    public static List<ScoreBean> getScoreList(String s){
        List<ScoreBean> scoreLists = new ArrayList<>();
        Document document = Jsoup.parse(s);
        Element table = document.select("table").get(1);
        Elements trs = table.select("tr");
        for (int i = 0; i < trs.size(); i++){
            Elements tds = trs.get(i).select("td");
            ScoreBean score = new ScoreBean();
            score.setSubject(tds.get(3).text());
            score.setType(tds.get(4).text());
            score.setGrade(tds.get(6).text());
            score.setOrdinary(tds.get(7).text());
            score.setMidterm(tds.get(8).text());
            score.setEnd(tds.get(9).text());
            score.setTest(tds.get(10).text());
            score.setScore(tds.get(11).text());
            score.setIsAgain(tds.get(13).text());
            score.setCollege(tds.get(14).text());
            scoreLists.add(score);
        }
        return scoreLists;
    }
}
