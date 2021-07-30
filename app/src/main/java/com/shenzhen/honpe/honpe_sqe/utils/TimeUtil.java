package com.shenzhen.honpe.honpe_sqe.utils;

import android.view.Gravity;

import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter.TimeWheelAdapter;
import com.shenzhen.honpe.pickview.listener.ISelectTimeCallback;
import com.shenzhen.honpe.wheelview.listener.OnItemSelectedListener;
import com.shenzhen.honpe.wheelview.view.WheelView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.DATE_UPDATE;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.DATE_UPDATE2;

/**
 * FileName: TimeUtil
 * Author: asus
 * Date: 2021/3/31 17:18
 * Description:
 */
public class TimeUtil {
    public static String[] mealsLabels = {"年", "月", "日"};
    public static boolean[] isCyclic = {false, false, false};
    // 添加大小月月份并将其转换为list,方便之后的判断
    public static String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
    public static String[] months_little = {"4", "6", "9", "11"};
    public static List<String> list_big = Arrays.asList(months_big);
    public static List<String> list_little = Arrays.asList(months_little);
    public static int startYear = 1900;
    public static int endYear = 2100;
    public static int startMonth = 1;
    public static int endMonth = 12;
    public static int startDay = 1;
    public static int endDay = 31;
    public static SimpleDateFormat y = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat m = new SimpleDateFormat("MM");
    public static SimpleDateFormat d = new SimpleDateFormat("dd");
    private static ISelectTimeCallback mSelectChangeCallback;
    public static Calendar calendar = Calendar.getInstance();
    private static int currentYear;
    public static void setOnSelectChangeDate(ISelectTimeCallback selectChangeCallback) {
        mSelectChangeCallback = selectChangeCallback;
    }


