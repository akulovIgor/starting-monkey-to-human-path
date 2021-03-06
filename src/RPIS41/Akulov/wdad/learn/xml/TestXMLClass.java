package RPIS41.Akulov.wdad.learn.xml;

import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Calendar;

public class TestXMLClass {
    public static void test(){
        Calendar calendarEarnings = Calendar.getInstance();
        calendarEarnings.set(2016, Calendar.OCTOBER, 10);//дата для проверки прибыли за день
        Calendar calendarRemove = Calendar.getInstance();
        calendarRemove.set(2015, Calendar.APRIL, 25);//дата для удаления

        try {
            XmlTask testClass = new XmlTask("src\\RPIS41\\Akulov\\wdad\\learn\\xml\\testRightFirst");
            System.out.println("Прибыль Сидорова за 10 октября 2016 года: " + testClass.earningsTotal("sidorov", calendarEarnings));
            testClass.removeDay(calendarRemove);
            testClass.changeOfficiantName("maxim", "petrov", "vlad", "ivanov");
        }catch (ParserConfigurationException | SAXException | IOException e){e.printStackTrace();}
    }
}
