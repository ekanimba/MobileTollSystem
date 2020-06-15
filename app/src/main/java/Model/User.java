package Model;

public class User {

    /* This Class represents a user-object*/

    private String fName, lName, email, phoneNumber, address, city, country, zipCode;

    public Boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    public Boolean getAutoPayment() {
        return autoPayment;
    }

    public void setAutoPayment(Boolean autoPayament) {
        this.autoPayment = autoPayament;
    }

    private Boolean notifications, autoPayment;


    private double balance;

    public User(String fName, String lName, String email, String phoneNumber, String address, String city, String country, String zipCode, Boolean notifications, Boolean autoPayment) {

        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.balance = 0;
        this.notifications = notifications;
        this.autoPayment = autoPayment;
    }



    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }
}
