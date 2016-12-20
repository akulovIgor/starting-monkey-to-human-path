package RPIS41.Akulov.wdad.learn.rmi;

import RPIS41.Akulov.wdad.data.managers.PreferencesManager;
import RPIS41.Akulov.wdad.utils.PreferencesConstantManager;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class Server {
    private static PreferencesManager preferencesManager;

    private static final String XML_DATA_MANAGER = "XmlDataManager";
    private static final int XML_DATA_MANAGER_PORT = 33333;
    private static final String CODEBASE_URL = "file:/c:/RMILAB/Codebase\\";

    public static void main(String[] args) {
        try {
            preferencesManager = PreferencesManager.getInstance();
        }catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            System.err.println("appconfig.xml damaged");
        }
        System.out.println("Preparing server:...");
        System.setProperty("java.rmi.server.codebase", CODEBASE_URL);
        System.setProperty("java.rmi.server.UseCodeBaseOnly", preferencesManager.getProperty(PreferencesConstantManager.USE_CODE_BASE_ONLY));
        System.setProperty("java.rmi.server.logCalls", "true");
        System.setProperty("java.security.policy", preferencesManager.getProperty(PreferencesConstantManager.POLICY_PATH));
        System.setSecurityManager(new SecurityManager());
        System.out.println("Server prepared.");

        Registry registry = null;
        try{
            if(preferencesManager.getProperty(PreferencesConstantManager.CREATE_REGISTRY).equals("yes"))
                registry = LocateRegistry.createRegistry(Integer.parseInt(preferencesManager.getProperty(PreferencesConstantManager.REGISTRY_PORT)));
            else registry = LocateRegistry.getRegistry(Integer.parseInt(preferencesManager.getProperty(PreferencesConstantManager.REGISTRY_PORT)));
        }catch (RemoteException re){
            re.printStackTrace();
            System.err.println("cant locate registry");
        }

        if(registry != null){
            try{
                System.out.println("exporting object ...");
                XmlDataManagerImpl xmlDataManagerImpl = new XmlDataManagerImpl();
                UnicastRemoteObject.exportObject(xmlDataManagerImpl, XML_DATA_MANAGER_PORT);
                registry.rebind(XML_DATA_MANAGER, xmlDataManagerImpl);
                preferencesManager.addBindedObject(XML_DATA_MANAGER, "RPIS41.Akulov.wdad.learn.rmi.XmlDataMangerImpl");
                System.out.println("Waiting ... ");
                System.out.println("Input \"exit\" to close server.");
                Scanner scanner = new Scanner(System.in);
                String input;
                while(true){
                    input = scanner.nextLine();
                    if(input.equals("exit")) {
                        try {
                            registry.unbind(XML_DATA_MANAGER);
                            preferencesManager.removeBindedObject(XML_DATA_MANAGER);
                            System.exit(0);
                        }catch (NotBoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            }catch (RemoteException re){
                re.printStackTrace();
                System.err.println("cant export or bind object");
            }
        }
    }
}