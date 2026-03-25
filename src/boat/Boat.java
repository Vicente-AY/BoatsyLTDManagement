package boat;

import employee.Captain;
import employee.FleetManager;
import employee.Sailor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Clase abstracta que representa un Barco
 */
public abstract class Boat implements Serializable {

    private static final long serialVersionUID = 1L;

    int id = 0;
    String name = "";
    double weight = 0.0;
    public double maxVelocity = 0;
    double maxDistance = 0;
    public double currentDistanceLeft = 0;
    int maxCrew = 0;
    double hoursUntilArrival = 0;
    ArrayList<Sailor> crew = new ArrayList<Sailor>();
    FleetManager assignedFM = null;
    Captain captain = null;
    LocalDateTime sailDate = null;
    LocalDateTime dateOfArrival = null;
    public LocalDateTime lastChecked = null;

    /**
     * Constructor de la clase
     * @param id identificador unico para cada barco
     * @param name nombre de la nave
     * @param weight peso del barco
     * @param maxVelocity velocidad maxima del barco
     * @param maxDistance distancia maxima que el barco puede recorrer
     * @param maxCrew tripulación maxima que el barco puede tener a la vez
     */
    public Boat(int id, String name, double weight, double maxVelocity, double maxDistance, int maxCrew) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.maxVelocity = maxVelocity;
        this.maxDistance = maxDistance;
        this.maxCrew = maxCrew;
    }

    /**
     * Metodo abstracto que hace que un barco zarpe
     * @param distance distancia que queremos que se desplaze el barco
     */
    public abstract void setSail(double distance);

    /**
     * Metodo abstracto que descarga un barco
     */
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
    public LocalDateTime getSailDate(){
        return sailDate;
    }
    public LocalDateTime getDateOfArrival(){
        return dateOfArrival;
    }
}
