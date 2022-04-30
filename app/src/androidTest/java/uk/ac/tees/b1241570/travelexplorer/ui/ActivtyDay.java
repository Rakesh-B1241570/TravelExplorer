package uk.ac.tees.b1241570.travelexplorer.ui;

import java.util.ArrayList;

public class ActivtyDay {

    private static ActivtyDay instance;

    private ArrayList list;

    public ArrayList getList() {
        return list;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }

    public ActivtyDay(){}

    public static ActivtyDay getInstance(){
        if(instance == null){
            instance = new ActivtyDay();
        }
        return instance;
    }

}
