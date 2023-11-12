package org.dongguk.jjoin.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class DateUtil {
    // yyyyMMdd 형식의 날짜 문자열을 입력받아서 Timestamp 객체로 반환
    public static Timestamp stringToTimestamp(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        simpleDateFormat.setLenient(false);
        Timestamp timestampDate = null;
        try {
            timestampDate = new Timestamp(simpleDateFormat.parse(date).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("잘못된 날짜 입력");
        }
        return timestampDate;
    }

    // startDate와 endDate 사이의 날짜들을 List<Timestamp>로 반환
    public List<Timestamp> getDates(String startDate, String endDate) {
        Timestamp startDay = stringToTimestamp(startDate);
        Timestamp endDay = stringToTimestamp(endDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDay);
        List<Timestamp> timestamps = new ArrayList<>();

        while (cal.getTime().getTime() < endDay.getTime()) {
            timestamps.add(new Timestamp(cal.getTime().getTime()));
            cal.add(Calendar.DATE, 1);
        }
        return timestamps;
    }
}
