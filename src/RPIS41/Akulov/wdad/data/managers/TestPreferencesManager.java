package RPIS41.Akulov.wdad.data.managers;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestPreferencesManager {
    public static void test(){
        try {
            PreferencesManager preferencesManager = PreferencesManager.getInstance();

            System.out.println(preferencesManager.isCreateRegistry());
            System.out.println(preferencesManager.getUseCodeBaseOnly());
            System.out.println(preferencesManager.getClassProvider());
            System.out.println(preferencesManager.getPolicyPath());
            System.out.println(preferencesManager.getRegistryAddress());
            System.out.println(preferencesManager.getRegistryPort());

            preferencesManager.setPolicyPath("efe\\ewfr\\wf\\checkSum.policy");
            preferencesManager.setClassProvider("C:\\edw\\wed\\wed\\client.jar");
            preferencesManager.setRegistryAddress("233.45.123.243");



        }catch (IOException | ParserConfigurationException | SAXException e){e.printStackTrace();}
    }
}