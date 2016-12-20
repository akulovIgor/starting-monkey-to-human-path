package RPIS41.Akulov.wdad.learn.rmi;

import java.io.Serializable;

public class Officiant implements Serializable{
    private String firstname;
    private String secondName;

    public Officiant(String firstname, String secondName){
        this.firstname = firstname;
        this.secondName = secondName;
    }
    public String getFirstname(){return firstname;}
    public String getSecondName(){return secondName;}
}