package entity;
import java.util.Objects;

public class Buyer {
    private String name;
    private String secondName;
    private String phoneNumber;
    private double money;

    public Buyer(String name, String email, String address) {
        this.name = name;
        this.secondName = email;
        this.phoneNumber = address;
        this.money = 0.0;
    }

    public Buyer(String name, String email, String address, double money) {
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getMoney() {
        return money;
    }

    public void addMoney(double amount) {
        if (amount > 0) {
            money += amount;
        }
    }

    public void deductMoney(double amount) {
        if (amount > 0 && money >= amount) {
            money -= amount;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, secondName, phoneNumber, money);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Buyer buyer = (Buyer) obj;
        return Double.compare(buyer.money, money) == 0 &&
                Objects.equals(name, buyer.name) &&
                Objects.equals(secondName, buyer.secondName) &&
                Objects.equals(phoneNumber, buyer.phoneNumber);
    }

    @Override
    public String toString() {
        return String.format("%s; %s; %s; $%.2f", name, secondName, phoneNumber, money);
    }


}
