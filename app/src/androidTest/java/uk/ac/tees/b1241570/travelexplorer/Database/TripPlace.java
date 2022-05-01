package uk.ac.tees.b1241570.travelexplorer.Database;

public class TripPlace {

    public String idt;
    public String idu;
    public String date;
    public String time;
    public String lattitude;
    public String longitude;
    public String purpose;
    public String placename;

    public TripPlace(String idt, String idu, String date, String time, String lattitude, String longitude, String purpose, String placename) {

        this.idt = idt;
        this.idu = idu;
        this.date = date;
        this.time = time;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.purpose = purpose;
        this.placename = placename;
    }


}
