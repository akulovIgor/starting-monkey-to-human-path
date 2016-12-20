package RPIS41.Akulov.wdad.learn.rmi;

import RPIS41.Akulov.wdad.data.managers.PreferencesManager;
import RPIS41.Akulov.wdad.utils.PreferencesConstantManager;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client {
    private static PreferencesManager preferencesManager;
    private static final String XML_DATA_MANAGER = "XmlDataManager";
    private static final String CODEBASE_URL = "file:/c:/RMILAB/Codebase\\";

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException{
        try {
            preferencesManager = PreferencesManager.getInstance();
        }catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            System.err.println("appconfig.xml damaged");
        }

        System.out.println("Preparing ...");
        System.setProperty("java.rmi.server.codebase", CODEBASE_URL);
        System.setProperty("java.rmi.server.useCodebaseOnly", preferencesManager.getProperty(PreferencesConstantManager.USE_CODE_BASE_ONLY));
        System.setProperty("java.security.policy", preferencesManager.getProperty(PreferencesConstantManager.POLICY_PATH));
        System.out.println("prepared");
        System.setSecurityManager(new SecurityManager());
        Registry registry = null;
        try{
            registry = LocateRegistry.getRegistry(
                    preferencesManager.getProperty(PreferencesConstantManager.REGISTRY_ADDRESS),
                    Integer.parseInt(preferencesManager.getProperty(PreferencesConstantManager.REGISTRY_PORT)));
        } catch (RemoteException re) {
            re.printStackTrace();
            System.err.println("cant locate registry");
        }
        if(registry != null){
            try{
                XmlDataManager xmlDataManager = (XmlDataManager) registry.lookup(XML_DATA_MANAGER);
                doWork(xmlDataManager);
            }catch (RemoteException | NotBoundException e){
                System.err.println("Your code don't work");
                e.printStackTrace();
            }
        }
    }

    private static void doWork(XmlDataManager xmlDataManager) {
        Calendar calendarEarnings = Calendar.getInstance();
        calendarEarnings.set(2016, Calendar.OCTOBER, 10);//дата для проверки прибыли за день
        Calendar calendarRemove = Calendar.getInstance();
        calendarRemove.set(2015, Calendar.APRIL, 25);//дата для удаления
        try {
            System.out.println("Прибыль Сидорова за 10 октября 2016 года: " + xmlDataManager.earningsTotal(new Officiant("lexa", "sidorov"), calendarEarnings));
            xmlDataManager.removeDay(calendarRemove);
            xmlDataManager.changeOfficiantName(new Officiant("egor", "ivanov"), new Officiant("vasya", "sidorov"));
            for (Order order : xmlDataManager.getOrders(calendarEarnings))
                System.out.println(order.getOfficiant().getFirstname() + " " + order.getOfficiant().getSecondName() + " " + order.getCountItems());
            Calendar lastKolyanWorkDay = xmlDataManager.lastOfficiantWorkDate(new Officiant("lexa", "sidorov"));
            System.out.println("last work day lexa sidorov: " +
                    lastKolyanWorkDay.get(Calendar.DAY_OF_MONTH) + " " +
                    lastKolyanWorkDay.get(Calendar.MONTH) + " " +
                    lastKolyanWorkDay.get(Calendar.YEAR));
        }catch (RemoteException re){re.printStackTrace();}
    }
}