package Model;

/* This Class represents a car-object*/

import java.util.Objects;

public class Car {

    private String carBrand;
    private String carModel;
    private String carFuel;
    private String carRegistrationNumber;

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarFuel() {
        return carFuel;
    }

    public void setCarFuel(String carFuel) {
        this.carFuel = carFuel;
    }

    public String getCarRegistrationNumber() {
        return carRegistrationNumber;
    }

    public void setCarRegistrationNumber(String carRegistrationNumber) {
        this.carRegistrationNumber = carRegistrationNumber;
    }

    public Car(String carBrand, String carModel, String carFuel, String carRegistrationNumber) {
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carFuel = carFuel;
        this.carRegistrationNumber = carRegistrationNumber;
    }
}
