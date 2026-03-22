package employee;

import boat.Boat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FleetManager extends Employee {

    ArrayList<Boat> managedBoats = new ArrayList<Boat>();

    public FleetManager(int id, String name, String surnames, LocalDate contractStartDate, double salary, int experience){
        super(id, name, surnames, contractStartDate, salary, experience);

    }

    public static void createFleetManager(ArrayList<Employee> employees){

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

        System.out.println("Indicate the name of the Manager");
        String name = input.nextLine();

        System.out.println("Indicate the surname of the Manager");
        String surnames = input.nextLine();

        LocalDate contractStartDate = LocalDate.now();

        double salary = 12000.00;

        System.out.println("Indicate the experience of the Manager");
        int experience = input.nextInt();
        input.nextLine();

        FleetManager newFM = new FleetManager(id, name, surnames, contractStartDate, salary, experience);
        employees.add(newFM);
        System.out.println("New Fleet Manager with ID: " + newFM.getId() + " named: " + newFM.getName() + " " + newFM.getSurnames() + " created Successfully");
    }

    public void calculateSalaryBonus(){

    }

    public ArrayList<Boat> getManagedBoats() {
        return managedBoats;
    }

}
