package boat;

import utils.Engine;

import java.util.ArrayList;
import java.util.Scanner;

public class MotorBoat extends Boat{

    Engine engine = null;
    int currentPassengers = 0;
    int maxPassengers = 0;

    public MotorBoat(int id, String name, double weight, int maxVelocity, int maxDistance, int maxCrew, Engine engine, int maxPassengers) {

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
        int maxVelocity = input.nextInt();
        input.nextLine();


        System.out.println("Indicate the maximum distance of the ship");
        int maxDistance = input.nextInt();
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

    @Override
    public void setSail(double distance){

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
