package boat;

import utils.Engine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class SailingBoat extends Boat{

    private static final long serialVersionUID = 1L;
    int sails = 0;
    int maxPassengers = 0;
    int currentPassengers = 0;

    public SailingBoat(int id, String name, double weight, int maxVelocity, int maxDistance, int maxCrew, int sails, int maxPassengers) {
        super(id, name, weight, maxVelocity, maxDistance, maxCrew);
        this.sails = sails;
        this.maxPassengers = maxPassengers;
    }

    public static void createSailingBoat(ArrayList<Boat> boats){

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
        int maxVelocity = input.nextInt();
        input.nextLine();


        System.out.println("Indicate the maximum distance of the ship");
        int maxDistance = input.nextInt();
        input.nextLine();

        System.out.println("Indicate the maximum crew of the ship");
        int maxCrew = input.nextInt();
        input.nextLine();

        System.out.println("Indicate the amount of sails of the ship");
        int sails = input.nextInt();
        input.nextLine();

        System.out.println("Indicate the maximum passengers of the ship");
        int maxPassengers = input.nextInt();
        input.nextLine();


        SailingBoat newSailing = new SailingBoat(id, name, weight, maxVelocity, maxDistance, maxCrew, sails, maxPassengers);
        boats.add(newSailing);
        System.out.println("Sailing Boat added to the fleet!");
    }

    public void load(int passengers){

        if(passengers > 0) {
            if (passengers > this.maxPassengers || passengers + this.currentPassengers > this.maxPassengers) {
                System.out.println("Not enough seats");
            } else {
                this.currentPassengers += passengers;
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
        long minutes = (long) (distance / this.maxVelocity);
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

    public int getSails(){
        return sails;
    }
    public int getMaxPassengers(){
        return maxPassengers;
    }
    public int getCurrentPassengers(){
        return currentPassengers;
    }
}
