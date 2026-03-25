package boat;

import exceptions.FieldRangeOutOfBoundsException;
import utils.Engine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que identifica un velero
 */
public class SailingBoat extends Boat{

    private static final long serialVersionUID = 1L;
    int sails = 0;
    int maxPassengers = 0;
    int currentPassengers = 0;

    /**
     * Constructor que crea nuevos veleros
     * @param id identifiador unico de barco
     * @param name nombre del barco
     * @param weight peso del barco
     * @param maxVelocity velocidad maxima del barco
     * @param maxDistance distancia maxima que el barco puede recorrer
     * @param maxCrew tripulación maxima que el barco puede tener al mismo tiempo
     * @param sails numero de velas que usael velero
     * @param maxPassengers numero maximo de pasajeros que se puede transportar
     */
    public SailingBoat(int id, String name, double weight, double maxVelocity, double maxDistance, int maxCrew, int sails, int maxPassengers) {
        super(id, name, weight, maxVelocity, maxDistance, maxCrew);
        this.sails = sails;
        this.maxPassengers = maxPassengers;
    }

    /**
     * Metodo que crea nuevos veleros
     * @param boats lista con todos los barcos del sistema
     */
    public static void createSailingBoat(ArrayList<Boat> boats){

        Scanner input = new Scanner(System.in);

        String name = "";
        double weight = 0.0;
        double maxVelocity =  0.0;
        double maxDistance  =  0.0;
        int maxCrew = 0;
        double maxCargo = 0.0;
        Engine engine = null;
        int maxPassengers = 0;
        int sails = 0;

        System.out.println("Creating Sailing Boat");

        //recorremos la lista de barcos buscando el id mas alto para no repetirlo
        int id = 0;
        for (Boat b : boats) {
            if(!boats.isEmpty()){
                if(b.getId() > id) {
                    id = b.getId();
                }
            }
        }
        id++;

        //solicitamos al usuario los datos del barco
        try {
            System.out.println("Indicate de name of the ship");
            name = input.nextLine();
            if(name == null || name.trim().isEmpty()){
                name = "SailingBoat" + id;
            }

            System.out.println("Indicate de weight of the ship (Tons - Max 45");
            weight = input.nextDouble();
            input.nextLine();
            if(weight <= 0 || weight > 45) {
                throw new FieldRangeOutOfBoundsException("The maximum Weight of a Sailing Boat cannot be less or equal to 0 or surpass 45 Tons");
            }

            System.out.println("Indicate de max velocity of the ship (Km/h - Max 120");
            maxVelocity = input.nextInt();
            input.nextLine();
            if(maxVelocity <= 0 || maxVelocity > 120) {
                throw new FieldRangeOutOfBoundsException("The maximum Velocity of a Sailing Boat cannot be less or equal to 0 or surpass 120Km/h");
            }

            System.out.println("Indicate the maximum distance of the ship (Km - Max 6000");
            maxDistance = input.nextInt();
            input.nextLine();
            if(maxDistance <= 0 || maxDistance > 6000) {
                throw new FieldRangeOutOfBoundsException("The maximum Distance of a Sailing Boat cannot be less or equal to 0 or surpass 6000Km");

            }

            System.out.println("Indicate the maximum crew of the ship (Max 10 Crew members");
            maxCrew = input.nextInt();
            input.nextLine();
            if(maxCrew <= 0 ||  maxCrew > 10) {
                throw new FieldRangeOutOfBoundsException("The maximum Crew of a Sailing Boat cannot be less or equal to 0 or surpass 10 Crew members");
            }

            System.out.println("Indicate the amount of sails of the ship (Max 35");
            sails = input.nextInt();
            input.nextLine();
            if(sails <= 0 || sails > 35) {
                throw new FieldRangeOutOfBoundsException("The maximum amount of Sails of a Sail Boat cannot be less or equal to 0 or surpass 35 Units");
            }

            System.out.println("Indicate the maximum passengers of the ship (Max 12 Passengers");
            maxPassengers = input.nextInt();
            input.nextLine();
            if(maxPassengers <= 0 || maxPassengers > 12) {
                throw new FieldRangeOutOfBoundsException("The maximum Passengers of a Sailing Boat cannot be less or equal to 0 or surpass 12 Passengers");
            }

        }
        //capturamos las excepciones lanzadas
        catch(InputMismatchException e){
            System.err.println("The field only accepts numbers " + e.getMessage());
            return;
        }
        catch(FieldRangeOutOfBoundsException e){
            System.err.println("Field out of Bounds: " + e.getMessage());
            return;
        }
        catch(Exception e){
            System.err.println("General Error: " + e.getMessage());
            return;
        }

        //creamos el nuevo velero lo añadimos a la lista y mostramos por pantalla su id y nombre
        SailingBoat newSailing = new SailingBoat(id, name, weight, maxVelocity, maxDistance, maxCrew, sails, maxPassengers);
        boats.add(newSailing);
        System.out.println("Sailing Boat with ID: " + newSailing.getId() + " Named: " + newSailing.getName() + " added to the fleet!");
    }

