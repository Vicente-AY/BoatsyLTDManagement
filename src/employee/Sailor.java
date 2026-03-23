package employee;

import boat.Boat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Sailor extends Employee {

    private static final long serialVersionUID = 1L;
    int trips = 0;
    double bonus = 0;
    double monthDistance = 0;
    Boat assignedBoat = null;

    public Sailor(int id, String name, String surnames, LocalDate contractStartDate, double salary, int experience) {
        super(id, name, surnames, contractStartDate, salary, experience);
    }

    public static void createSailor(ArrayList<Employee> employees) {

        Scanner input = new Scanner(System.in);

        System.out.println("Adding a new employee");
        int id = 0;
        if(!employees.isEmpty()) {
            for (Employee e : employees) {
                if(e.id > id) {
                    id = e.id;
                }
            }
        }
        id++;

        System.out.println("Indicate the name of the sailor");
        String name = input.nextLine();

        System.out.println("Indicate the surname of the sailor");
        String surnames = input.nextLine();

        LocalDate contractStartDate = LocalDate.now();

        double salary = 1500.00;


        System.out.println("Indicate the experience of the sailor");
        int experience = input.nextInt();
        input.nextLine();

        Sailor newSailor = new Sailor(id, name, surnames, contractStartDate, salary, experience);
        employees.add(newSailor);
        System.out.println("New Sailor with ID: " + newSailor.getId() + " Named: " + newSailor.getName() + " " + newSailor.getSurnames() + " created Successfully");
    }

    public void calculateSalaryBonus(){

        if(this.monthDistance > 5000){
            this.bonus = monthDistance / 100000;
        }
    }

    public double getDistance(){
        return monthDistance;
    }

    public void setDistance(double distance){
        this.monthDistance += distance;
    }

    //Getters y Setters

    public double bounus(){
        return bonus;
    }
    public double monthDistance(){
        return monthDistance;
    }
    public double getMonthDistance(){
        return monthDistance;
    }
    public void setMonthDistance(double monthDistance){
        this.monthDistance = monthDistance;
    }
    public Boat getAssignedBoat() {
        return assignedBoat;
    }
    public void setAssignedBoat(Boat assignedBoat) {
        this.assignedBoat = assignedBoat;
    }
    public int getTrips() {
        return trips;
    }
    public void setTrips(int trips) {
        this.trips = trips;
    }
}
