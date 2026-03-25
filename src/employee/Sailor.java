package employee;

import boat.Boat;
import exceptions.EmptyFieldException;
import exceptions.NegativeNumberException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que identifica un Marinero
 */
public class Sailor extends Employee {

    private static final long serialVersionUID = 1L;
    int trips = 0;
    double bonus = 0;
    double monthDistance = 0;
    Boat assignedBoat = null;
    int nextTripsForRise = 150;
    int lastUpdatedYear = 0;

    /**
     * Constructor de la clase marinero
     * @param id identificador unico por empleado
     * @param name nombre del marinero
     * @param surnames apellidos del marinero
     * @param contractStartDate fecha de inicio de contrato con la compañia
     * @param salary salario del marinero
     * @param experience experiencia del marinero
     */
    public Sailor(int id, String name, String surnames, LocalDate contractStartDate, double salary, int experience) {
        super(id, name, surnames, contractStartDate, salary, experience);
    }

    /**
     * Metodo que permite crear nuevos marineros y añadirlos al sistema
     * @param employees lista con todos los empleados registrados en el sistema
     */
    public static void createSailor(ArrayList<Employee> employees) {

        Scanner input = new Scanner(System.in);

        String name = "";
        String surnames = "";
        LocalDate contractStartDate = null;
        double salary = 0.0;
        int experience = 0;

        //recorremos la lista de empleados buscando el id mas alto para no repetir id
        System.out.println("Adding a new employee");
        int id = 0;
        if(!employees.isEmpty()) {
            for (Employee e : employees) {
                if(e.id > id) {
                    id = e.id;
                }
            }
        }
        id++;

        //preguntamos al usuario los datos del nuevo marinero
        try {
            System.out.println("Indicate the name of the sailor");
            name = input.nextLine();
            if (name == null || name.trim().isEmpty()) {
                throw new EmptyFieldException("Fleet Manager name cannot be empty");
            }

            System.out.println("Indicate the surname of the sailor");
            surnames = input.nextLine();
            if (surnames == null || surnames.trim().isEmpty()) {
                throw new EmptyFieldException("Fleet Manager surnames cannot be empty");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.println("Indicate the Contract Initialization Date (DD/MM/YYYY - Enter for today)");
            String contractDate = input.nextLine();
            if (contractDate == null || contractDate.trim().isEmpty()) {
                contractStartDate = LocalDate.now();
            } else {
                contractStartDate = LocalDate.parse(contractDate, formatter);
            }

            salary = 1500.00;

            System.out.println("Indicate the experience of the sailor");
            experience = input.nextInt();
            input.nextLine();
            if(experience < 0){
                throw new NegativeNumberException("Experience cannot be negative");
            }
        }
        //capturamos las excepciones lanzadas
        catch(DateTimeParseException e){
            System.err.println("Invalid Time Format " + e.getMessage());
            return;
        }
        catch(NegativeNumberException e){
            System.err.println("Invalid Number " + e.getMessage());
            return;
        }
        catch(InputMismatchException e){
            System.err.println("Field only accepts Numbers " + e.getMessage());
            return;
        }
        catch(Exception e){
            System.err.println("General Error " + e.getMessage());
            return;
        }

        /*Creamos al nuevo marinero, le añadimos fecha de actualización para las subidas salariales, lo añadimos
        al listado de empleados y mostramos su id y nombre por pantalla*/
        Sailor newSailor = new Sailor(id, name, surnames, contractStartDate, salary, experience);
        newSailor.lastUpdatedYear = contractStartDate.getYear();
        employees.add(newSailor);
        System.out.println("New Sailor with ID: " + newSailor.getId() + " Named: " + newSailor.getName() + " " + newSailor.getSurnames() + " created Successfully");
    }

    /**
     * Metodo que permite calcula el bono del marinero en base a la distancia recorrida ese mes
     */
    public void calculateBonus(){

        this.bonus = this.monthDistance / 100;
    }

    /**
     * Metodo que actualiza el sueldo del marinero en base a varias reglas
     */
    @Override
    public void updateBaseSalary() {

        LocalDate today = LocalDate.now();

        //si ha superado el limte de viajes, se aumenta el salario y se actualiza el limite
        if(this.trips >= nextTripsForRise){
            this.salary += 100;
            this.nextTripsForRise += 150;
        }

        //si cumple otro año en la compalia se le actualiza el salario nuevamente
        if(today.getMonth() == this.contractStartDate.getMonth()){
            if(today.getYear() > this.lastUpdatedYear){
                this.salary += 25;
                this.lastUpdatedYear = today.getYear();
            }
        }
    }

    //Getters y Setters

    public double getDistance(){
        return monthDistance;
    }

    public void setDistance(double distance){
        this.monthDistance += distance;
    }

    public double getBonus(){
        this.calculateBonus();
        return bonus;
    }
    public double monthDistance(){
        return monthDistance;
    }
    public double getMonthDistance(){
        return monthDistance;
    }
    public void setMonthDistance(double monthDistance){
        this.monthDistance = monthDistance;
    }
    public Boat getAssignedBoat() {
        return assignedBoat;
    }
    public void setAssignedBoat(Boat assignedBoat) {
        this.assignedBoat = assignedBoat;
    }
    public int getTrips() {
        return trips;
    }
    public void setTrips(int trips) {
        this.trips = trips;
    }
}
