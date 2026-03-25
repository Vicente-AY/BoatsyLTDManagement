package boat;

import exceptions.FieldRangeOutOfBoundsException;
import utils.Engine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CargoShip extends Boat {

    private static final long serialVersionUID = 1L;
    double maxCargo;
    double currentCargo = 0.0;
    Engine engine;

    public CargoShip(int id, String name, double weight, double maxVelocity, double maxDistance, int maxCrew, double maxCargo, Engine engine) {
        super(id, name, weight, maxVelocity, maxDistance, maxCrew);
        this.maxCargo = maxCargo;
        this.engine = engine;
    }

    public static void createCargo(ArrayList<Boat> boats) {

        Scanner input = new Scanner(System.in);

        String name = "";
        double weight = 0.0;
        double maxVelocity =  0.0;
        double maxDistance  =  0.0;
        int maxCrew = 0;
        double maxCargo = 0.0;
        Engine engine = null;

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
            if(name == null || name.isEmpty() || name == ""){
                name = "CargoShip" + id;
            }

            System.out.println("Indicate de weight of the ship (Tons without load - Max 250.000)");
            weight = input.nextDouble();
            input.nextLine();
            if(weight <= 0 || weight > 250.000){
                throw new FieldRangeOutOfBoundsException("The maximum weight of a Cargo Ship cannot be less or equal to 0 or surpass 250.000 Tons");
            }

            System.out.println("Indicate de max velocity of the ship (Km/h");
            maxVelocity = input.nextDouble();
            input.nextLine();
            if(maxVelocity <= 0 ||  maxVelocity > 50){
                throw new FieldRangeOutOfBoundsException("The maximum velocity of a Cargo Ship cannot be less or equal to 0 or surpass 50Km/h ");
            }

            System.out.println("Indicate the maximum distance of the ship (Km)");
            maxDistance = input.nextDouble();
            input.nextLine();
            if(maxDistance <= 0 || maxDistance > 30000){
                throw new FieldRangeOutOfBoundsException("The maximum distance of a Cargo Ship cannot be less or equal to 0 or surpass 30.000Km");

            }

            System.out.println("Indicate the maximum crew capacity of the ship");
            maxCrew = input.nextInt();
            input.nextLine();
            if(maxCrew <= 0 || maxCrew > 40) {
                throw new FieldRangeOutOfBoundsException("The maximum crew of a Cargo Ship cannot be less or equal to 0 or surpass 40 crew members");
            }

            System.out.println("Indicate the maximum cargo of the ship (Tons)");
            maxCargo = input.nextDouble();
            input.nextLine();
            if(maxCargo <= 0 || maxCargo > 500_000){
                throw new FieldRangeOutOfBoundsException("The maximum Cargo of a Cargo Ship cannot be less or equal to 0 or surpass 500.000 Tons");
            }


            boolean cont = true;
            while (cont) {
                System.out.println("Indicate the engine type of the ship");
                System.out.println("1. Diesel | 2. GNL | 3. Electric");
                int option = input.nextInt();
                input.nextLine();
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
        }
        catch(InputMismatchException e) {
            System.err.println("The field only accepts numbers " + e.getMessage());
            return;
        }
        catch(FieldRangeOutOfBoundsException e) {
            System.err.println("Field out of bounds: " +  e.getMessage());
            return;
        }
        catch(Exception e){
            System.err.println("General Error: " + e.getMessage());
            return;
        }

        CargoShip newCargoShip = new CargoShip(id, name, weight, maxVelocity, maxDistance, maxCrew, maxCargo, engine);
        boats.add(newCargoShip);
        System.out.println("Cargo Ship with ID: " + newCargoShip.getId() + " Named: " + newCargoShip.getName() + " added to the fleet!");
    }

    public void load(double cargo){

        if(cargo > maxCargo || cargo + this.currentCargo > maxCargo){
            System.out.println("Not enough cargo capacity");
        }
        else{
            this.currentCargo += cargo;
            System.out.println("Loaded " + cargo + " tons of cargo");
            System.out.println("New cargo Status: " + this.currentCargo + "/" + this.maxCargo);
        }
    }

    @Override
    public void unload(){
        this.currentCargo = 0.0;
    }

    @Override
    public void setSail(double distance){

        if(!canSail(distance)){
            return;
        }

        if(this.currentCargo < this.maxCargo){
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
