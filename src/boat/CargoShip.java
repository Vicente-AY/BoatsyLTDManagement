package boat;

import utils.Engine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class CargoShip extends Boat {

    double maxCargo;
    double currentCargo = 0.0;
    Engine engine;

    public CargoShip(int id, String name, double weight, int maxVelocity, int maxDistance, int maxCrew, double maxCargo, Engine engine) {
        super(id, name, weight, maxVelocity, maxDistance, maxCrew);
        this.maxCargo = maxCargo;
        this.engine = engine;
    }

    public static void createCargo(ArrayList<Boat> boats) {

        Scanner input = new Scanner(System.in);

        System.out.println("Creating Cargo Ship");

        int id = 0;
        for (Boat b : boats) {
            if(!boats.isEmpty()){
                if(b.getId() > id) {
                    id = b.getId();
                }
            }
        }
        id++;

        System.out.println("Indicate de name of the ship");
        String name = input.nextLine();

        System.out.println("Indicate de weight of the ship");
        double weight = input.nextDouble();
        input.nextLine();

        System.out.println("Indicate de max velocity of the ship");
        int maxVelocity = input.nextInt();
        input.nextLine();


        System.out.println("Indicate the maximum distance of the ship");
        int maxDistance = input.nextInt();
        input.nextLine();

        System.out.println("Indicate the maximum crew capacity of the ship");
        int maxCrew = input.nextInt();
        input.nextLine();

        System.out.println("Indicate the maximum cargo of the ship");
        double maxCargo = input.nextDouble();
        input.nextLine();

        System.out.println("Indicate the engine type of the ship");
        System.out.println("1. Diesel | 2. GNL | 3. Electric");
        int option = input.nextInt();
        input.nextLine();
        Engine engine = null;
        boolean cont = true;
        while(cont) {
            switch (option) {
                case 1:
                    engine = Engine.Diesel;
                    cont = false;
                    break;
                case 2:
                    engine = Engine.GNL;
                    cont = false;
                    break;
                case 3:
                    engine = Engine.Electric;
                    cont = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        CargoShip newCargoShip = new CargoShip(id, name, weight, maxVelocity, maxDistance, maxCrew, maxCargo, engine);
        boats.add(newCargoShip);
        System.out.println("Cargo Ship added to the fleet!");
    }

    public void loadCargo(double cargo){

        Scanner input = new Scanner(System.in);

        if(cargo > maxCargo || cargo + this.currentCargo > maxCargo){
            System.out.println("Not enough cargo");
        }
        else{
            this.currentCargo = cargo;
            System.out.println("Cargo loaded!");
        }
    }

    public void unloadCargo(){
        this.currentCargo = 0.0;
        System.out.println("Cargo unloaded!");
    }

    @Override
    public void setSail(double distance){

        if(!canSail(distance)){
            return;
        }

        if(this.currentCargo < this.maxCargo){
            if(!confirmDeparture()){
                System.out.println("Operation cancelled");
            }
        }
        System.out.println("Setting course");
        long minutes = (long) ((distance / this.maxVelocity) *10);
        double hours = (double) minutes / 60;
        System.out.println(hours  + " hours until arrival");
        this.sailDate = LocalDateTime.now();
        this.lastChecked = this.sailDate;
        this.dateOfArrival = this.sailDate.plusHours(minutes);
        this.currentDistanceLeft = distance;
    }

    public boolean canSail(double distance){

        if(distance > this.maxDistance){
            System.out.println("Sail out of bounds!");
            return false;
        }
        if(this.captain == null){
            System.out.println("The captain has not been set!");
            return false;
        }
        if(this.assignedFM == null){
            System.out.println("The Fleet Manager has not been set!");
            return false;
        }
        if(this.crew.size() < (this.maxCrew / 2)){
            System.out.println("Not enough crew!");
            return false;
        }
        return true;
    }

    public boolean confirmDeparture(){

        Scanner input = new Scanner(System.in);
        System.out.println("Warning! The ship is not full (" + this.currentCargo + "/" + this.maxCargo + ")");
        System.out.println("Are you sure do you want to set sail? (Y/N)");
        String option = input.nextLine();
        if(option.equalsIgnoreCase("y") || option.equalsIgnoreCase("yes")){
            return true;
        }
        else{
            return false;
        }
    }

    public Engine getEngine(){
        return engine;
    }

    public double getCurrentCargo(){
        return currentCargo;
    }
    public double getMaxCargo(){
        return maxCargo;
    }
}
