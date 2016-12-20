package RPIS41.Akulov.wdad.data.managers;

import RPIS41.Akulov.wdad.utils.PreferencesConstantManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;

import java.util.Properties;

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

    public void setProperty(String key, String value){
        getPropertyElement(key).setTextContent(value);
        saveChanges();
    }
    public String getProperty(String key){
        return getPropertyElement(key).getTextContent();
    }
    public void setProperties(Properties prop){
        for (String key : prop.stringPropertyNames()) {
            setProperty(key, prop.getProperty(key));
        }
    }
    public Properties getProperties() {
        Properties props = new Properties();
        String key, value;
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "//*[not(*)]";

        try {
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(appconfig, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                key = getNodePath(nodeList.item(i));
                value = getProperty(key);
                props.put(key, value);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return props;
    }
    public void addBindedObject(String name, String className){
        Element bindedObject = appconfig.createElement("bindedobject");
        bindedObject.setAttribute("name", name);
        bindedObject.setAttribute("class", className);
        Element server = (Element)appconfig.getElementsByTagName("server").item(0);
        server.appendChild(bindedObject);
        saveChanges();
    }
    public void removeBindedObject(String name){
        NodeList bindedObjects = appconfig.getElementsByTagName("bindedobject");
        for (int i = 0; i < bindedObjects.getLength(); i++) {
            Element bindedObject = (Element)bindedObjects.item(i);
            if (name.equals(bindedObject.getAttribute("name"))) {
                bindedObject.getParentNode().removeChild(bindedObject);
            }
        }
        saveChanges();
    }

    private String getNodePath(Node node) {
        Node parent = node.getParentNode();
        if (parent.getNodeName().equals("#document")) {
            return node.getNodeName();
        }
        return getNodePath(parent) + '.' + node.getNodeName();
    }
    private Element getPropertyElement(String key){
        String[] tags = key.split("\\.");
        return (Element)appconfig.getElementsByTagName(tags[tags.length - 1]).item(0);
    }

    @Deprecated
    public boolean getCreateRegistry(){
        NodeList createRegistry = appconfig.getElementsByTagName("createregistry");
        return createRegistry.item(0).getTextContent().equals("yes");
    }
    @Deprecated
    public void setCreateRegistry(boolean createRegistryValue){
        NodeList createRegistry = appconfig.getElementsByTagName("createregistry");
        if(createRegistryValue)
            createRegistry.item(0).setTextContent("yes");
        else createRegistry.item(0).setTextContent("no");
        saveChanges();
    }
    @Deprecated
    public String getRegistryAddress(){
        NodeList registryAddress = appconfig.getElementsByTagName("registryaddress");
        return registryAddress.item(0).getTextContent();
    }
    @Deprecated
    public void setRegistryAddress(String registryAddressValue){
        NodeList registryAddress = appconfig.getElementsByTagName("registryaddress");
        registryAddress.item(0).setTextContent(registryAddressValue);
        saveChanges();
    }
    @Deprecated
    public int getRegistryPort(){
        NodeList registryPort = appconfig.getElementsByTagName("registryport");
        return Integer.parseInt(registryPort.item(0).getTextContent());
    }
    @Deprecated
    public void setRegistryPort(int registryPortValue){
        NodeList registryPort = appconfig.getElementsByTagName("registryport");
        registryPort.item(0).setTextContent(Integer.toString(registryPortValue));
        saveChanges();
    }
    @Deprecated
    public String getPolicyPath(){
        NodeList policyPath = appconfig.getElementsByTagName("policypath");
        return policyPath.item(0).getTextContent();
    }
    @Deprecated
    public void setPolicyPath(String policyPathValue){
        NodeList policyPath = appconfig.getElementsByTagName("policypath");
        policyPath.item(0).setTextContent(policyPathValue);
        saveChanges();
    }
    @Deprecated
    public boolean isUseCodeBaseOnly(){
        NodeList useCodeBaseOnly = appconfig.getElementsByTagName("usecodebaseonly");
        return useCodeBaseOnly.item(0).getTextContent().equals("yes");
    }
    @Deprecated
    public void setUseCodeBaseOnly(boolean useCodeBaseOnlyValue){
        NodeList useCodeBaseOnly = appconfig.getElementsByTagName("usecodebaseonly");
        if(useCodeBaseOnlyValue)
            useCodeBaseOnly.item(0).setTextContent("yes");
        else useCodeBaseOnly.item(0).setTextContent("no");
        saveChanges();
    }
    @Deprecated
    public String getClassProvider(){
        NodeList classProvider = appconfig.getElementsByTagName("classprovider");
        return classProvider.item(0).getTextContent();
    }
    @Deprecated
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