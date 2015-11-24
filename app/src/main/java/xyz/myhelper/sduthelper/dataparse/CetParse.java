package xyz.myhelper.sduthelper.dataparse;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import xyz.myhelper.sduthelper.bean.CetBean;

/**
 * CET成绩的解析类
 * Created by dream on 15-11-23.
 */
public class CetParse {

    public static List<CetBean> getCetList(String s){
        List<CetBean> cetLists = new ArrayList<>();
        Document cetDoc = Jsoup.parse(s);
        Elements trs = cetDoc.select("table").get(2).select("tr");
        for (int i = 0; i < trs.size(); i++){
            CetBean cet = new CetBean();
            if (i == 0){
                Elements ths = trs.get(i).select("th");
                cet.setNum(ths.get(0).text());
                cet.setId(ths.get(3).text());
                cet.setTime(ths.get(4).text().replaceAll("\\s*", ""));
                cet.setType(ths.get(5).text());
                cet.setScore(ths.get(6).text().replaceAll("\\s*", ""));
                cetLists.add(cet);
            }else {
                Elements tds = trs.get(i).select("td");
                cet.setNum(tds.get(0).text());
                cet.setId(tds.get(3).text());
                cet.setTime(tds.get(4).text());
                cet.setType(tds.get(5).text());
                cet.setScore(tds.get(6).text());
                cetLists.add(cet);
            }
        }
        return cetLists;

    }
}
