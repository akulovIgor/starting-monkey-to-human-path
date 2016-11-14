package RPIS41.Akulov.wdad.data.managers;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreferencesManager {
    private static PreferencesManager instance;
    private static String sourcePath = "src\\RPIS41\\Akulov\\wdad\\resources\\configuration\\appconfig.xml";
    private Document appconfig;

    private PreferencesManager() throws ParserConfigurationException, SAXException, IOException{
        File xmlFile = new File(sourcePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        appconfig = documentBuilder.parse(xmlFile);
        appconfig.getDocumentElement().normalize();
    }

    public static PreferencesManager getInstance() throws ParserConfigurationException, SAXException, IOException{
        if(instance == null)
            instance = new PreferencesManager();
        return instance;
    }

    public boolean getCreateRegistry(){
       
        NodeList createRegistry = appconfig.getElementsByTagName("createregistry");
        if(createRegistry.item(0).getTextContent().equals("yes"))
            return true;
        else return false;
    }
    public void setCreateRegistry(boolean createRegistryValue){
        NodeList createRegistry = appconfig.getElementsByTagName("createregistry");
        if(createRegistryValue)
            createRegistry.item(0).setTextContent("yes");
        else createRegistry.item(0).setTextContent("no");
        saveChanges();
    }

    public String getRegistryAddress(){
        NodeList registryAddress = appconfig.getElementsByTagName("registryaddress");
        return registryAddress.item(0).getTextContent();
    }
    public void setRegistryAddress(String registryAddressValue){
        NodeList registryAddress = appconfig.getElementsByTagName("registryaddress");
        registryAddress.item(0).setTextContent(registryAddressValue);
        saveChanges();
    }

    public int getRegistryPort(){
        NodeList registryPort = appconfig.getElementsByTagName("registryport");
        return Integer.parseInt(registryPort.item(0).getTextContent());
    }
    public void setRegistryPort(int registryPortValue){
        NodeList registryPort = appconfig.getElementsByTagName("registryport");
        registryPort.item(0).setTextContent(Integer.toString(registryPortValue));
        saveChanges();
    }

    public String getPolicyPath(){
        NodeList policyPath = appconfig.getElementsByTagName("policypath");
        return policyPath.item(0).getTextContent();
    }
    public void setPolicyPath(String policyPathValue){
        NodeList policyPath = appconfig.getElementsByTagName("policypath");
        policyPath.item(0).setTextContent(policyPathValue);
        saveChanges();
    }

    public boolean isUseCodeBaseOnly(){
        NodeList useCodeBaseOnly = appconfig.getElementsByTagName("usecodebaseonly");
        if(useCodeBaseOnly.item(0).getTextContent().equals("yes"))
            return true;
        else return false;
    }
    public void setUseCodeBaseOnly(boolean useCodeBaseOnlyValue){
        NodeList useCodeBaseOnly = appconfig.getElementsByTagName("usecodebaseonly");
        if(useCodeBaseOnlyValue)
            useCodeBaseOnly.item(0).setTextContent("yes");
        else useCodeBaseOnly.item(0).setTextContent("no");
        saveChanges();
    }

    public String getClassProvider(){
        NodeList classProvider = appconfig.getElementsByTagName("classprovider");
        return classProvider.item(0).getTextContent();
    }
    public void setClassProvider(String classProviderValue) {
        NodeList classProvider = appconfig.getElementsByTagName("classprovider");
        classProvider.item(0).setTextContent(classProviderValue);
        saveChanges();
    }

    private void saveChanges() {
        try {
            DOMImplementationLS domImplementationLS =
                    (DOMImplementationLS) appconfig.getImplementation().getFeature("LS", "3.0");
            LSOutput lsOutput = domImplementationLS.createLSOutput();
            FileOutputStream outputStream = new FileOutputStream(sourcePath);
            lsOutput.setByteStream(outputStream);
            LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
            lsSerializer.write(appconfig, lsOutput);
            outputStream.close();
        }catch (IOException e){e.printStackTrace();}
    }
}
