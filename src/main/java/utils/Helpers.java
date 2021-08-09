package utils;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.util.Date;
import java.util.List;

public class Helpers {

    public static Date parseStringToDate(String input){
//        Отрезаем первую дату, оставляем только последнюю
        String[] inputArray = input.split("-");
        String processedText = inputArray[inputArray.length - 1];
//        Парсим дату из строки
        Parser parser = new Parser();
        List<Date> dates = null;
        List<DateGroup> groups = parser.parse(processedText);
        for(DateGroup group:groups) {
            dates = group.getDates();
        }
        return dates.get(dates.size() - 1);
    }
}