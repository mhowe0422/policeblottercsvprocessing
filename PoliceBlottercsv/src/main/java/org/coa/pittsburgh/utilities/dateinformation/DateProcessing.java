/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coa.pittsburgh.utilities.dateinformation;

/**
 *
 * @author Mark
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class DateProcessing {

    private String gYesterdayExample;
    private String gsdf = "MM/dd/yyyy";
    private GregorianCalendar gc;
    private int gdaysback = 1;
    boolean invokedStandalone = false;
    private String YesterdayIncident;
    private String YesterdayIncidentFileName;
    private String ArrestBlotterFileName;
    private String IncidentDate;

    Calendar calendar = null;
    SimpleDateFormat sdf = null;
    SimpleTimeZone edt = null;
    String[] ids = null;
//  private int gCurrentSecond = 0;

    public DateProcessing() {
        ids = TimeZone.getAvailableIDs(-5 * 60 * 60 * 1000);
        edt = new SimpleTimeZone(-5 * 60 * 60 * 1000, ids[0]);
        edt.setStartRule(Calendar.MARCH, 2, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        edt.setEndRule(Calendar.NOVEMBER, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

        calendar = new GregorianCalendar();
        calendar.setTimeZone(edt);

        sdf = new SimpleDateFormat();
        //setDaysBack(1);

    }

    public void Mydate() {
        //gc = new GregorianCalendar();
        calendar.add(Calendar.DATE, -getDaysBack());
        //gc.add(Calendar.DATE, -getDaysBack());
        //Date todayMinusOne = gc.getTime();
        Date todayMinusOne = calendar.getTime();
        System.out.println("getTime() " + todayMinusOne);
        System.out.println("sdf 1 " + getSimpleDateFormat());
        sdf.applyPattern(getSimpleDateFormat());
        //sdf = new SimpleDateFormat(getSimpleDateFormat());
        System.out.println(sdf.format(todayMinusOne));
        setYesterdayIncident(sdf.format(todayMinusOne));
        System.out.println("Yesterday Incident "+ getYesterdayIncident());

        //sdf.applyPattern(gsdf);
        setSimpleDateFormat("EEEEyyyyMMdd");
        //sdf = new SimpleDateFormat(getSimpleDateFormat());
        sdf.applyPattern(getSimpleDateFormat());
        System.out.println("sdf 2 " + getSimpleDateFormat());
        System.out.println(sdf.format(todayMinusOne));
        setYesterdayIncidentFileName(sdf.format(todayMinusOne).toLowerCase());
        setArrestBlotterFileName("blotter_"+sdf.format(todayMinusOne).toLowerCase()+".csv");
        System.out.println(getArrestBlotterFileName());
        
        
        //setSimpleDateFormat("EEEE");
        //sdf = new SimpleDateFormat(getSimpleDateFormat());
        //sdf.applyPattern(getSimpleDateFormat());
        //System.out.println("sdf 2 " + getSimpleDateFormat());
        //System.out.println(sdf.format(todayMinusOne));
        //setArrestBlotterFileName("arrest_blotter_"+sdf.format(todayMinusOne)+".csv");
        //System.out.println(getArrestBlotterFileName());
    }

    public void PilotsDate(String pilotsdate) {
        int tomorrow = 1;
        int myYear;
        int myMonth;
        int myDay;
        myYear = Integer.parseInt(pilotsdate.substring(0, 4));
        myMonth = Integer.parseInt(pilotsdate.substring(4, 6));
        myDay = Integer.parseInt(pilotsdate.substring(6, 8));
        //System.out.println("Calc " + myYear+" "+myMonth+" "+myDay);
        gc = new GregorianCalendar();
        gc.clear(Calendar.DATE);
        //   System.out.println("Passed "+ pilotsdate);
        gc.set(Calendar.MONTH, myMonth - 1);
        gc.set(Calendar.DAY_OF_MONTH, myDay);
        gc.set(Calendar.YEAR, myYear);
        //System.out.println(gc.get(Calendar.DATE));
        gc.add(Calendar.DATE, tomorrow);
        Date todayMinusOne = gc.getTime();
        sdf = new SimpleDateFormat("yyyyMMdd");
        setYesterdayExample(sdf.format(todayMinusOne));
    }

    public void setYesterdayExample(String myYesterdayExample) {
        gYesterdayExample = myYesterdayExample;
    }

    public String getYesterdayExample() {
        return gYesterdayExample;
    }

    public void setSimpleDateFormat(String sdf) {
        gsdf = sdf;
    }

    public String getSimpleDateFormat() {
        return gsdf;
    }

    public int getCurrentSecond() {
 //String[] ids = TimeZone.getAvailableIDs(-5 * 60 * 60 * 1000);
        //edt = new SimpleTimeZone(-5 * 60 * 60 * 1000, ids[0]);
        //edt.setStartRule(Calendar.MARCH, 2, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        //edt.setEndRule(Calendar.NOVEMBER, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

        //Calendar calendar = new GregorianCalendar(edt);
        return calendar.get(Calendar.SECOND);
    }

    public String getCurrentTime() {
 //String[] ids = TimeZone.getAvailableIDs(-5 * 60 * 60 * 1000);
        //SimpleTimeZone edt = new SimpleTimeZone(-5 * 60 * 60 * 1000, ids[0]);
        //edt.setStartRule(Calendar.MARCH, 2, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        //edt.setEndRule(Calendar.NOVEMBER, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

        //Calendar calendar = new GregorianCalendar(edt);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        Integer Ihour = new Integer(hour);
        Integer Iminute = new Integer(minute);
        Integer Isecond = new Integer(second);

        String shour = Ihour.toString();
        String sminute = Iminute.toString();
        String ssecond = Isecond.toString();

        if (hour < 10) {
            shour = "0" + Ihour.toString();
        }

        if (minute < 10) {
            sminute = "0" + Iminute.toString();
        }
        if (second < 10) {
            ssecond = "0" + Isecond.toString();
        }

        String ct = shour + ":" + sminute + ":" + ssecond;
        return ct;
    }

    public void setDaysBack(int localdaysback) {
        gdaysback = localdaysback;
    }

    public int getDaysBack() {
        return gdaysback;
    }

    public void example() {
        // get the supported ids for GMT-05:00 (Eastern Standard Time)
// String[] ids = TimeZone.getAvailableIDs(-5 * 60 * 60 * 1000);
        // if no ids were returned, something is wrong. get out.
        if (ids.length == 0) {
            System.exit(0);
        }
        // begin output
        System.out.println("Example");
        // create an Eastern Standard Time time zone
// SimpleTimeZone edt = new SimpleTimeZone(-5 * 60 * 60 * 1000, ids[0]);
        // set up rules for daylight savings time
        edt.setStartRule(Calendar.MARCH, 2, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        edt.setEndRule(Calendar.NOVEMBER, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        // create a GregorianCalendar with the Eastern Daylight time zone
        // and the current date and time
        //Calendar calendar = new GregorianCalendar(edt);
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        // print out a bunch of interesting things
        System.out.println("ERA: " + calendar.get(Calendar.ERA));
        System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
        System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
        System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
        System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
        System.out.println("DATE: " + calendar.get(Calendar.DATE));
        System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
        System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println("DAY_OF_WEEK_IN_MONTH: "
                + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
        System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
        System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
        System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
        System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
        System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
        System.out.println("ZONE_OFFSET: "
                + (calendar.get(Calendar.ZONE_OFFSET) / (60 * 60 * 1000)));
        System.out.println("DST_OFFSET: "
                + (calendar.get(Calendar.DST_OFFSET) / (60 * 60 * 1000)));
// System.out.println("Reset Date");
        //calendar.clear(Calendar.HOUR_OF_DAY); // so doesn't override
        //calendar.set(Calendar.HOUR, 3);
        //calendar.clear(Calendar.DATE);
        //calendar.set(Calendar.MONTH,10);
        //calendar.set(Calendar.DAY_OF_MONTH,27);
        //calendar.set(Calendar.YEAR,2000);
        //System.out.println("ERA: " + calendar.get(Calendar.ERA));
        //System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
        //System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
        //System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
        //System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
        //System.out.println("DATE: " + calendar.get(Calendar.DATE));
        //System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
        //System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
        //System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
        //System.out.println("DAY_OF_WEEK_IN_MONTH: "
        //                   + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
        //System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
        //System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
        //System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
        //System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
        //System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
        //System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
        //System.out.println("ZONE_OFFSET: "
        //       + (calendar.get(Calendar.ZONE_OFFSET)/(60*60*1000))); // in hours
        //System.out.println("DST_OFFSET: "
        //       + (calendar.get(Calendar.DST_OFFSET)/(60*60*1000))); // in hours
    }

    public static void main(String[] args) {
        DateProcessing ye = new DateProcessing();
        ye.setDaysBack(1);
        ye.Mydate();
//  String createDate = ye.getYesterdayExample();
//  String oselectstatement = "select * from csvdtalib.csmpf120 where RMALID in (select parent from csvdtalib.csmpf140 where DATA LIKE '%7/23/07%' and DATA LIKE '%Complaint Reply%')";
//  String oselectstatement = "select * from csvdtalib.csmpf120 where DATA LIKE '%Create Date: "+ createDate +"%' and DATA LIKE '%Customer Voice:%' and DATA LIKE '%COMPLAINT%'";
//   oselectstatement += getTableID();
//  System.out.println(oselectstatement);

//    YesterdayExample ye = new YesterdayExample();
//        ye.invokedStandalone = true;
//        ye.setSimpleDateFormat("MMM dd yyyy");
//        ye.example();
//        System.out.println("Current time is " + ye.getCurrentTime());
//
//        ye.setDaysBack(0);
//        ye.Mydate();
//        System.out.println("Today is " + ye.getYesterdayExample());
//
//        ye.setDaysBack(1);
//        ye.Mydate();
//        System.out.println("Get Yesterday " + ye.getYesterdayExample());
//
//        ye.setDaysBack(1);
//        ye.setSimpleDateFormat("MM/dd/YYYY");
//        ye.Mydate();
//        System.out.println("Get Yesterday " + ye.getYesterdayExample());
//
//        ye.setDaysBack(1);
//        ye.setSimpleDateFormat("EEEEYYYYddMM");
//        ye.Mydate();
//        String DOW = ye.getYesterdayExample().toLowerCase();
//        System.out.println("Get Yesterday DOW " + DOW);
//
//        ye.setDaysBack(3);
//        ye.setSimpleDateFormat("MMM dd, yyyy");
//        ye.Mydate();
//        System.out.println("3 Days ago " + ye.getYesterdayExample());
//
//        ye.setDaysBack(365);
//        ye.Mydate();
//        System.out.println("A year ago " + ye.getYesterdayExample());
//
//        ye.setDaysBack(-7);
//        ye.setSimpleDateFormat("yyyyMMdd");
//        ye.Mydate();
//        System.out.println("7 Days in the future " + ye.getYesterdayExample());
//        System.out.println("Get Current Second " + ye.getCurrentSecond());
//
//        if (args.length == 1) {
//            if (args[0].length() < 4) {
//                Integer myArg = new Integer(args[0]);
//
//                ye.setDaysBack(myArg.intValue());
//                ye.setSimpleDateFormat("MMM dd, yyyy");
//                ye.Mydate();
//
//                System.out.println("Results from the Command Line " + ye.getYesterdayExample());
//            }
//        }
//    }
//
//    public String getIncidentDate() {
//        // setDaysBack(1);
//        // setSimpleDateFormat("MM/dd/YYYY");
//        // Mydate();
//        System.out.println("Get Yesterday " + getYesterdayExample());
//        return getYesterdayExample();
    }

    public String getIncidentFileNameDate() {
        //setDaysBack(1);
        //setSimpleDateFormat("EEEEYYYYMMdd");
        //Mydate();
        String DOW = getYesterdayExample().toLowerCase();
        //System.out.println("Get Yesterday DOW " + DOW);
        return DOW;
    }

    public String getIncidentFileNameDate(int DaysBack) {
        setDaysBack(DaysBack);
        setSimpleDateFormat("EEEEYYYYMMdd");
        Mydate();
        String DOW = getYesterdayExample().toLowerCase();
        //System.out.println("Get Yesterday DOW " + DOW);
        return DOW;
    }

    public String getYesterdayIncident() {
        return YesterdayIncident;
    }

    public void setYesterdayIncident(String YesterdayIncident) {
        this.YesterdayIncident = YesterdayIncident;
    }

    public String getYesterdayIncidentFileName() {
        return YesterdayIncidentFileName;
    }

    public void setYesterdayIncidentFileName(String YesterdayIncidentFileName) {
        this.YesterdayIncidentFileName = YesterdayIncidentFileName;
    }

    public String getArrestBlotterFileName() {

        return ArrestBlotterFileName;

    }

    public void setArrestBlotterFileName(String ArrestBlotterFileName) {
        this.ArrestBlotterFileName = ArrestBlotterFileName;
    }

    public String getIncidentDate() {
        return IncidentDate;
    }

    public void setIncidentDate(String IncidentDate) {
        this.IncidentDate = IncidentDate;
    }

}