    public static int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    public static int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static void InitWheelViewSet(List<WheelView> wheelViews, Calendar startCalendar, Calendar endCalendar) {
        for (int i = 0; i < wheelViews.size(); i++) {
            wheelViews.get(i).setTextSize(12);
            wheelViews.get(i).setLabel(mealsLabels[i]);
            wheelViews.get(i).setCyclic(isCyclic[i]);
            wheelViews.get(i).setIsOptions(true);
            wheelViews.get(i).setTextColorOut(0xFFa8a8a8);
            wheelViews.get(i).setTextColorCenter(0xFF057dff);
            wheelViews.get(i).setDividerColor(0xFF057dff);
            wheelViews.get(i).setTextDirection(0xFF03DAC5);
            wheelViews.get(i).setAlphaGradient(true);
            wheelViews.get(i).isCenterLabel(true);
            wheelViews.get(i).setGravity(Gravity.CENTER);
            wheelViews.get(i).setDividerType(WheelView.DividerType.WRAP);
            wheelViews.get(i).setLineSpacingMultiplier(2.0f);
        }

        currentYear = Integer.parseInt(y.format(startCalendar.getTime()));
        startYear = Integer.parseInt(y.format(startCalendar.getTime()));
        endYear = Integer.parseInt(y.format(endCalendar.getTime()));

        startMonth = Integer.parseInt(m.format(startCalendar.getTime()));

        startDay = Integer.parseInt(d.format(startCalendar.getTime()));

        wheelViews.get(0).setAdapter(new TimeWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        wheelViews.get(0).setCurrentItem(getYear() - startYear);// 初始化时显示的数据

        if (startYear == endYear) {//开始年等于终止年
            wheelViews.get(1).setAdapter(new TimeWheelAdapter(startMonth, endMonth));
            wheelViews.get(1).setCurrentItem(getMonth() + 1 - startMonth);
        } else if (getYear() == startYear) {
            //起始日期的月份控制
            wheelViews.get(1).setAdapter(new TimeWheelAdapter(startMonth, 12));
            wheelViews.get(1).setCurrentItem(getMonth() + 1 - startMonth);
        } else if (getYear() == endYear) {
            //终止日期的月份控制
            wheelViews.get(1).setAdapter(new TimeWheelAdapter(1, endMonth));
            wheelViews.get(1).setCurrentItem(getMonth());
        } else {
            wheelViews.get(1).setAdapter(new TimeWheelAdapter(1, 12));
            wheelViews.get(1).setCurrentItem(getMonth());
        }
        //日
        boolean leapYear = (getYear() % 4 == 0 && getYear() % 100 != 0) || getYear() % 400 == 0;
        if (startYear == endYear && startMonth == endMonth) {
            if (list_big.contains(String.valueOf(getMonth() + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wheelViews.get(2).setAdapter(new TimeWheelAdapter(startDay, endDay));
            } else if (list_big.contains(String.valueOf(getMonth() + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wheelViews.get(2).setAdapter(new TimeWheelAdapter(startDay, endDay));
            } else {
                // 闰年
                if (leapYear) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    wheelViews.get(2).setAdapter(new TimeWheelAdapter(startDay, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    wheelViews.get(2).setAdapter(new TimeWheelAdapter(startDay, endDay));
                }
            }
            wheelViews.get(2).setCurrentItem(getDay() - startDay);

        } else if (getYear() == startYear && getMonth() + 1 == startMonth) {
            // 起始日期的天数控制
            if (list_big.contains(String.valueOf(getMonth() + 1))) {

                wheelViews.get(2).setAdapter(new TimeWheelAdapter(startDay, 31));
            } else if (list_little.contains(String.valueOf(getMonth() + 1))) {
                wheelViews.get(2).setAdapter(new TimeWheelAdapter(startDay, 30));
            } else {
                // 闰年 29，平年 28
                wheelViews.get(2).setAdapter(new TimeWheelAdapter(startDay, leapYear ? 29 : 28));
            }
            wheelViews.get(2).setCurrentItem(getDay() - startDay);
        } else if (getYear() == endYear && getMonth() + 1 == endMonth) {
            // 终止日期的天数控制
            if (list_big.contains(String.valueOf(getMonth() + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wheelViews.get(2).setAdapter(new TimeWheelAdapter(1, endDay));
            } else if (list_little.contains(String.valueOf(getMonth() + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wheelViews.get(2).setAdapter(new TimeWheelAdapter(1, endDay));
            } else {
                // 闰年
                if (leapYear) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    wheelViews.get(2).setAdapter(new TimeWheelAdapter(1, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    wheelViews.get(2).setAdapter(new TimeWheelAdapter(1, endDay));
                }
            }
            wheelViews.get(2).setCurrentItem(getDay() - 1);
        } else {
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (list_big.contains(String.valueOf(getMonth() + 1))) {
                wheelViews.get(2).setAdapter(new TimeWheelAdapter(1, 31));
            } else if (list_little.contains(String.valueOf(getMonth() + 1))) {
                wheelViews.get(2).setAdapter(new TimeWheelAdapter(1, 30));
            } else {
                // 闰年 29，平年 28
                wheelViews.get(2).setAdapter(new TimeWheelAdapter(startDay, leapYear ? 29 : 28));
            }
            wheelViews.get(2).setCurrentItem(getDay() - 1);
        }

        wheelViews.get(0).setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int yearNum = index + startYear;
                currentYear = yearNum;
                int currentMonthItem = wheelViews.get(1).getCurrentItem(); //记录上一次的item位置
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (startYear == endYear) {
                    //重新设置月份
                    wheelViews.get(1).setAdapter(new TimeWheelAdapter(startMonth, endMonth));
                    if (currentMonthItem > wheelViews.get(1).getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wheelViews.get(1).getAdapter().getItemsCount() - 1;
                        wheelViews.get(1).setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + startMonth;
                    if (startMonth == endMonth) {
                        //重新设置日
                        setReDay(wheelViews, yearNum, monthNum, startDay, endDay, list_big, list_little);
                    }
                } else if (yearNum == startYear) {//等于开始的年
                    //重新设置月份
                    wheelViews.get(1).setAdapter(new TimeWheelAdapter(startMonth, 12));

                    if (currentMonthItem > wheelViews.get(1).getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wheelViews.get(1).getAdapter().getItemsCount() - 1;
                        wheelViews.get(1).setCurrentItem(currentMonthItem);
                    }

                    int month = currentMonthItem + startMonth;
                    if (month == startMonth) {
                        //重新设置日
                        setReDay(wheelViews, yearNum, month, startDay, 31, list_big, list_little);
                    } else {
                        //重新设置日
                        setReDay(wheelViews, yearNum, month, 1, 31, list_big, list_little);
                    }
                } else if (yearNum == endYear) {
                    //重新设置月份
                    wheelViews.get(1).setAdapter(new TimeWheelAdapter(1, endMonth));
                    if (currentMonthItem > wheelViews.get(1).getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wheelViews.get(1).getAdapter().getItemsCount() - 1;
                        wheelViews.get(1).setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + 1;

                    if (monthNum == endMonth) {
                        //重新设置日
                        setReDay(wheelViews, yearNum, monthNum, 1, endDay, list_big, list_little);
                    } else {
                        //重新设置日
                        setReDay(wheelViews, yearNum, monthNum, 1, 31, list_big, list_little);
                    }
                } else {
                    //重新设置月份
                    wheelViews.get(1).setAdapter(new TimeWheelAdapter(1, 12));
                    //重新设置日
                    setReDay(wheelViews, yearNum, wheelViews.get(1).getCurrentItem() + 1, 1, 31, list_big, list_little);
                }
                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        // 添加"月"监听
        wheelViews.get(1).setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + 1;

                if (startYear == endYear) {
                    month_num = month_num + startMonth - 1;
                    if (startMonth == endMonth) {
                        //重新设置日
                        setReDay(wheelViews, currentYear, month_num, startDay, endDay, list_big, list_little);
                    } else if (startMonth == month_num) {

                        //重新设置日
                        setReDay(wheelViews, currentYear, month_num, startDay, 31, list_big, list_little);
                    } else if (endMonth == month_num) {
                        setReDay(wheelViews, currentYear, month_num, 1, endDay, list_big, list_little);
                    } else {
                        setReDay(wheelViews, currentYear, month_num, 1, 31, list_big, list_little);
                    }
                } else if (currentYear == startYear) {
                    month_num = month_num + startMonth - 1;
                    if (month_num == startMonth) {
                        //重新设置日
                        setReDay(wheelViews, currentYear, month_num, startDay, 31, list_big, list_little);
                    } else {
                        //重新设置日
                        setReDay(wheelViews, currentYear, month_num, 1, 31, list_big, list_little);
                    }

                } else if (currentYear == endYear) {
                    if (month_num == endMonth) {
                        //重新设置日
                        setReDay(wheelViews, currentYear, wheelViews.get(1).getCurrentItem() + 1, 1, endDay, list_big, list_little);
                    } else {
                        setReDay(wheelViews, currentYear, wheelViews.get(1).getCurrentItem() + 1, 1, 31, list_big, list_little);
                    }

                } else {
                    //重新设置日
                    setReDay(wheelViews, currentYear, month_num, 1, 31, list_big, list_little);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });
        wheelViews.get(2).setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });
    }

    private static void setReDay(List<WheelView> wheelViews, int yearNum, int monthNum, int startDay, int endDay, List<String> list_big, List<String> list_little) {
        int currentItem = wheelViews.get(2).getCurrentItem();
        if (list_big.contains(String.valueOf(monthNum))) {
            if (endDay > 31) {
                endDay = 31;
            }
            wheelViews.get(2).setAdapter(new TimeWheelAdapter(startDay, endDay));
        } else if (list_little.contains(String.valueOf(monthNum))) {
            if (endDay > 30) {
                endDay = 30;
            }
            wheelViews.get(2).setAdapter(new TimeWheelAdapter(startDay, endDay));
        } else {
            if (yearNum % 4 == 0 && yearNum % 100 != 0 || yearNum % 400 == 0) {
                if (endDay > 29) {
                    endDay = 29;
                }
                wheelViews.get(2).setAdapter(new TimeWheelAdapter(startDay, endDay));
            } else {
                if (endDay > 28) {
                    endDay = 28;
                }
                wheelViews.get(2).setAdapter(new TimeWheelAdapter(startDay, endDay));
            }
        }
        if (currentItem > wheelViews.get(2).getAdapter().getItemsCount() - 1) {
            currentItem = wheelViews.get(2).getAdapter().getItemsCount() - 1;
            wheelViews.get(2).setCurrentItem(currentItem);
        }
    }

    public static String getTime(List<WheelView> wheelViews) {
        StringBuilder sb = new StringBuilder();
        if (currentYear == startYear) {
            if ((wheelViews.get(1).getCurrentItem() + startMonth) == startMonth) {
                sb.append((wheelViews.get(0).getCurrentItem() + startYear)).append("-")
                        .append((wheelViews.get(1).getCurrentItem() + startMonth)).append("-")
                        .append((wheelViews.get(2).getCurrentItem() + startDay)).append(" ");
            } else {
                sb.append((wheelViews.get(0).getCurrentItem() + startYear)).append("-")
                        .append((wheelViews.get(1).getCurrentItem() + startMonth)).append("-")
                        .append((wheelViews.get(2).getCurrentItem() + 1)).append(" ");
            }
        } else {
            sb.append((wheelViews.get(0).getCurrentItem() + startYear)).append("-")
                    .append((wheelViews.get(1).getCurrentItem() + 1)).append("-")
                    .append((wheelViews.get(2).getCurrentItem() + 1)).append(" ");
        }
        return sb.toString();
    }
    public static void setChangedListener(List<WheelView> wheelViews) {
        if (mSelectChangeCallback != null) {
            for (int i = 0; i < wheelViews.size(); i++) {
                wheelViews.get(i).setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        mSelectChangeCallback.onTimeSelectChanged();
                    }
                });
            }
        }
    }
}


