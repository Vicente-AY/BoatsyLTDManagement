package boat;

import employee.Captain;
import employee.Employee;
import employee.FleetManager;
import employee.Sailor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public abstract class Boat {

    int id = 0;
    String name = "";
    double weight = 0.0;
    int maxVelocity = 0;
    int maxDistance = 0;
    double currentDistanceLeft = 0;
    int maxCrew = 0;
    double daysUntilArrival = 0;
    ArrayList<Sailor> crew = new ArrayList<Sailor>();
    FleetManager assignedFM = null;
    Captain captain = null;
    LocalDate sailDate = null;
    LocalDate dateOfArrival = null;
    LocalDate lastChecked = null;


    public Boat(int id, String name, double weight, int maxVelocity, int maxDistance, int maxCrew) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.maxVelocity = maxVelocity;
        this.maxDistance = maxDistance;
        this.maxCrew = maxCrew;
    }

    public void setSail(double distance){

        if(distance > this.maxDistance){
            System.out.println("Sail out of bounds!");
        }
        else if(this.captain == null){
            System.out.println("The captain has not been set!");
        }
        else if(this.crew.size() < (this.maxCrew / 2)){
            System.out.println("Not enough crew!");
        }
        else{
            System.out.println("Setting course");
            int hours = (int) (distance / this.maxVelocity);
            int days = hours / 24;
            this.daysUntilArrival = days;
            System.out.println(days  + " Days until arrival");
            this.sailDate = LocalDate.now();
            this.lastChecked = this.sailDate;
            this.dateOfArrival = this.sailDate.plusDays(days);
            this.currentDistanceLeft = distance;
        }
    }

    public void newArrivalDate(Boat boat){

        LocalDate currentDate = LocalDate.now();
        long diffHours = ChronoUnit.HOURS.between(this.lastChecked, currentDate);
        if(diffHours > 0) {
            long coveredDistance = diffHours * this.maxVelocity;
            this.currentDistanceLeft -= coveredDistance;
            this.lastChecked = currentDate;
        }
    }

    //Getters and Setters
    public int getId() {
        return id;
    }
    public double getWeight() {
        return weight;
    }
    public int getMaxVelocity() {
        return maxVelocity;
    }
    public int getMaxDistance() {
        return maxDistance;
    }
    public int getMaxCrew() {
        return maxCrew;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public void setMaxVelocity(int maxVelocity) {
        this.maxVelocity = maxVelocity;
    }
    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }
    public void setMaxCrew(int maxCrew) {
        this.maxCrew = maxCrew;
    }
    public ArrayList<Sailor> getCrew() {
        return crew;
    }
    public Captain getCaptain() {
        return captain;
    }
    public void setCaptain(Captain captain) {
        this.captain = captain;
    }
    public FleetManager getAssignedFM() {
        return assignedFM;
    }
    public void setAssignedFM(FleetManager assignedFM) {
        this.assignedFM = assignedFM;
    }
    public String getName(){
        return name;
    }
}
