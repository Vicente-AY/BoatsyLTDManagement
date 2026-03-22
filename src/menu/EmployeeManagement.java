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
            System.out.println("1. Add Employee | 2. Remove Employee | 3. Assign Employee to a boat");
            System.out.println("4. List of Employees | 5. Employee Info | 6. Back");
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
                    System.out.println("Back to Main Menu");
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
            System.out.println("Select the type of employee you want to add");
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

    public void removeEmployee(ArrayList<Employee> employees){

        Employee employeeToRemove = null;

        System.out.println("Type the ID of the Employee you want to remove");
        int id = input.nextInt();
        input.nextLine();

        for(Employee e: employees){
            if(e.getId() == id){
                employeeToRemove = e;
            }
        }
        if(employeeToRemove != null) {
            if (employeeToRemove instanceof Sailor) {
                System.out.println("Are you sure you want to remove Sailor " + employeeToRemove.getName() + " " + employeeToRemove.getSurnames() + "?");
            }
            if (employeeToRemove instanceof Captain) {
                System.out.println("Are you sure you want to remove Captain " + employeeToRemove.getName() + " " + employeeToRemove.getSurnames() + "?");
            }
            if (employeeToRemove instanceof FleetManager) {
                System.out.println("Are you sure you want to remove Fleet Manager " + employeeToRemove.getName() + " " + employeeToRemove.getSurnames() + "?");
            }
            System.out.println("Y / N");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                employees.remove(employeeToRemove);
                System.out.println("Employee " +  employeeToRemove.getName() + " " + employeeToRemove.getSurnames() + " removed successfully");
            } else {
                System.out.println("Cancelling operation");
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

        for(Employee e: employee){
            if(e.getId() == employeeId){
                employeeToAssign = e;
            }
        }
        if(employeeToAssign == null) {
            System.out.println("Employee not found");
            return;
        }

        System.out.println("Type the ID of the boat you want to Assign to");
        int boatId = input.nextInt();
        input.nextLine();

        for(Boat boat: boats){
            if(boat.getId() == boatId){
                boatToAssign = boat;
            }
        }
        if(boatToAssign == null){
            System.out.println("Boat not found");
            return;
        }
        if(boatToAssign.getCurrentDistanceLeft() > 0){
            System.out.println("The ship is already on trip. Wait until they arrive to a port to change its crew");
            return;
        }

        if(employeeToAssign instanceof Sailor) {
            Sailor sailor = (Sailor) employeeToAssign;
            if(sailor.getAssignedBoat() == boatToAssign){
                System.out.println("The Sailor is already assigned to that boat");
                return;
            }
            else if(boatToAssign.getCrew().size() >= boatToAssign.getMaxCrew()){
                System.out.println("The boat is fully crewed");
                return;
            }
            else if(sailor.getAssignedBoat() != null){
                System.out.println("The Sailor is already assigned to the Boat with ID: " + sailor.getAssignedBoat().getId() + " Named: " + sailor.getAssignedBoat().getName());
                if(sailor.getAssignedBoat().getCurrentDistanceLeft() <= 0){
                    boolean change = changeBoat();
                    if(change){
                        System.out.println("Changing Ship from " + sailor.getAssignedBoat().getName() + " to " + boatToAssign.getName());
                        sailor.getAssignedBoat().getCrew().remove(sailor);
                        sailor.setAssignedBoat(boatToAssign);
                        boatToAssign.getCrew().add(sailor);
                        System.out.println("Change Completed");
                    }
                }
                else{
                    System.out.println("Wait until the Ship arrive to a port to change its crew");
                }
            }
            else{
                sailor.setAssignedBoat(boatToAssign);
                boatToAssign.getCrew().add(sailor);
                System.out.println("Sailor " + sailor.getName() + " " + sailor.getSurnames() + " Assigned to boat ID: " + boatToAssign.getId() + " named: " + boatToAssign.getName() + " Successfully");
                return;
            }
        }
        else if(employeeToAssign instanceof Captain) {
            Captain captain = (Captain) employeeToAssign;
            if(captain.getAssignedBoat() == boatToAssign){
                System.out.println("The Captain is already assigned to that boat");
                return;
            }
            if(captain.getAssignedBoat() != null || boatToAssign.getCaptain() != null) {

                if(captain.getAssignedBoat().getCurrentDistanceLeft() >= 0){
                    System.out.println("Selected Captain is assigned to the Boat with ID: " + captain.getAssignedBoat().getId() + " Named: " + captain.getAssignedBoat().getName() + " which is currently on a trip");
                    System.out.println("Wait until arrival to change Captain");
                    return;
                }

                if(captain.getAssignedBoat() != null && boatToAssign.getCaptain() != null) {
                    System.out.println("Caution! Both, selected Captain and Ship has already been assigned.");
                    System.out.println("Selected Captain is assigned to the Boat with ID: " + captain.getAssignedBoat().getId() + " Named: " + captain.getAssignedBoat().getName());
                    System.out.println("And selected Ship has Captain with ID: " + boatToAssign.getCaptain().getId() + " Named: " + boatToAssign.getCaptain().getName());
                    System.out.println("Choose your action");
                    System.out.println("1. Exchange Captains and Ships | 2. Just assign selected Captain to the selected Boat | 3. Cancel");
                    int option = input.nextInt();
                    input.nextLine();

                    Boat boatToExchange = captain.getAssignedBoat();
                    Captain captainToExchange = boatToAssign.getCaptain();

                    while(true) {
                        switch (option) {
                            case 1:
                                captain.setAssignedBoat(boatToAssign);
                                boatToAssign.setCaptain(captain);

                                captainToExchange.setAssignedBoat(boatToExchange);
                                boatToExchange.setCaptain(captainToExchange);
                                System.out.println("Exchange Completed");
                                break;
                            case 2:
                                boatToAssign.setCaptain(captain);
                                captain.setAssignedBoat(boatToAssign);

                                boatToExchange.setCaptain(null);
                                captainToExchange.setAssignedBoat(null);
                                System.out.println("Change Completed");
                                break;
                            case 3:
                                System.out.println("Cancelling operation");
                                return;
                            default:
                                System.out.println("Invalid option");
                                break;
                        }
                    }
                }

                if (captain.getAssignedBoat() != null) {
                    System.out.println("The Captain is already assigned to the Boat with ID: " + captain.getAssignedBoat().getId() + " Named: " + captain.getAssignedBoat().getName());
                    if (captain.getAssignedBoat().getCurrentDistanceLeft() >= 0) {
                        boolean change = changeBoat();
                        if (change) {
                            System.out.println("Changing assigned Ship from " + captain.getAssignedBoat().getName() + " to " + boatToAssign.getName());
                            captain.getAssignedBoat().setCaptain(null);
                            captain.setAssignedBoat(boatToAssign);
                            boatToAssign.setCaptain(captain);
                            System.out.println("Change Completed");
                        }
                    }
                }
                if (boatToAssign.getCaptain() != null) {
                    System.out.println("The Boat has already the captain " + boatToAssign.getCaptain().getName() + " " + boatToAssign.getCaptain().getSurnames() + " Assigned");
                    boolean change = changeBoat();
                    if(change){
                        System.out.println("Changing assigned Captain from " + boatToAssign.getCaptain().getName() + " " + boatToAssign.getCaptain().getSurnames() + " to " + captain.getName() + " " + captain.getSurnames());
                        boatToAssign.getCaptain().setAssignedBoat(null);
                        boatToAssign.setCaptain(captain);
                        captain.setAssignedBoat(boatToAssign);
                        System.out.println("Change completed");
                    }
                }
            }
            else{
                captain.setAssignedBoat(boatToAssign);
                boatToAssign.setCaptain(captain);
                System.out.println("Captain " +  captain.getName() + " " + captain.getSurnames() + " Assigned to " + boatToAssign.getName() + " Successfully");
                return;
            }
        }
        else if(employeeToAssign instanceof FleetManager) {
            FleetManager fleetManager = (FleetManager) employeeToAssign;
            if(fleetManager.getManagedBoats().size() >= 5){
                System.out.println("The Fleet Manager has the maximum managed boats assigned");
                return;
            }
            else if(boatToAssign.getAssignedFM() == fleetManager){
                System.out.println("The Fleet Manager is already managing that boat");
                return;
            }
            else if(boatToAssign.getAssignedFM() != null){
                System.out.println("The boat has already been assigned to the Fleet Manager " + boatToAssign.getAssignedFM().getId() + " " + boatToAssign.getAssignedFM().getName() + " " + boatToAssign.getAssignedFM().getSurnames());
                boolean change = changeBoat();
                if(change){
                    boatToAssign.getAssignedFM().getManagedBoats().remove(boatToAssign);
                    boatToAssign.setAssignedFM(fleetManager);
                    fleetManager.getManagedBoats().add(boatToAssign);
                    System.out.println("Change completed");
                }
            }
            else{
                fleetManager.getManagedBoats().add(boatToAssign);
                boatToAssign.setAssignedFM(fleetManager);
                System.out.println("Captain " +  fleetManager.getId() + " " + fleetManager.getName() + " " + fleetManager.getSurnames() + " Assigned to " + boatToAssign.getId() + " " + boatToAssign.getName() + " Successfully");
                return;
            }
        }
    }

    public void employeeList(ArrayList<Employee> employees) {

        for(Employee employee : employees){
            if(employee instanceof Captain) {
                System.out.println("Employee ID: " + employee.getId() + " Captain: " + employee.getName() + " " + employee.getSurnames());
            }
            if(employee instanceof Sailor) {
                System.out.println("Employee ID: " + employee.getId() + " Sailor: " + employee.getName() + " " + employee.getSurnames());
            }
            if(employee instanceof FleetManager) {
                System.out.println("Employee ID: " + employee.getId() + " Fleet Manager " + employee.getName() + " " + employee.getSurnames());
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
                System.out.println("ID: " +  sailorInfo.getId());
                System.out.println("Full name: " + sailorInfo.getName() + " " +  sailorInfo.getSurnames());
                System.out.println("Contracted: " +  sailorInfo.getContractStartDate());
                System.out.println("Salary: " +  sailorInfo.getSalary());
                System.out.println("Years of experience: " + sailorInfo.getExperience());
                if(sailorInfo.getAssignedBoat() != null) {
                    System.out.println("Is assigned to boat ID: " + sailorInfo.getAssignedBoat().getId() + " Named: " + sailorInfo.getAssignedBoat().getName());
                }
                else{
                    System.out.println("The current Sailor is no assigned to a ship");
                }
                System.out.println("This month has traveled: " + sailorInfo.getMonthDistance() + "Km and made " + sailorInfo.getTrips() + " trips");
            }
            if(infoEmployee instanceof Captain) {
                Captain captainInfo = (Captain) infoEmployee;
                System.out.println("Captain Info");
                System.out.println("ID: " +  captainInfo.getId());
                System.out.println("Full name: " + captainInfo.getName() + " " +  captainInfo.getSurnames());
                System.out.println("Contracted: " +  captainInfo.getContractStartDate());
                System.out.println("Salary: " +  captainInfo.getSalary());
                System.out.println("Years of experience: " + captainInfo.getExperience());
                if(captainInfo.getAssignedBoat() != null) {
                    System.out.println("Is assigned to boat ID: " + captainInfo.getAssignedBoat().getId() + " Named: " + captainInfo.getAssignedBoat().getName());
                }
                else{
                    System.out.println("The current Captain is not assigned to a ship");
                }
                System.out.println("This month has traveled: " + captainInfo.getMonthDistance() + "Km and made " + captainInfo.getTrips() + " trips");
            }
            if(infoEmployee instanceof FleetManager) {
                FleetManager fleetManagerInfo = (FleetManager) infoEmployee;
                System.out.println("Fleet Manager Info");
                System.out.println("ID: " +  fleetManagerInfo.getId());
                System.out.println("Full name: " + fleetManagerInfo.getName() + " " +  fleetManagerInfo.getSurnames());
                System.out.println("Contracted: " +  fleetManagerInfo.getContractStartDate());
                System.out.println("Salary: " +  fleetManagerInfo.getSalary());
                System.out.println("Years of experience: " + fleetManagerInfo.getExperience());
                if(!fleetManagerInfo.getManagedBoats().isEmpty()) {
                    System.out.println("Is managing the current Fleet");
                    for (Boat b : fleetManagerInfo.getManagedBoats()) {
                        System.out.println(b.getId() + " " + b.getName());
                    }
                }
                else{
                    System.out.println("The selected Fleet Manager is not managing any ship");
                }
            }
        }
    }

    public boolean changeBoat(){

        System.out.println("Do you want to change? (Y/N)");
        String choice = input.next();
        if(choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")){
            return true;
        }
        else{
            System.out.println("Cancelling operation");
            return false;
        }
    }
}
