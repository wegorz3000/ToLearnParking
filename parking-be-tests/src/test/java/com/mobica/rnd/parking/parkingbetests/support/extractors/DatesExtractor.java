package com.mobica.rnd.parking.parkingbetests.support.extractors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by int_eaja on 2017-08-08.
 */
public class DatesExtractor implements IDataExtractor {

    private Date startDate, endDate;

    @Override
    public JSONObject[] extractData(JSONObject obj, String key) {
        startDate = null;
        endDate = null;
        JSONArray data = (JSONArray) obj.get("data");
        JSONObject datesObj = (JSONObject) data.get(0);
        Object startObj = datesObj.get("start");
        String startDiff = startObj instanceof String ? (String) startObj : null;
        Object endObj = datesObj.get("end");
        String endDiff = endObj instanceof String ? (String) endObj : null;
        initDates(startDiff, endDiff);
        return new JSONObject[]{initDates(startDiff, endDiff).getDates()};
    }

    private DatesExtractor getDate(Date base, int diff, int type) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(base);
        c.add(type, diff);
        Date dres = c.getTime();
        base.setTime(c.getTime().getTime());
        return this;
    }

    private String format(Date dres) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(dres);// c.getTime();
    }

    private DatesExtractor initDates(String startDaysDiff, String endDaysDiff) {
        Date now = new Date();
        if (startDaysDiff != null) {
            String[] startDiffs = startDaysDiff.split("\\s+");
            startDate = new Date(now.getTime());
            getDate(startDate, getCount('m', startDiffs), Calendar.MONTH)
                    .getDate(startDate, getCount('d', startDiffs), Calendar.DATE);
        }
        if (endDaysDiff != null) {
            String[] endDiffs = endDaysDiff.split("\\s+");
            endDate = new Date(now.getTime());
            getDate(endDate, getCount('m', endDiffs), Calendar.MONTH)
                    .getDate(endDate, getCount('d', endDiffs), Calendar.DATE);
        }

        return this;
    }

    private JSONObject getDates() {

        String startDataStr = startDate == null ? null : format(startDate);
        String endDateStr = endDate == null ? null : format(endDate);
        JSONObject o = new JSONObject();
        o.put("start", startDataStr);
        o.put("end", endDateStr);
        return o;
    }


    private int getCount(char type, String[] diffs) {
        int res = 0;
        for (String x : diffs) {
            if (x.indexOf(type) > 0) {
                res = Integer.parseInt(x.substring(0, x.indexOf(type)));
                break;
            }
        }
        return res;
    }
}
