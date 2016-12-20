package RPIS41.Akulov.wdad.learn.rmi;

import java.io.Serializable;


public class Item implements Serializable{

    private String name;
    private int cost;

    public Item(String name, int cost){
        this.name = name;
        this.cost = cost;
    }

    public String getName(){return name;}
    public int getCost(){return cost;}
}