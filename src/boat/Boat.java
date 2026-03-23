package boat;

import employee.Captain;
import employee.FleetManager;
import employee.Sailor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public abstract class Boat implements Serializable {

    private static final long serialVersionUID = 1L;
    int id = 0;
    String name = "";
    double weight = 0.0;
    double maxVelocity = 0;
    double maxDistance = 0;
    double currentDistanceLeft = 0;
    int maxCrew = 0;
    double hoursUntilArrival = 0;
    ArrayList<Sailor> crew = new ArrayList<Sailor>();
    FleetManager assignedFM = null;
    Captain captain = null;
    LocalDateTime sailDate = null;
    LocalDateTime dateOfArrival = null;
    LocalDateTime lastChecked = null;


    public Boat(int id, String name, double weight, int maxVelocity, int maxDistance, int maxCrew) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.maxVelocity = maxVelocity;
        this.maxDistance = maxDistance;
        this.maxCrew = maxCrew;
    }

    public abstract void setSail(double distance);

    public void boatTripStatusUpdate(ArrayList<Boat> boats){

        LocalDateTime currentDate = LocalDateTime.now();
        double potentialCoveredDistance = 0;

        for(Boat boat : boats){
            double currentDist = boat.getCurrentDistanceLeft();
            double actualCovered = 0;

            if(currentDist > 0){
                double diffMinutes = ChronoUnit.MINUTES.between(this.lastChecked, currentDate);
                if(diffMinutes > 0) {
                    potentialCoveredDistance = (diffMinutes * 60) * this.maxVelocity;

                    actualCovered = Math.min(potentialCoveredDistance, currentDist);

                    this.currentDistanceLeft = currentDist - actualCovered;
                    this.lastChecked = currentDate;

                    if(this.currentDistanceLeft <= 0){
                        this.currentDistanceLeft = 0;
                        boat.unload();
                        boat.getCaptain().setTrips(boat.getCaptain().getTrips() + 1);
                        for(Sailor sailor : boat.getCrew()){
                            sailor.setTrips(sailor.getTrips() + 1);
                        }
                    }
                }
            }
            if(actualCovered > 0) {
                boat.getCaptain().setMonthDistance(boat.getCaptain().getMonthDistance() + actualCovered);
                for (Sailor sailor : boat.getCrew()) {
                    sailor.setMonthDistance(sailor.getMonthDistance() + actualCovered);
                }
            }
        }
    }

    public abstract void unload();

    //Getters and Setters
    public int getId() {
        return id;
    }
    public double getWeight() {
        return weight;
    }
    public double getMaxVelocity() {
        return maxVelocity;
    }
    public double getMaxDistance() {
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
    public double getCurrentDistanceLeft(){
        return currentDistanceLeft;
    }
    public void setCurrentDistanceLeft(double distance){
        this.currentDistanceLeft = distance;
    }
}
