package menu;

import boat.Boat;
import boat.CargoShip;
import employee.Captain;
import employee.Employee;
import employee.FleetManager;
import employee.Sailor;
import utils.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {



    ArrayList<Boat> boats = new ArrayList<Boat>();
    ArrayList<Employee> employees = new ArrayList<Employee>();

    public void displayMenu() {

        chargeData();

        Scanner input = new Scanner(System.in);

        int option = 0;
        boolean cont = true;
        while(cont){
            System.out.println("Welcome to Boatsy LTD Management");
            System.out.println("Type your option");
            System.out.println("1. Manage Personal. | 2. Manage Fleet | 3. Close application");
            option = input.nextInt();
            input.nextLine();
            switch(option){
                case 1:
                    EmployeeManagement employeeManagement = new EmployeeManagement();
                    employeeManagement.personalManagementMenu(employees,  boats);
                    break;
                case 2:
                    FleetManagement fleetManagement = new FleetManagement();
                    fleetManagement.fleetManagementMenu(boats, employees);
                    break;
                case 3:
                    System.out.println("Have a nice day!");
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    public void chargeData(){
        CargoShip ship1 = new CargoShip(1, "Titanic II", 50000.0, 40, 10000, 30, 20000.0, Engine.Diesel);
        CargoShip ship2 = new CargoShip(2, "Atlantic Carrier", 60000.0, 35, 12000, 40, 25000.0, Engine.GNL);

        boats.add(ship1);
        boats.add(ship2);

// Captain
        Captain captain1 = new Captain(1, "Juan", "Pérez López", LocalDate.of(2020, 1, 15), 5000.0, 10, 1000.0);
        Captain captain2 = new Captain(2, "María", "García Ruiz", LocalDate.of(2018, 6, 10), 5500.0, 12, 1200.0);

        employees.add(captain1);
        employees.add(captain2);

// FleetManager
        FleetManager manager1 = new FleetManager(3, "Carlos", "Sánchez Díaz", LocalDate.of(2015, 3, 20), 6000.0, 15);
        FleetManager manager2 = new FleetManager(4, "Laura", "Martínez Gómez", LocalDate.of(2017, 9, 5), 6200.0, 13);

        employees.add(manager1);
        employees.add(manager2);

// Sailor
        Sailor sailor1 = new Sailor(5, "Pedro", "López Fernández", LocalDate.of(2021, 2, 1), 2000.0, 3, 300.0);
        Sailor sailor2 = new Sailor(6, "Ana", "Torres Navarro", LocalDate.of(2022, 5, 12), 2100.0, 2, 250.0);

        employees.add(sailor1);
        employees.add(sailor2);
    }
}