    /**
     * Metodo que permite la carga de pasajeros al velero
     * @param passengers nuemor de pasajeros que queremos cargar al barco
     */
    public void load(int passengers){

        if(passengers > 0) {
            if (passengers > this.maxPassengers || passengers + this.currentPassengers > this.maxPassengers) {
                System.out.println("Not enough seats");
            } else {
                this.currentPassengers += passengers;
                System.out.println("Operation successfully. Loaded " + passengers + " passengers");
                System.out.println("New seat status: " + this.currentPassengers + "/" + this.maxPassengers);
            }
        }
        else{
            System.out.println("Invalid option");
        }
    }

    /**
     * Metodo que permite la descarga total de pasajeros del barco
     */
    @Override
    public void unload(){
        this.currentPassengers = 0;
    }

    /**
     * Metodo que permite al barco zarpar de puerto
     * @param distance distancia que queremos que se desplaze el barco
     */
    @Override
    public void setSail(double distance){

        //Si no puede zarpar cancelamos la operación
        if(!canSail(distance)){
            return;
        }

        /*De poder zarpar confirmamos la operación por pantalla, calculamos el tiempo que tardará en llegar al destino
        y modificamos las variables necesarias*/
        System.out.println("Setting course");
        long minutes = (long) (distance / (this.maxVelocity / 60));
        this.sailDate = LocalDateTime.now();
        this.lastChecked = this.sailDate;
        this.dateOfArrival = this.sailDate.plusMinutes(minutes);
        this.currentDistanceLeft = distance;
    }

    /**
     * Metodo auxiliar que identifica si un barco puede o no zarpar
     * @param distance distancia que queremos dedsplazar al barco
     * @return booleanto true si puede zarpar o false si no puede
     */
    public boolean canSail(double distance){

        //Si la distancia proporcionada es mayor a la distancia maxima no podrá zarpar
        if(distance > this.maxDistance){
            System.out.println("Sail out of bounds!");
            return false;
        }
        //Si no tiene capitán asignado no podrá zarpar
        if(this.captain == null){
            System.out.println("The captain has not been set!");
            return false;
        }
        //Si no tiene Asistente de flota no podrá zarpar
        if(this.assignedFM == null){
            System.out.println("The Fleet Manager has not been set!");
            return false;
        }
        //Si la tripulación actual es menor a la mitad de la tripulación maxima no pdorá zarpar
        if(this.crew.size() < (this.maxCrew / 2)){
            System.out.println("Not enough crew!");
            return false;
        }
        //Si los pasajeros actuales son menores a los pasajeros maximos posibles requerira de confirmación por parte
        // del usuario para poder zarpar
        if(this.currentPassengers < this.maxPassengers){
            if(!confirmDeparture()){
                System.out.println("Operation cancelled");
                return true;
            }
        }
        return true;
    }

    /**
     * Metodo auxiliar que pregunta al usuario si desea zarpar pese a las condiciones mostradas
     * @return booleano true si puede zarpar, false si no puede
     */
    public boolean confirmDeparture(){

        //Mostramos la situación de pasajeros por pantalla
        Scanner input = new Scanner(System.in);
        System.out.println("Warning! The ship is not full of passengers (" + this.currentPassengers + "/" + this.maxPassengers + ")");
        //solicitamos confirmación al suaurio
        System.out.println("Are you sure do you want to set sail? (Y/N)");
        String option = input.nextLine();
        if(option.equalsIgnoreCase("y") || option.equalsIgnoreCase("yes")){
            return true;
        }
        else{
            return false;
        }
    }

    //Getters y Setters

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
