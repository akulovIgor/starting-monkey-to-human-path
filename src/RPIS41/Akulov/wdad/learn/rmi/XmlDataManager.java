package RPIS41.Akulov.wdad.learn.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

public interface XmlDataManager extends Remote{

    public int earningsTotal(Officiant officicant, Calendar date) throws RemoteException;
    public void removeDay(Calendar date) throws RemoteException;
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws RemoteException;
    public List<Order> getOrders(Calendar calendar) throws RemoteException;
    public Calendar lastOfficiantWorkDate(Officiant officiant) throws RemoteException;
}