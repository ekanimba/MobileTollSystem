package Model;

public class Trip {

    String entryToll;
    String exitToll;
    String entryTime;
    String exitTime;
    String ticketPrice;
    String carRegistrationNumber;
    Boolean paid;
    String dateTrip;
    String tripId;

    public Trip(String entryToll, String exitToll, String entryTime, String exitTime, String ticketPrice, String carRegistrationNumber, String dateTrip, Boolean paid, String tripId){
        this.entryToll = entryToll;
        this.exitToll = exitToll;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.ticketPrice = ticketPrice;
        this.carRegistrationNumber = carRegistrationNumber;
        this.dateTrip = dateTrip;
        this.paid = paid;
        this.tripId = tripId;
    }

    //getters and setters goes here

    public String getEntryToll() {
        return entryToll;
    }

    public String getExitToll() {
        return exitToll;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public String getCarRegistrationNumber() {
        return carRegistrationNumber;
    }

    public String getDateTrip() { return dateTrip; }

    public Boolean getPaid() { return paid; }

    public String getTripId() { return tripId; }
}
