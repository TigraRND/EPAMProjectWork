package utils;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.util.Date;
import java.util.List;

public class Helpers {

    public static Date parseStringToDate(String input, int num){
        Parser parser = new Parser();
        List<Date> dates = null;
        List<DateGroup> groups = parser.parse(input);
        for(DateGroup group:groups) {
            dates = group.getDates();
        }
        return dates.get(num);
    }

    public static Date parseStringToDate(String input){
        Parser parser = new Parser();
        List<Date> dates = null;
        List<DateGroup> groups = parser.parse(input);
        for(DateGroup group:groups) {
            dates = group.getDates();
        }
        return dates.get(dates.size() - 1);
    }
}