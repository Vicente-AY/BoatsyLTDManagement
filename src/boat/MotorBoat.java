package boat;

import utils.Engine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class MotorBoat extends Boat{

    private static final long serialVersionUID = 1L;
    Engine engine = null;
    int currentPassengers = 0;
    int maxPassengers = 0;

    public MotorBoat(int id, String name, double weight, double maxVelocity, double maxDistance, int maxCrew, Engine engine, int maxPassengers) {

        super(id, name, weight, maxVelocity, maxDistance, maxCrew);
        this.engine = engine;
        this.maxPassengers = maxPassengers;
    }

    public static void createMotorBoat(ArrayList<Boat> boats) {

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

        System.out.println("Indicate the name of the ship");
        String name = input.nextLine();

        System.out.println("Indicate de weight of the ship");
        double weight = input.nextDouble();
        input.nextLine();

        System.out.println("Indicate de max velocity of the ship");
        double maxVelocity = input.nextDouble();
        input.nextLine();


        System.out.println("Indicate the maximum distance of the ship");
        double maxDistance = input.nextDouble();
        input.nextLine();

        System.out.println("Indicate the maximum crew capacity of the ship");
        int maxCrew = input.nextInt();
        input.nextLine();

        System.out.println("Indicate the engine type of the ship");
        System.out.println("1. Diesel | 2. Gasoline | 3. Electric");
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
                    engine = Engine.Gasoline;
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

        System.out.println("Indicate the maximum passengers of the ship");
        int maxPassengers = input.nextInt();
        input.nextLine();

        MotorBoat newMB = new MotorBoat(id, name, weight, maxVelocity, maxDistance, maxCrew, engine, maxPassengers);
        boats.add(newMB);
        System.out.println("Motor Boat added to the fleet!");
    }

    public void load(int passengers){

        if(passengers > 0){
            if(passengers > this.maxPassengers || passengers + this.currentPassengers > this.maxPassengers){
                System.out.println("Not enough sets");
            }
            else{
                this.currentPassengers += passengers;
                System.out.println("Operation successfully. Loaded " + passengers + " passengers");
                System.out.println("New seat status: " + this.currentPassengers + "/" + this.maxPassengers);
            }
        }
        else{
            System.out.println("Invalid option");
        }
    }

    @Override
    public void unload(){
        this.currentPassengers = 0;
    }

    @Override
    public void setSail(double distance){

        if(!canSail(distance)){
            return;
        }

        if(this.currentPassengers < this.maxPassengers){
            if(!confirmDeparture()){
                System.out.println("Operation cancelled");
                return;
            }
        }
        System.out.println("Setting course");
        long minutes = (long) (distance / (this.maxVelocity / 60));
        this.sailDate = LocalDateTime.now();
        this.lastChecked = this.sailDate;
        this.dateOfArrival = this.sailDate.plusMinutes(minutes);
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
        System.out.println("Warning! The ship is not full of passengers (" + this.currentPassengers + "/" + this.maxPassengers + ")");
        System.out.println("Are you sure do you want to set sail? (Y/N)");
        String option = input.nextLine();
        if(option.equalsIgnoreCase("y") || option.equalsIgnoreCase("yes")){
            return true;
        }
        else{
            return false;
        }
    }

    public Engine getEngine() {
        return engine;
    }

    public int  getMaxPassengers() {
        return maxPassengers;
    }
    public int getCurrentPassengers(){
        return currentPassengers;
    }
}
