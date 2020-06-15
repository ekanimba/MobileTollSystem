package Model;

public class TollEntry {

    private String entryTime;
    private String entryToll;
    private String tagId;
    private String userId;
    private String carRegistrationNumber;

    public String getCarRegistrationNumber() {
        return carRegistrationNumber;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public String getEntryToll() {
        return entryToll;
    }

    public String getTagId() {
        return tagId;
    }

    public String getUserId() {
        return userId;
    }



    public TollEntry(String entryTime,String entryToll,String tagId,String userId , String carRegistrationNumber){
        this.entryTime = entryTime;
        this.entryToll = entryToll;
        this.tagId = tagId;
        this.userId = userId;
        this.carRegistrationNumber = carRegistrationNumber;
    }
}
