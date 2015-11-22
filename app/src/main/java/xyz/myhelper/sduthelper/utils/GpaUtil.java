package xyz.myhelper.sduthelper.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import xyz.myhelper.sduthelper.bean.GpaBean;
import xyz.myhelper.sduthelper.bean.NewGpaBean;
import xyz.myhelper.sduthelper.model.Schedule;

/**
 * Created by dream on 15-11-22.
 * 绩点计算的工具类
 */
public class GpaUtil {

    /**
     * @param gpaBeanList 所有成绩的集合
     * @return 返回第一专业和第二专业的绩点
     */
    public double[] getGpa(List<GpaBean> gpaBeanList) {
        List<GpaBean> oneLists = new ArrayList<>();
        List<GpaBean> twoLists = new ArrayList<>();
        // 循环遍历
        for (int i = 0; i < gpaBeanList.size(); i++){
            GpaBean gpaBean = gpaBeanList.get(i);
            // 第二学位的
            if ("第二".equals(gpaBean.getMethod()) || "第二专业".equals(gpaBean.getMethod())){
                twoLists.add(gpaBean);
            }else if (!"公选课".equals(gpaBean.getClasses())){ // 不是公选课才计算
                oneLists.add(gpaBean);
            }
        }

        // 得到新的集合
        List<NewGpaBean> newTwoList = changeScore(twoLists);
        List<NewGpaBean> newOneList = changeScore(oneLists);

        // 由新集合计算绩点
        double[] gpa = new double[]{getGpaNum(newOneList), getGpaNum(newTwoList)};
        return gpa;
    }

    private static double getGpaNum(List<NewGpaBean> mLists){
        for (int i = 0; i<mLists.size(); i++){
            for (int j = 0; j < i; j++){ // 查询是否有重复的
                if (mLists.get(i).getSubject().equals(mLists.get(j).getSubject())){ // 存在重复的
                    // 重复科目只取最高分
                    if (mLists.get(i).getScore() > mLists.get(j).getScore()){
                        mLists.remove(j);
                    }else {
                        mLists.remove(i);
                    }
                }
            }
        }

        // 再次循环计算绩点信息
        double sumScore = 0; //总成绩
        double sumGrade = 0; // 总学分
        for (int i= 0; i < mLists.size(); i++){
            Log.i("MyTag",mLists.get(i).getSubject() + "--" + mLists.get(i).getGrade() +"===" + mLists.get(i).getScore());
            sumGrade += mLists.get(i).getGrade();
            sumScore += mLists.get(i).getScore() * mLists.get(i).getGrade();
        }
        return (sumScore/sumGrade);
    }

    /**
     *
     * @param mLists 需要转换分数的集合
     */
    // 将分数转换,变成只有科目、学分和分数的集合
    private List<NewGpaBean> changeScore(List<GpaBean> mLists){
        List<NewGpaBean> newLists = new ArrayList<>();
        for (int i = 0; i < mLists.size(); i++) {
            GpaBean gpaBean = mLists.get(i);

            // 新集合中的学分和科目名不变
            NewGpaBean newGpaBean = new NewGpaBean();
            newGpaBean.setGrade(Double.parseDouble(gpaBean.getGrade()));
            newGpaBean.setSubject(gpaBean.getSubject());

            // 有补考成绩，先计算补考成绩
            if (gpaBean.getNewGrade() != null && !"".equals(gpaBean.getNewGrade())) {
                // 补考没有通过的
                if ("缺考".equals(gpaBean.getNewGrade())
                        || Double.parseDouble(gpaBean.getNewGrade()) < 60) {
                    newGpaBean.setScore(0);
                } else { // 补考通过的算60分
                    newGpaBean.setScore(60);
                }
            } else {
                // 先将两级制和五级制的分数替换为百分制
                if ("不及格".equals(gpaBean.getOriginalGrade())) {
                    newGpaBean.setScore(0);
                } else if ("及格".equals(gpaBean.getOriginalGrade())) {
                    newGpaBean.setScore(62);
                } else if ("中等".equals(gpaBean.getOriginalGrade())) {
                    newGpaBean.setScore(73);
                } else if ("良好".equals(gpaBean.getOriginalGrade()) ||
                        "良".equals(gpaBean.getOriginalGrade())) {
                    newGpaBean.setScore(84);
                } else if ("优秀".equals(gpaBean.getOriginalGrade())) {
                    newGpaBean.setScore(95);
                } else if ("合格".equals(gpaBean.getOriginalGrade())) {
                    newGpaBean.setScore(70);
                } else if ("不合格".equals(gpaBean.getOriginalGrade())) {
                    newGpaBean.setScore(0);
                } else if ("缓考".equals(gpaBean.getOriginalGrade())) {
                    newGpaBean.setScore(0);
                } else if (isNumeric(gpaBean.getOriginalGrade())) { // 是数字型
                    if (Double.parseDouble(gpaBean.getOriginalGrade()) < 60) {
                        newGpaBean.setScore(0);
                    } else {
                        newGpaBean.setScore(Double.parseDouble(gpaBean.getOriginalGrade()));
                    }
                } else { // 有特殊情况没有考虑
                    newGpaBean.setScore(60); // 疏漏的暂定为及格
                    LogUtil.setNoClassLog("绩点有其它字符串：" + gpaBean.getOriginalGrade());
                }
            }
            // 将转换后的加入到集合中
            newLists.add(newGpaBean);
        }
        return newLists;
    }

    // 判断是否是数字
    private boolean isNumeric(String str){
        try {
            double num=Double.valueOf(str);//字符串强制转换数字
            return true;//数字返True
        } catch (Exception e) {
            return false;//抛异返False
        }
    }
}
