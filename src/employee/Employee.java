package employee;

import boat.Boat;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Employee {

    int id = 0;
    String name = "";
    String surnames = "";
    LocalDate contractStartDate = LocalDate.now();
    double salary = 0;
    int experience = 0;

    public Employee(int id, String name, String surnames, LocalDate contractStartDate, double salary, int experience) {
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.contractStartDate = contractStartDate;
        this.salary = salary;
        this.experience = experience;
    }

    public abstract void calculateSalaryBonus();

    //Getters y Setters

    public int  getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurnames() {
        return surnames;
    }
    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }
    public LocalDate getContractStartDate() {
        return contractStartDate;
    }
    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public int getExperience() {
        return experience;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }
}
