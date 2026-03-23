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

    Data dataAccess = new Data();

    public void displayMenu() {

        ArrayList<Boat> boats = dataAccess.chargeBoats();
        ArrayList<Employee> employees = dataAccess.chargeEmployees();


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
                    dataAccess.saveBoats(boats);
                    dataAccess.saveEmployees(employees);
                    System.out.println("Have a nice day!");
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }
}
