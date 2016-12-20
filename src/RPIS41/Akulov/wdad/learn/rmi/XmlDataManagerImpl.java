package RPIS41.Akulov.wdad.learn.rmi;

import RPIS41.Akulov.wdad.learn.xml.XmlTask;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by molish on 22.10.2016.
 */
public class XmlDataManagerImpl implements XmlDataManager,Serializable {

    private final XmlTask xmlTask = new XmlTask("src\\RPIS41\\Akulov\\wdad\\learn\\xml\\testRightFirst");

    @Override
    public int earningsTotal(Officiant officicant, Calendar date) {
        return xmlTask.earningsTotal(officicant.getSecondName(), date);
    }

    @Override
    public void removeDay(Calendar date) {
        xmlTask.removeDay(date);
    }

    @Override
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) {
        xmlTask.changeOfficiantName(oldOfficiant.getFirstname(), oldOfficiant.getSecondName(), newOfficiant.getFirstname(), newOfficiant.getSecondName());
    }

    @Override
    public List<Order> getOrders(Calendar calendar) {
        return xmlTask.getOrders(calendar);
    }

    @Override
    public Calendar lastOfficiantWorkDate(Officiant officiant) {
        return xmlTask.lastOfficiantWorkDate(officiant);
    }
}
