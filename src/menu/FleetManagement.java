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
            System.out.println("4. Unassign Crew | 5. Ship Info | 6. Load Ship");
            System.out.println("7. Set Sail a Ship | 8. Ship trip Status | 9. Back");
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
                    unassignCrew(boats);
                    break;
                case 5:
                    shipInfo(boats);
                    break;
                case 6:
                    loadShip(boats);
                    break;
                case 7:
                    setSailShip(boats);
                    break;
                case 8:
                    shipTripStatus(boats);
                    break;
                case 9:
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
            if(boatToRemove.getCurrentDistanceLeft() > 0){
                System.out.println("Sorry, the ship is on duty. Wait until arrival before deleting it");
                return;
            }
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

        if(boatToAssign != null) {

            if(boatToAssign.getCurrentDistanceLeft() > 0){
                System.out.println("Sorry, the ship is on duty. Wait until arrival");
                return;
            }

            for(Employee employee : employees){

                if(boatToAssign.getCrew().size() >= boatToAssign.getMaxCrew() && boatToAssign.getCaptain() != null && boatToAssign.getAssignedFM() != null){
                    System.out.println("The boat is fully crew");
                    return;
                }

                if(employee instanceof Sailor){
                    Sailor sailorToAssign = (Sailor) employee;
                    if(sailorToAssign.getAssignedBoat() == null){
                        boatToAssign.getCrew().add(sailorToAssign);
                        sailorToAssign.setAssignedBoat(boatToAssign);
                    }
                }
                if(employee instanceof Captain) {
                    Captain captainToAssign = (Captain) employee;
                    if (captainToAssign.getAssignedBoat() == null && boatToAssign.getCaptain() == null) {
                        boatToAssign.setCaptain((Captain) employee);
                        ((Captain) employee).setAssignedBoat(boatToAssign);
                    }
                }
                if(employee instanceof FleetManager){
                    FleetManager  fleetManagerToAssign = (FleetManager) employee;
                    Boolean managinBoat = false;
                    for(Boat b : fleetManagerToAssign.getManagedBoats()){
                        if(b.getId() == boatToAssign.getId()){
                            managinBoat = true;
                        }
                    }
                    if(boatToAssign.getAssignedFM() == null && fleetManagerToAssign.getManagedBoats().size() < 5 && !managinBoat){
                        boatToAssign.setAssignedFM(fleetManagerToAssign);
                        fleetManagerToAssign.getManagedBoats().add(boatToAssign);
                    }
                }
            }
            System.out.println("Operation successfull");
            System.out.println("Boat with ID: " + boatToAssign.getId() + " Named: " + boatToAssign.getName());
            System.out.println(" Crewed with " + boatToAssign.getCrew().size() + "/" + boatToAssign.getMaxCrew() + " Sailors");
            System.out.println("Captain Assigned: " + boatToAssign.getCaptain().getName() + " " + boatToAssign.getCaptain().getSurnames());
            System.out.println("Fleet Manager Assigned: " + boatToAssign.getAssignedFM().getName() + " " + boatToAssign.getAssignedFM().getSurnames());
        }
        else{
            System.out.println("ID not bound");
        }
    }

    public void unassignCrew(ArrayList<Boat> boats){

        Boat boatUnassign = null;

        System.out.println("Type the ID of the Ship you want to Unassign Personal");
        int id =  input.nextInt();
        input.nextLine();
        for(Boat boat : boats){
            if(boat.getId() == id){
                boatUnassign = boat;
            }
        }

        if(boatUnassign != null) {

            if(boatUnassign.getCurrentDistanceLeft() > 0){
                System.out.println("The ship is no Duty. Wait until arrival");
                return;
            }

            if(boatUnassign.getCaptain() != null){
                boatUnassign.getCaptain().setAssignedBoat(null);
                boatUnassign.setCaptain(null);
            }
            if(boatUnassign.getAssignedFM() != null){
                boatUnassign.getAssignedFM().getManagedBoats().remove(boatUnassign);
                boatUnassign.setAssignedFM(null);
            }
            if(!boatUnassign.getCrew().isEmpty()) {
                for (Sailor sailor : boatUnassign.getCrew()) {
                    sailor.setAssignedBoat(null);
                }
                boatUnassign.getCrew().clear();
            }
        }
    }

    public void shipInfo(ArrayList<Boat> boats) {

        Boat boatInfo = null;

        System.out.println("Type the ID of the Ship you want Info about");
        int id = input.nextInt();
        input.nextLine();
        for (Boat boat : boats) {
            if (boat.getId() == id) {
                boatInfo = boat;
            }
        }

        if (boatInfo != null) {
            if (boatInfo instanceof MotorBoat) {
                MotorBoat motorBoat = (MotorBoat) boatInfo;
                System.out.println("Ship Class: Motor Boat");
                System.out.println("ID: " + motorBoat.getId());
                System.out.println("Name: " + motorBoat.getName());
                System.out.println("Weight: " + motorBoat.getWeight());
                System.out.println("Max Velocity: " + motorBoat.getMaxVelocity());
                System.out.println("MaxDistance: " + motorBoat.getMaxDistance());
                System.out.println("Engine type: " + motorBoat.getEngine());
                System.out.println("Current Passengers: " + motorBoat.getCurrentPassengers() + "/" + motorBoat.getMaxPassengers());
                if (motorBoat.getCurrentDistanceLeft() > 0) {
                    System.out.println("Ship on Duty. Distance Lef until arrival: " + motorBoat.getCurrentDistanceLeft());
                } else {
                    System.out.println("Ship waiting orders");
                }
                System.out.println("Current Crew: " + motorBoat.getCrew().size() + "/" + motorBoat.getMaxCrew());
                if (motorBoat.getCaptain() != null) {
                    System.out.println("Assigned Captain: " + motorBoat.getCaptain().getName() + " " + motorBoat.getCaptain().getSurnames());
                } else {
                    System.out.println("The Ship do not have an assigned Captain yet");
                }
                if (motorBoat.getAssignedFM() != null) {
                    System.out.println("Assigned Fleet Manager: " + motorBoat.getAssignedFM().getName() + " " + motorBoat.getAssignedFM().getSurnames());
                } else {
                    System.out.println("The Ship do not have an assigned Fleet Manager yet");
                }
            }
            if (boatInfo instanceof SailingBoat) {
                SailingBoat sailingBoat = (SailingBoat) boatInfo;
                System.out.println("Ship Class: Sailing Boat");
                System.out.println("ID: " + sailingBoat.getId());
                System.out.println("Name: " + sailingBoat.getName());
                System.out.println("Weight: " + sailingBoat.getWeight());
                System.out.println("Max Velocity: " + sailingBoat.getMaxVelocity());
                System.out.println("MaxDistance: " + sailingBoat.getMaxDistance());
                System.out.println("Number of Sails: " + sailingBoat.getSails());
                System.out.println("Current Passengers: " + sailingBoat.getCurrentPassengers() + "/" + sailingBoat.getMaxPassengers());
                if (sailingBoat.getCurrentDistanceLeft() > 0) {
                    System.out.println("Ship on Duty. Distance Lef until arrival: " + sailingBoat.getCurrentDistanceLeft());
                } else {
                    System.out.println("Ship waiting orders");
                }
                System.out.println("Current Crew: " + sailingBoat.getCrew().size() + "/" + sailingBoat.getMaxCrew());
                if (sailingBoat.getCaptain() != null) {
                    System.out.println("Assigned Captain: " + sailingBoat.getCaptain().getName() + " " + sailingBoat.getCaptain().getSurnames());
                } else {
                    System.out.println("Ship do not have an assigned Captain yet");
                }
                if (sailingBoat.getAssignedFM() != null) {
                    System.out.println("Assigned Fleet Manager: " + sailingBoat.getAssignedFM().getName() + " " + sailingBoat.getAssignedFM().getSurnames());
                } else {
                    System.out.println("Ship do not have an assigned FM yet");
                }
            }
            if (boatInfo instanceof CargoShip) {
                CargoShip cargoShip = (CargoShip) boatInfo;
                System.out.println("Ship Class: Cargo Shio");
                System.out.println("ID: " + cargoShip.getId());
                System.out.println("Name: " + cargoShip.getName());
                System.out.println("Weight: " + cargoShip.getWeight());
                System.out.println("Max Velocity: " + cargoShip.getMaxVelocity());
                System.out.println("MaxDistance: " + cargoShip.getMaxDistance());
                System.out.println("Engine type: " + cargoShip.getEngine());
                System.out.println("Current Cargo: " + cargoShip.getCurrentCargo() + "/" + cargoShip.getMaxCargo());
                if (cargoShip.getCurrentDistanceLeft() > 0) {
                    System.out.println("Ship on Duty. Distance Lef until arrival: " + cargoShip.getCurrentDistanceLeft());
                } else {
                    System.out.println("Ship waiting orders");
                }
                System.out.println("Current Crew: " + cargoShip.getCrew().size() + "/" + cargoShip.getMaxCrew());
                if (cargoShip.getCaptain() != null) {
                    System.out.println("Assigned Captain: " + cargoShip.getCaptain().getName() + " " + cargoShip.getCaptain().getSurnames());
                } else {
                    System.out.println("The ship do not have an assigned Captain yet");
                }
                if (cargoShip.getCaptain() != null) {
                    System.out.println("Assigned Fleet Manager: " + cargoShip.getAssignedFM().getName() + " " + cargoShip.getAssignedFM().getSurnames());
                } else {
                    System.out.println("The ship do not have an assigned Fleet Manager yet");
                }
            }
        }
    }

    public void loadShip(ArrayList<Boat> boats) {

        Boat boatToLoad = null;

        System.out.println("Type the ID of the ship you want to load");
        int id = input.nextInt();
        input.nextLine();

        for(Boat boat : boats) {
            if(boat.getId() == id) {
                boatToLoad = boat;
            }
        }

        if(boatToLoad != null){
            if(boatToLoad instanceof CargoShip) {
                CargoShip cargoShip = (CargoShip) boatToLoad;
                System.out.println("Type the amount of tons you want to load");
                double  tons = input.nextDouble();
                input.nextLine();
                cargoShip.load(tons);
            }
            else{
                System.out.println("Type the amount of passengers you want to load");
                int passengers =  input.nextInt();
                input.nextLine();
                if(boatToLoad instanceof MotorBoat) {
                    MotorBoat motorBoat = (MotorBoat) boatToLoad;
                    motorBoat.load(passengers);
                }
                if(boatToLoad instanceof SailingBoat) {
                    SailingBoat sailingBoat = (SailingBoat) boatToLoad;
                    sailingBoat.load(passengers);
                }
            }
        }
    }

    public void setSailShip(ArrayList<Boat> boats) {

        Boat boatToSail = null;

        System.out.println("Type the ID of the ship you want to load");
        int id = input.nextInt();
        input.nextLine();

        for(Boat boat : boats) {
            if(boat.getId() == id) {
                boatToSail = boat;
            }
        }

        if(boatToSail != null){
            System.out.println("Type the distance you want the ship to trip");
            double  distance = input.nextDouble();
            input.nextLine();
            boatToSail.setSail(distance);
        }
    }

    public void shipTripStatus(ArrayList<Boat> boats) {

        Boat boatTripStatus = null;

        System.out.println("Type the ID of the ship you want to see its status");
        int id = input.nextInt();
        input.nextLine();

        for(Boat boat : boats) {
            if(boat.getId() == id) {
                boatTripStatus = boat;
            }
        }

        if(boatTripStatus != null){

            if(boatTripStatus.getCurrentDistanceLeft() <= 0){
                System.out.println("The selected ship is waiting at the dock");
            }
            else {
                if (boatTripStatus instanceof CargoShip) {
                    CargoShip cargoShip = (CargoShip) boatTripStatus;

                    System.out.println("Ship Class: Cargo Ship");
                    System.out.println("ID: " + cargoShip.getId());
                    System.out.println("Sailing Date: " + cargoShip.getSailDate());
                    System.out.println("Current Cargo: " + cargoShip.getCurrentCargo() + "/" +  cargoShip.getMaxCargo());
                    System.out.println("Current Velocity: " + cargoShip.getMaxVelocity());
                    System.out.println("Current Crew: " + cargoShip.getCrew().size() + "/" + cargoShip.getMaxCrew());
                    System.out.println("Current Captain: " + cargoShip.getCaptain().getName() + " " + cargoShip.getCaptain().getSurnames());
                    System.out.println("Current Fleet Manager: " + cargoShip.getAssignedFM().getName() + " " + cargoShip.getAssignedFM().getSurnames());
                    System.out.println("Current Distance left: " + cargoShip.getCurrentDistanceLeft());
                    System.out.println("Approximate arrival Date: " + cargoShip.getDateOfArrival());
                }
                if(boatTripStatus instanceof MotorBoat) {
                    MotorBoat motorBoat = (MotorBoat) boatTripStatus;

                    System.out.println("Ship Class: Motor Boat");
                    System.out.println("ID: " + motorBoat.getId());
                    System.out.println("Sailing Date: " + motorBoat.getSailDate());
                    System.out.println("Passengers aboard: " + motorBoat.getCurrentPassengers() + "/" + motorBoat.getMaxPassengers());
                    System.out.println("Current Velocity: " + motorBoat.getMaxVelocity());
                    System.out.println("Current Crew: " + motorBoat.getCrew().size() + "/" + motorBoat.getMaxCrew());
                    System.out.println("Current Captain: " + motorBoat.getCaptain().getName() + " " + motorBoat.getCaptain().getSurnames());
                    System.out.println("Current Fleet Manager: " + motorBoat.getAssignedFM().getName() + " " + motorBoat.getAssignedFM().getSurnames());
                    System.out.println("Current Distance left: " + motorBoat.getCurrentDistanceLeft());
                    System.out.println("Approximate arrival Date: " + motorBoat.getDateOfArrival());
                }
                if(boatTripStatus instanceof MotorBoat) {
                    SailingBoat sailingBoat = (SailingBoat) boatTripStatus;

                    System.out.println("Ship Class: Sailing Boat");
                    System.out.println("ID: " + sailingBoat.getId());
                    System.out.println("Sailing Date: " + sailingBoat.getSailDate());
                    System.out.println("Passengers aboard: " + sailingBoat.getCurrentPassengers() + "/" + sailingBoat.getMaxPassengers());
                    System.out.println("Current Velocity: " + sailingBoat.getMaxVelocity());
                    System.out.println("Current Crew: " + sailingBoat.getCrew().size() + "/" + sailingBoat.getMaxCrew());
                    System.out.println("Current Captain: " + sailingBoat.getCaptain().getName() + " " + sailingBoat.getCaptain().getSurnames());
                    System.out.println("Current Fleet Manager: " + sailingBoat.getAssignedFM().getName() + " " + sailingBoat.getAssignedFM().getSurnames());
                    System.out.println("Current Distance left: " + sailingBoat.getCurrentDistanceLeft());
                    System.out.println("Approximate arrival Date: " + sailingBoat.getDateOfArrival());
                }
            }
        }
        else{
            System.out.println("ID not found");
        }
    }
}
