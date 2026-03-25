package employee;

import boat.Boat;
import exceptions.EmptyFieldException;
import exceptions.NegativeNumberException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Captain extends Employee{

    private static final long serialVersionUID = 1L;
    int trips = 0;
    double bonus = 0;
    double monthDistance = 0;
    Boat assignedBoat = null;

    public Captain(int id, String name, String surnames, LocalDate contractStartDate, double salary, int experience){
        super(id, name, surnames, contractStartDate, salary, experience);
    }

    public static void createCaptain(ArrayList<Employee> employees){

        String name = "";
        String surnames = "";
        LocalDate contractStartDate = null;
        double salary = 0.0;
        int experience = 0;

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

        try {
            System.out.println("Indicate the name of the Captain");
            name = input.nextLine();
            if (name == null || name.trim().isEmpty()) {
                throw new EmptyFieldException("Captain name cannot be empty");
            }

            System.out.println("Indicate the surname of the Captain");
            surnames = input.nextLine();
            if (surnames == null || surnames.trim().isEmpty()) {
                throw new EmptyFieldException("Captain surname is empty");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.println("Indicate the Contract Initialization Date (DD/MM/YYYY - Enter for today)");
            String contractDate = input.nextLine();
            if (contractDate == null || contractDate.trim().isEmpty()) {
                contractStartDate = LocalDate.now();
            } else {
                contractStartDate = LocalDate.parse(contractDate, formatter);
            }

            salary = 3500.00;

            System.out.println("Indicate the experience of the Captain");
            experience = input.nextInt();
            input.nextLine();
            if(experience < 0){
                throw new NegativeNumberException("Experience cannot be negative");
            }
        }
        catch(DateTimeParseException e){
            System.err.println("Invalid Time Format " + e.getMessage());
            return;
        }
        catch(NegativeNumberException e){
            System.err.println("Invalid Number " + e.getMessage());
            return;
        }
        catch(InputMismatchException e){
            System.err.println("Field only accepts Numbers " + e.getMessage());
            return;
        }
        catch(Exception e){
            System.err.println("General Error " + e.getMessage());
            return;
        }

        Captain newCaptain = new Captain(id, name, surnames, contractStartDate, salary, experience);
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
