package no.westerdals.PG4100.innlevering1;


public class Car {
    private String regNumber;
    private boolean available = true;

    public Car(String regNumber) {
        setRegNumber(regNumber);
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Car))
            return false;

        if (this == o) {
            return true;
        }
        Car other = (Car) o;
        return this.regNumber.equals(other.getRegNumber());
    }
}
