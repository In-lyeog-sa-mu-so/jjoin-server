package org.dongguk.jjoin.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    // yyyyMMdd 형식의 날짜 문자열을 입력받아서 Timestamp 객체로 반환
    public static Timestamp stringToTimestamp(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        simpleDateFormat.setLenient(false);
        Timestamp timestampDate = null;
        try {
            Date stringToDate = simpleDateFormat.parse(date);
            timestampDate = new Timestamp(stringToDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return timestampDate;
    }

    // yyyyMMdd 형식의 날짜 문자열을 입력받아서 Timestamp 객체로 반환
    public static String timestampToString(Timestamp date) {
        return String.format("%d%02d%02d", date.getYear() + 1900, date.getMonth()+1, date.getDate());
    }

    // 해당 주차의 시작 날짜를 Timestamp 객체로 반환
    public static Timestamp dateWeekStartDate(String date, int weekNum) {
        Timestamp targetDate = stringToTimestamp(date);
        int day1 = targetDate.getDay();
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDate);

        // 만약 dateWeek가 가리키는 월의 1일이 목요일 이전에 시작한다면 해당 월 1주차에 이전월 날짜 포함
        if (day1 < 4) {
            cal.add(Calendar.DATE, -day1 + 7*(weekNum - 1));
            return new Timestamp(cal.getTime().getTime());
        } else {
            cal.add(Calendar.DATE, 8 - day1 + 7*(weekNum - 1));
            return new Timestamp(cal.getTime().getTime());
        }
    }

    // 주차에 해당하는 모든 날짜를 순서대로 저장한 List<Timestamp> 객체 반환
    public static List<Timestamp> weekDays(String dateWeek) {
        Timestamp startDate = dateWeekStartDate(dateWeek.substring(0, 6) + "01", dateWeek.charAt(6) - '0');
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);

        List<Timestamp> timestampList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            timestampList.add(new Timestamp(cal.getTime().getTime()));
            cal.add(Calendar.DATE, 1);
        }

        return timestampList;
    }

    // 월에 해당하는 모든 날짜를 순서대로 저장한 List<Timestamp> 객체 반환
    public static List<Timestamp> monthDays(String dateMonth) {
        Timestamp startDate = stringToTimestamp(dateMonth + "02");
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.MONTH, 1);
        Timestamp endDate = new Timestamp(cal.getTime().getTime());
        cal.setTime(startDate);

        List<Timestamp> timestampList = new ArrayList<>();
        while (cal.getTime().getTime() < endDate.getTime()) {
            timestampList.add(new Timestamp(cal.getTime().getTime()));
            cal.add(Calendar.DATE, 1);
        }

        return timestampList;
    }
}
