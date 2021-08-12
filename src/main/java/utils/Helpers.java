package utils;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

public class Helpers {

    public static Date parseStringToDate(String input) {
//        Отрезаем первую дату, оставляем только последнюю
        String[] inputArray = input.split("-");
        String processedText = inputArray[inputArray.length - 1];
//        Парсим дату из строки
        Parser parser = new Parser();
        List<Date> dates = null;
        List<DateGroup> groups = parser.parse(processedText);
        for (DateGroup group : groups) {
            dates = group.getDates();
        }
        return dates.get(dates.size() - 1);
    }

    public static boolean checkSubstringInString(String source, String substring) {
        int index = source.toLowerCase().lastIndexOf(substring.toLowerCase());

        if (index == -1) {
            return false;
        } else {
            return true;
        }
    }

    public static int randomNumInRange(int start, int end) {
        return start + (int) (Math.random() * end);
    }

    //    Снятие скриншота для Allure отчета
    public static void takeScreenshot(String name, WebDriver driver){
        Allure.addAttachment(name, new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
    }
}