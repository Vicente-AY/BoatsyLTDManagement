package employee;

import boat.Boat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Captain extends Employee{

    int trips = 0;
    double bonus = 0;
    double monthDistance = 0;
    Boat assignedBoat = null;

    public Captain(int id, String name, String surnames, LocalDate contractStartDate, double salary, int experience, double bonus){
        super(id, name, surnames, contractStartDate, salary, experience);
        this.bonus = bonus;
    }

    public static void createCaptain(ArrayList<Employee> employees){

        Scanner input = new Scanner(System.in);

        int id = 0;
        if(!employees.isEmpty()) {
            for (Employee e : employees) {
                if(e.id > id) {
                    id = e.id;
                }
            }
        }
        id++;

        System.out.println("Indicate the name of the Captain");
        String name = input.nextLine();

        System.out.println("Indicate the surname of the Captain");
        String surnames = input.nextLine();

        LocalDate contractStartDate = LocalDate.now();

        double salary = 3500.00;

        System.out.println("Indicate the experience of the Captain");
        int experience = input.nextInt();
        input.nextLine();

        double bonus = 0;

        Captain newCaptain = new Captain(id, name, surnames, contractStartDate, salary, experience, bonus);
        employees.add(newCaptain);
        System.out.println("New Captain with ID: " + newCaptain.getId() + " named: " + newCaptain.getName() + " " + newCaptain.getSurnames() + " created Successfully");
    }

    public void calculateSalaryBonus(){

        if(this.monthDistance > 5000){
            this.bonus = monthDistance / 10000;
        }
    }

    //Getters y Setters

    public Boat getAssignedBoat(){
        return assignedBoat;
    }
    public void setAssignedBoat(Boat assignedBoat){
        this.assignedBoat = assignedBoat;
    }
    public double getBonus(){
        return bonus;
    }
    public double getMonthDistance(){
        return monthDistance;
    }
    public void setMonthDistance(double monthDistance){
        this.monthDistance = monthDistance;
    }
    public int getTrips(){
        return this.trips;
    }
    public void setTrips(int trips){
        this.trips = trips;
    }
}
