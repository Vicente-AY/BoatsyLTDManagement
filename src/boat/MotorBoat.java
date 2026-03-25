package boat;

import exceptions.FieldRangeOutOfBoundsException;
import utils.Engine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
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

        String name = "";
        double weight = 0.0;
        double maxVelocity =  0.0;
        double maxDistance  =  0.0;
        int maxCrew = 0;
        double maxCargo = 0.0;
        Engine engine = null;
        int maxPassengers = 0;

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

        try {
            System.out.println("Indicate de name of the ship");
            name = input.nextLine();

            if(name == null || name.trim().isEmpty()){
                name = "MotorBoat" + id;
            }

            System.out.println("Indicate de weight of the ship (Tons - Max 45)");
            weight = input.nextDouble();
            input.nextLine();
            if(weight <= 0 || weight > 45000){
                throw new FieldRangeOutOfBoundsException("The maximum Weight of a Motor Boat cannot be less or equal to 0 or surpass 45.000 Tons");
            }

            System.out.println("Indicate de max velocity of the ship (Km/h - max 260)");
            maxVelocity = input.nextDouble();
            input.nextLine();
            if(maxVelocity <= 0 || weight > 260){
                throw new FieldRangeOutOfBoundsException("The maximum Velocity of a Motor Boat cannot be less or equal to 0 or surpass 260Km/h");
            }


            System.out.println("Indicate the maximum distance of the ship (Km - max 700");
            maxDistance = input.nextDouble();
            input.nextLine();
            if(maxDistance <= 0 || maxDistance > 700){
                throw new FieldRangeOutOfBoundsException("The maximum Distance of a Motor Boat cannot be less or equal to 0 or surpass 700Km");

            }

            System.out.println("Indicate the maximum crew capacity of the ship (Max - 10 Crew Members");
            maxCrew = input.nextInt();
            input.nextLine();
            if(maxCrew <= 0 || maxCrew > 10){
                throw new FieldRangeOutOfBoundsException("The maximum Crew of a Motor Boat cannot be less or equal to 0 or surpass 10 Crew Mempers");
            }


            boolean cont = true;
            while (cont) {
                System.out.println("Indicate the engine type of the ship");
                System.out.println("1. Diesel | 2. Gasoline | 3. Electric");
                int option = input.nextInt();
                input.nextLine();
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

            System.out.println("Indicate the maximum passengers of the ship (Max 20 passengers)");
            maxPassengers = input.nextInt();
            input.nextLine();
            if(maxPassengers <= 0 || maxPassengers > 20){
                throw new FieldRangeOutOfBoundsException("The maximum Passewngers of a Motor Boat cannot be less or equal to 0 or surpass 20 passengers");
            }
        }

        catch(InputMismatchException e) {
            System.err.println("The field only accepts numbers " + e.getMessage());
            return;
        }
        catch(FieldRangeOutOfBoundsException e) {
            System.err.println("Field out of Bounds: " + e.getMessage());
            return;
        }
        catch(Exception e){
            System.err.println("General Error: " + e.getMessage());
            return;
        }

        MotorBoat newMB = new MotorBoat(id, name, weight, maxVelocity, maxDistance, maxCrew, engine, maxPassengers);
        boats.add(newMB);
        System.out.println("Motor Boat with ID: " + newMB.getId() + " Named: " + newMB.getName() + " added to the fleet!");
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
