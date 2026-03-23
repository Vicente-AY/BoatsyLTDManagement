package boat;

import utils.Engine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class SailingBoat extends Boat{

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

    @Override
    public void setSail(double distance){

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
