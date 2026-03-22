package menu;

import boat.*;
import employee.*;

import java.util.ArrayList;
import java.util.Scanner;

public class FleetManagement {

    Scanner input = new Scanner(System.in);

    public void fleetManagementMenu(ArrayList<Boat> boats, ArrayList<Employee> employees){

        int option = 0;

        while(true) {
            System.out.println("- - - Fleet Management Menu - - -");
            System.out.println("1. Add Boat | 2. Remove Boat | 3. Assign Crew Automatically");
            System.out.println("4. Back");
            option = input.nextInt();
            input.nextLine();
            switch (option) {
                case 1:
                    addBoat(boats);
                    break;
                case 2:
                    removeBoat(boats);
                    break;
                case 3:
                    assignCrew(boats, employees);
                    break;
                case 4:
                    System.out.println("Back to Main Manu");
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    public void addBoat(ArrayList<Boat> boats){

        int option = 0;

        while(true) {
            System.out.println("Select the type of Boat you want to add");
            System.out.println("1. Add Cargo Ship | 2. Add Motor Boat | 3. Add Sailing Boat");
            System.out.println("4. Back");
            option = input.nextInt();
            input.nextLine();
            switch (option) {
                case 1:
                    CargoShip.createCargo(boats);
                    break;
                case 2:
                    MotorBoat.createMotorBoat(boats);
                    break;
                case 3:
                    SailingBoat.createSailingBoat(boats);
                    break;
                case 4:
                    System.out.println("Back to the Manage Fleet Menu");
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    public void removeBoat(ArrayList<Boat> boats){

        Boat boatToRemove = null;

        System.out.println("Type the ID of the Boat you want to remove");
        int id =  input.nextInt();
        input.nextLine();
        for(Boat boat : boats){
            if(boat.getId() == id){
                boatToRemove = boat;
            }
        }
        if(boatToRemove != null) {
            if (boatToRemove instanceof CargoShip) {
                System.out.println("Are you sure you want to remove Cargo Ship " + boatToRemove.getId() + " " + boatToRemove.getName() + "?");
            }
            if (boatToRemove instanceof MotorBoat) {
                System.out.println("Are you sure you want to remove Motor Boat " + boatToRemove.getId() + " " + boatToRemove.getName() + "?");
            }
            if (boatToRemove instanceof SailingBoat) {
                System.out.println("Are you sure you want to remove Sailing Ship " + boatToRemove.getId() + " " + boatToRemove.getName() + "?");
            }
            System.out.println("Y / N");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                boats.remove(boatToRemove);
                System.out.println("Boat " + boatToRemove.getId() + " " + boatToRemove.getName() + " removed successfully");
            } else {
                System.out.println("Cancelling operation");
            }
        }
        else{
            System.out.println("ID not bound");
        }
    }

    public void assignCrew(ArrayList<Boat> boats, ArrayList<Employee> employees){

        Boat boatToAssign = null;

        System.out.println("Type the ID of the Boat you want to assign crew");
        int id =  input.nextInt();
        input.nextLine();
        for(Boat boat : boats){
            if(boat.getId() == id){
                boatToAssign = boat;
            }
        }
        if(boatToAssign.getCrew().size() >= boatToAssign.getMaxCrew()){
            System.out.println("The boat is fully crew");
            return;
        }
        if(boatToAssign != null) {
            for(Employee employee : employees){
                if(employee instanceof Sailor){
                    Sailor sailorToAssign = (Sailor) employee;
                    if(sailorToAssign.getAssignedBoat() == null){
                        boatToAssign.getCrew().add(sailorToAssign);
                        sailorToAssign.setAssignedBoat(boatToAssign);
                        if(boatToAssign.getCrew().size() >= boatToAssign.getMaxCrew()){
                            break;
                        }
                    }
                }
            }
        }
        else{
            System.out.println("ID not bound");
        }
    }


}
