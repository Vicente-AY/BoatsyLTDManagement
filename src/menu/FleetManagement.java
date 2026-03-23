package menu;

import boat.*;
import com.sun.security.jgss.GSSUtil;
import employee.*;

import java.util.ArrayList;
import java.util.Scanner;

public class FleetManagement {

    Scanner input = new Scanner(System.in);

    public void fleetManagementMenu(ArrayList<Boat> boats, ArrayList<Employee> employees){

        int option = 0;

        while(true) {
            System.out.println("- - - Fleet Management Menu - - -");
            System.out.println("1. Add Ship | 2. Remove Ship | 3. Assign Crew Automatically");
            System.out.println("4. Ship Info | 5. Back");
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
                    shipInfo(boats);
                case 5:
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
                case 5:
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
            if (boatToRemove.getAssignedFM() != null || boatToRemove.getCaptain() != null || boatToRemove.getCrew().size() > 0) {
                System.out.println("Sorry, the ship as already personal on board. Remove them first before deleting the ship");
                return;
            }
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
            System.out.println("Ship ID not found");
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

    public void shipInfo(ArrayList<Boat> boats){

        Boat boatInfo = null;

        System.out.println("Type the ID of the Ship you want to Info");
        int id =  input.nextInt();
        input.nextLine();
        for(Boat boat : boats){
            if(boat.getId() == id){
                boatInfo = boat;
            }
        }

        if(boatInfo != null){
            if(boatInfo instanceof MotorBoat){
                MotorBoat motorBoat = (MotorBoat) boatInfo;
                System.out.println("Ship Class: Motor Boat");
                System.out.println("ID: " + motorBoat.getId());
                System.out.println("Name: " + motorBoat.getName());
                System.out.println("Weight: " + motorBoat.getWeight());
                System.out.println("Max Velocity: " + motorBoat.getMaxVelocity());
                System.out.println("MaxDistance: " + motorBoat.getMaxDistance());
                System.out.println("Engine type: " + motorBoat.getEngine());
                System.out.println("Current Passengers: " + motorBoat.getCurrentPassengers() + "/" + motorBoat.getMaxPassengers());
                if(motorBoat.getCurrentDistanceLeft() > 0){
                    System.out.println("Ship on Duty. Distance Lef until arrival: " + motorBoat.getCurrentDistanceLeft());
                }
                else{
                    System.out.println("Ship waiting orders");
                }
                System.out.println("Current Crew: " + motorBoat.getCrew().size() + "/" +  motorBoat.getMaxCrew());
                System.out.println("Assigned Captain: " + motorBoat.getCaptain().getName() + " " + motorBoat.getCaptain().getSurnames());
                System.out.println("Assigned Fleet Manager: " + motorBoat.getAssignedFM().getName() + " " + motorBoat.getAssignedFM().getSurnames());

            }
            if(boatInfo instanceof SailingBoat){
                SailingBoat sailingBoat = (SailingBoat) boatInfo;
                System.out.println("Ship Class: Sailing Boat");
                System.out.println("ID: " + sailingBoat.getId());
                System.out.println("Name: " + sailingBoat.getName());
                System.out.println("Weight: " + sailingBoat.getWeight());
                System.out.println("Max Velocity: " + sailingBoat.getMaxVelocity());
                System.out.println("MaxDistance: " + sailingBoat.getMaxDistance());
                System.out.println("Number of Sails: " + sailingBoat.getSails());
                System.out.println("Current Passengers: " + sailingBoat.getCurrentPassengers() + "/" + sailingBoat.getMaxPassengers());
                if(sailingBoat.getCurrentDistanceLeft() > 0){
                    System.out.println("Ship on Duty. Distance Lef until arrival: " + sailingBoat.getCurrentDistanceLeft());
                }
                else{
                    System.out.println("Ship waiting orders");
                }
                System.out.println("Current Crew: " + sailingBoat.getCrew().size() + "/" +  sailingBoat.getMaxCrew());
                System.out.println("Assigned Captain: " + sailingBoat.getCaptain().getName() + " " + sailingBoat.getCaptain().getSurnames());
                System.out.println("Assigned Fleet Manager: " + sailingBoat.getAssignedFM().getName() + " " + sailingBoat.getAssignedFM().getSurnames());

            }
            if(boatInfo instanceof CargoShip){
                CargoShip cargoShip = (CargoShip) boatInfo;
                System.out.println("Ship Class: Cargo Shio");
                System.out.println("ID: " + cargoShip.getId());
                System.out.println("Name: " + cargoShip.getName());
                System.out.println("Weight: " + cargoShip.getWeight());
                System.out.println("Max Velocity: " + cargoShip.getMaxVelocity());
                System.out.println("MaxDistance: " + cargoShip.getMaxDistance());
                System.out.println("Engine type: " + cargoShip.getEngine());
                System.out.println("Current Cargo: " + cargoShip.getCurrentCargo() + "/" + cargoShip.getMaxCargo());
                if(cargoShip.getCurrentDistanceLeft() > 0){
                    System.out.println("Ship on Duty. Distance Lef until arrival: " + cargoShip.getCurrentDistanceLeft());
                }
                else{
                    System.out.println("Ship waiting orders");
                }
                System.out.println("Current Crew: " + cargoShip.getCrew().size() + "/" +  cargoShip.getMaxCrew());
                System.out.println("Assigned Captain: " + cargoShip.getCaptain().getName() + " " + cargoShip.getCaptain().getSurnames());
                System.out.println("Assigned Fleet Manager: " + cargoShip.getAssignedFM().getName() + " " + cargoShip.getAssignedFM().getSurnames());


            }

        }
    }
}
