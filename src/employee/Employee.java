package employee;

import boat.Boat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase abstracta que identifica a un empleado de la compañia
 */
public abstract class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    int id = 0;
    String name = "";
    String surnames = "";
    LocalDate contractStartDate = LocalDate.now();
    double salary = 0;
    int experience = 0;

    /**
     * Constructor de empleado
     * @param id identificador unico para cada empleado
     * @param name nombre del empleado
     * @param surnames apellidos del empleado
     * @param contractStartDate fecha de inicación de contrato con la compañia
     * @param salary salario del empleado
     * @param experience años de experiencia del empleado
     */
    public Employee(int id, String name, String surnames, LocalDate contractStartDate, double salary, int experience) {
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.contractStartDate = contractStartDate;
        this.salary = salary;
        this.experience = experience;
    }

    /**
     * Metodo abstracto de actualización del empleado
     */
    public abstract void updateBaseSalary();

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
