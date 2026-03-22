package boat;

import utils.Engine;

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

        if(cargo > maxCargo){
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
}
