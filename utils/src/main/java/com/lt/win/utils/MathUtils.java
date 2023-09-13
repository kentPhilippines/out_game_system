package com.lt.win.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathUtils {
    private static final String DEFAULT_DIGITS = "0";
    private static final String FIRST_DEFAULT_DIGITS = "1";

    /**
     * @param target 目标数字
     * @param length 需要补充到的位数, 补充默认数字[0], 第一位默认补充[1]
     * @return 补充后的结果
     */
    public static String makeUpNewData(String target, int length) {
        return makeUpNewData(target, length, DEFAULT_DIGITS);
    }

    /**
     * @param target 目标数字
     * @param length 需要补充到的位数
     * @param add    需要补充的数字, 补充默认数字[0], 第一位默认补充[1]
     * @return 补充后的结果
     */
    public static String makeUpNewData(String target, int length, String add) {
        if (target.startsWith("-")) target.replace("-", "");
        if (target.length() >= length) return target.substring(0, length);
        StringBuffer sb = new StringBuffer(FIRST_DEFAULT_DIGITS);
        for (int i = 0; i < length - (1 + target.length()); i++) {
            sb.append(add);
        }
        return sb.append(target).toString();
    }

    /**
     * 生产一个随机的指定位数的字符串数字
     *
     * @param length
     * @return
     */
    public static String randomDigitNumber(int length) {
        int start = Integer.parseInt(makeUpNewData("", length));//1000+8999=9999
        int end = Integer.parseInt(makeUpNewData("", length + 1)) - start;//9000
        return (int) (Math.random() * end) + start + "";
    }

    /**
     * 保留两位小数（不四舍五入）
     *
     * @param bigDecimal
     * @return
     */
    public static BigDecimal tranBigDecimal(BigDecimal bigDecimal) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);// 不四舍五入
        return new BigDecimal(decimalFormat.format(bigDecimal));
    }

    /**
     * 保留四位小数（不四舍五入）
     *
     * @param bigDecimal
     * @return
     */
    public static BigDecimal tranBigDecimalFour(BigDecimal bigDecimal) {
        DecimalFormat decimalFormat = new DecimalFormat(".0000");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);// 不四舍五入
        return new BigDecimal(decimalFormat.format(bigDecimal));
    }

    public static BigDecimal objectTranBigDecimal(Object object) {
        if (object instanceof BigDecimal) {
            return tranBigDecimal((BigDecimal) object);
        } else if (object instanceof Double) {
            return tranBigDecimal(new BigDecimal((Double) object));
        }
        return new BigDecimal(0);
    }
}
