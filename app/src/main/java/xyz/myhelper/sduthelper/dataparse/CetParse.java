package xyz.myhelper.sduthelper.dataparse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import xyz.myhelper.sduthelper.bean.CetBean;

/**
 * Created by dream on 15-11-23.
 */
public class CetParse {

    public static List<CetBean> getCetList(String s){
        List<CetBean> cetLists = new ArrayList<>();
        Document cetDoc = Jsoup.parse(s);
        Element tab = cetDoc.select("table").get(0).select("table").get(1);
        Elements trs = tab.select("tr");
        for (int i = 1; i < trs.size(); i++){
            CetBean cet = new CetBean();
            Elements tds = trs.select("td");
            cet.setNum(tds.get(0).text());
            cet.setId(tds.get(3).text());
            cet.setTime(tds.get(4).text());
            cet.setType(tds.get(5).text());
            cet.setScore(tds.get(6).text());
            cetLists.add(cet);
        }
        return cetLists;

    }
}
