package menu;

import boat.Boat;
import employee.Captain;
import employee.Employee;
import employee.FleetManager;
import employee.Sailor;

import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeManagement {

    Scanner input = new Scanner(System.in);

    public void personalManagementMenu(ArrayList<Employee> employees, ArrayList<Boat> boats) {
        int option = 0;
        while(true) {
            System.out.println("- - - Employee Management Menu - - -");
            System.out.println("Type your option:");
            System.out.println("1. Add Employee | 2. Remove Employee | 3. Assign Employee to a boat");
            System.out.println("4. List of Employees | 5. Employee Info | 6. Exit");
            option = input.nextInt();
            switch(option) {
                case 1:
                    addEmployee(employees);
                    break;
                case 2:
                    removeEmployee(employees);
                    break;
                case 3:
                    assignBoat(employees, boats);
                    break;
                case 4:
                    employeeList(employees);
                    break;
                case 5:
                    employeeInfo(employees);
                    break;
                case 6:
                    System.out.println("Exit Employee Management Menu");
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    public void addEmployee(ArrayList<Employee> employee){

        int option = 0;
        while(true) {
            System.out.println("Type your option:");
            System.out.println("1. Add Sailor | 2. Add Captain | 3. Add Fleet Manager");
            System.out.println("4. Back");
            option = input.nextInt();
            input.nextLine();
            switch(option) {
                case 1:
                    Sailor.createSailor(employee);
                    break;
                case 2:
                    Captain.createCaptain(employee);
                    break;
                case 3:
                    FleetManager.createFleetManager(employee);
                    break;
                case 4:
                    System.out.println("Back to the Manage Employee Menu");
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    public void removeEmployee(ArrayList<Employee> employee){

        Employee employeeToRemove = null;

        System.out.println("Type the ID of the Employee you want to remove");
        int id = input.nextInt();
        input.nextLine();

        for(Employee e: employee){
            if(e.getId() == id){
                employeeToRemove = e;
            }
        }
        if(employeeToRemove != null) {
            if (employeeToRemove instanceof Sailor) {
                System.out.println("Are you sure you want to remove Sailor " + employeeToRemove.getName() + " " + employeeToRemove.getSurnames());
            }
            if (employeeToRemove instanceof Captain) {
                System.out.println("Are you sure you want to remove Captain " + employeeToRemove.getName() + " " + employeeToRemove.getSurnames());
            }
            if (employeeToRemove instanceof FleetManager) {
                System.out.println("Are you sure you want to remove Fleet Manager " + employeeToRemove.getName() + " " + employeeToRemove.getSurnames());
            }
            System.out.println("Y / N");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                System.out.println("Removing Employee");
                employee.remove(employeeToRemove);
            } else {
                System.out.println("Cancelling");
            }
        }
        else{
            System.out.println("ID not found");
        }
    }

    public void assignBoat(ArrayList<Employee> employee, ArrayList<Boat> boats){

        Employee employeeToAssign = null;
        Boat boatToAssign = null;

        System.out.println("Type the ID of the Employee you want to Assign to a boat");
        int employeeId = input.nextInt();
        input.nextLine();

        System.out.println("Type the ID of the boat you want to Assign to");
        int boatId = input.nextInt();
        input.nextLine();

        for(Employee e: employee){
            if(e.getId() == employeeId){
                employeeToAssign = e;
            }
        }
        for(Boat boat: boats){
            if(boat.getId() == boatId){
                boatToAssign = boat;
            }
        }
        if(employeeToAssign == null) {
            System.out.println("Employee not found");
            return;
        }
        else if(boatToAssign == null){
            System.out.println("Boat not found");
            return;
        }
        else{
            if(employeeToAssign instanceof Sailor) {
                Sailor sailor = (Sailor) employeeToAssign;
                if(sailor.getAssignedBoat() == boatToAssign){
                    System.out.println("The Sailor is already assigned to that boat");
                    return;
                }
                else if(sailor.getAssignedBoat() != null){
                    System.out.println("The Sailor is already assigned to the Boat " + sailor.getAssignedBoat().getId() + " " + sailor.getAssignedBoat().getName());
                    return;
                }
                else if(boatToAssign.getCrew().size() >= boatToAssign.getMaxCrew()){
                    System.out.println("The boat is fully crewed");
                    return;
                }
                else{
                    sailor.setAssignedBoat(boatToAssign);
                    boatToAssign.getCrew().add(sailor);
                    System.out.println("Sailor " +  sailor.getId() + " " + sailor.getName() + " " + sailor.getSurnames() + " Assigned to " + boatToAssign.getId() + " " + boatToAssign.getName() + " Successfully");
                    return;
                }
            }
            else if(employeeToAssign instanceof Captain) {
                Captain captain = (Captain) employeeToAssign;
                if(captain.getAssignedBoat() == boatToAssign){
                    System.out.println("The Captain is already assigned to that boat");
                    return;
                }
                else if(captain.getAssignedBoat() != null){
                    System.out.println("The Captain is already assiged to the Boat " + captain.getAssignedBoat().getId() + " " + captain.getAssignedBoat().getName());
                    return;
                }
                else if(boatToAssign.getCaptain() != null){
                    System.out.println("The Boat has the captain " + boatToAssign.getCaptain().getId() + " " + boatToAssign.getCaptain().getName() + " " + boatToAssign.getCaptain().getSurnames());
                    return;
                }
                else{
                    captain.setAssignedBoat(boatToAssign);
                    boatToAssign.setCaptain(captain);
                    System.out.println("Captain " +  captain.getId() + " " + captain.getName() + " " + captain.getSurnames() + " Assigned to " + boatToAssign.getId() + " " + boatToAssign.getName() + " Successfully");
                    return;
                }
            }
            else if(employeeToAssign instanceof FleetManager) {
                FleetManager fleetManager = (FleetManager) employeeToAssign;
                if(fleetManager.getManagedBoats().size() > 5){
                    System.out.println("The Fleet Manager has the maximum managed boats assigned");
                    return;
                }
                else if(boatToAssign.getAssignedFM() == fleetManager){
                    System.out.println("The Fleet Manager is already managing that boat");
                    return;
                }
                else if(boatToAssign.getAssignedFM() != null){
                    System.out.println("The boat has already been assigned to the Fleet Manager " + boatToAssign.getAssignedFM().getId() + " " + boatToAssign.getAssignedFM().getName() + " " + boatToAssign.getAssignedFM().getSurnames());
                    return;
                }
                else{
                    fleetManager.getManagedBoats().add(boatToAssign);
                    boatToAssign.setAssignedFM(fleetManager);
                    System.out.println("Captain " +  fleetManager.getId() + " " + fleetManager.getName() + " " + fleetManager.getSurnames() + " Assigned to " + boatToAssign.getId() + " " + boatToAssign.getName() + " Successfully");
                    return;
                }
            }
        }
    }

    public void employeeList(ArrayList<Employee> employees) {

        for(Employee employee : employees){
            if(employee instanceof Captain) {
                System.out.println("Captain: " + employee.getId() + " " + employee.getName() + " " + employee.getSurnames());
            }
            if(employee instanceof Sailor) {
                System.out.println("Sailor: " + employee.getId() + " " + employee.getName() + " " + employee.getSurnames());
            }
            if(employee instanceof FleetManager) {
                System.out.println("FleetManager: " + employee.getId() + " " + employee.getName() + " " + employee.getSurnames());
            }
        }
    }

    public void employeeInfo(ArrayList<Employee> employees) {

        Employee infoEmployee = null;

        System.out.println("Type the id of the employee");
        int id = input.nextInt();
        for(Employee e : employees){
            if(e.getId() == id){
                infoEmployee = e;
            }
        }
        if(infoEmployee != null){
            if(infoEmployee instanceof Sailor) {
                Sailor sailorInfo = (Sailor) infoEmployee;
                System.out.println("Sailor Info");
                System.out.println(sailorInfo.getId() + " " + sailorInfo.getName() + " " + sailorInfo.getSurnames()
                        + " Contracted: " + sailorInfo.getContractStartDate() + " Salary: " + sailorInfo.getSalary()
                        + " Years of Experience " + sailorInfo.getExperience()
                        + "\nIs assigned to " + sailorInfo.getAssignedBoat().getId()
                        + " " + sailorInfo.getAssignedBoat().getName() + " This month has traveled "
                        + sailorInfo.getMonthDistance() + "Km and made " + sailorInfo.getTrips());
            }
            if(infoEmployee instanceof Captain) {
                Captain captainInfo = (Captain) infoEmployee;
                System.out.println("Captain Info");
                System.out.println(captainInfo.getId() + " " + captainInfo.getName() + " " + captainInfo.getSurnames() +
                        " " + captainInfo.getContractStartDate() + " " + captainInfo.getSalary() + " " + captainInfo.getExperience());
            }
            if(infoEmployee instanceof FleetManager) {
                FleetManager fleetManagerInfo = (FleetManager) infoEmployee;
                System.out.println("Fleet Manager Info");
                System.out.println(fleetManagerInfo.getId() + " " + fleetManagerInfo.getName() + " " + fleetManagerInfo.getSurnames() +
                        " " + fleetManagerInfo.getContractStartDate() + " " + fleetManagerInfo.getSalary() + " " + fleetManagerInfo.getExperience());
                System.out.println("Is managing the current Fleet");
                for(Boat b : fleetManagerInfo.getManagedBoats()){
                    System.out.println(b.getId() + " " + b.getName());
                }
            }
        }
    }
}
