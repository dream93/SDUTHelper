package xyz.myhelper.sduthelper.utils.zfutils.utils;

/**
 * @author dream
 * @version 1.0
 * Created by dream on 15-11-19.
 */
public class StringUtil {
    /**
     * 判断是否是value
     *
     * @param valueToken 传过来的字符串
     * @param value      开始字符串
     * @return 反回的字符串
     */
    public static boolean isValue(String valueToken, String value) {
        if (valueToken.indexOf(value) != -1) {
            return true;
        }
        return false;
    }

    /**
     * 提取value值
     *
     * @param valueToken  传入
     * @param startString 开始
     * @param unStart     跳过字数
     * @return 返回字符串
     */
    public static String getValue(String valueToken, String startString,
                                  String endString, int unStart) {
        int start = valueToken.indexOf(startString);
        int end = valueToken.length();
        String tempStr = valueToken.substring(start + unStart, end);
        end = tempStr.indexOf(endString, unStart);
        if (end == -1) {
            end = tempStr.length();
        }
        return tempStr.substring(0, end);
    }

}
