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

public class FleetManager extends Employee {

    private static final long serialVersionUID = 1L;
    ArrayList<Boat> managedBoats = new ArrayList<Boat>();

    public FleetManager(int id, String name, String surnames, LocalDate contractStartDate, double salary, int experience){
        super(id, name, surnames, contractStartDate, salary, experience);

    }

    public static void createFleetManager(ArrayList<Employee> employees){

        Scanner input = new Scanner(System.in);

        String name = "";
        String surnames = "";
        LocalDate contractStartDate = null;
        double salary = 0.0;
        int experience = 0;

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
            System.out.println("Indicate the name of the Fleet Manager");
            name = input.nextLine();
            if (name == null || name.trim().isEmpty()) {
                throw new EmptyFieldException("Fleet Manager name cannot be empty");
            }

            System.out.println("Indicate the surname of the Manager");
            surnames = input.nextLine();
            if (surnames == null || surnames.trim().isEmpty()) {
                throw new EmptyFieldException("Fleet Manager surnames cannot be empty");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.println("Indicate the Contract Initialization Date (DD/MM/YYYY - Enter for today)");
            String contractDate = input.nextLine();
            if (contractDate == null || contractDate.trim().isEmpty()) {
                contractStartDate = LocalDate.now();
            } else {
                contractStartDate = LocalDate.parse(contractDate, formatter);
            }

            salary = 12000.00;

            System.out.println("Indicate the experience of the Manager");
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

        FleetManager newFM = new FleetManager(id, name, surnames, contractStartDate, salary, experience);
        employees.add(newFM);
        System.out.println("New Fleet Manager with ID: " + newFM.getId() + " named: " + newFM.getName() + " " + newFM.getSurnames() + " created Successfully");
    }


    public ArrayList<Boat> getManagedBoats() {
        return managedBoats;
    }

}
