package employee;

import boat.Boat;
import exceptions.EmptyFieldException;
import exceptions.NegativeNumberException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que identifica a un Capitán de barco
 */
public class Captain extends Employee{

    private static final long serialVersionUID = 1L;
    int trips = 0;
    double bonus = 0;
    double monthDistance = 0;
    Boat assignedBoat = null;
    int nextTripsForRise = 100;
    int lastUpdatedYear = 0;

    /**
     * Constructor de la Clase Capitán
     * @param id identificador unico por empleado
     * @param name nombre del capitán
     * @param surnames apellidos del capitán
     * @param contractStartDate fecha de contrato del capitán
     * @param salary salario del capitán
     * @param experience años de experiencia del capitán
     */
    public Captain(int id, String name, String surnames, LocalDate contractStartDate, double salary, int experience){
        super(id, name, surnames, contractStartDate, salary, experience);
    }

    /**
     * Metodo que permite la cración de nuevos capitanes de barco
     * @param employees lista con todos los empleados registrados en el sistema
     */
    public static void createCaptain(ArrayList<Employee> employees){

        String name = "";
        String surnames = "";
        LocalDate contractStartDate = null;
        double salary = 0.0;
        int experience = 0;

        Scanner input = new Scanner(System.in);

        //recorremos la lsita entera en busca del id mas alto para no repetirlo
        int id = 0;
        if(!employees.isEmpty()) {
            for (Employee e : employees) {
                if(e.id > id) {
                    id = e.id;
                }
            }
        }
        id++;

        //preguntamos al usuario los datos del capitán
        try {
            System.out.println("Indicate the name of the Captain");
            name = input.nextLine();
            if (name == null || name.trim().isEmpty()) {
                throw new EmptyFieldException("Captain name cannot be empty");
            }

            System.out.println("Indicate the surname of the Captain");
            surnames = input.nextLine();
            if (surnames == null || surnames.trim().isEmpty()) {
                throw new EmptyFieldException("Captain surname is empty");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.println("Indicate the Contract Initialization Date (DD/MM/YYYY - Enter for today)");
            String contractDate = input.nextLine();
            if (contractDate == null || contractDate.trim().isEmpty()) {
                contractStartDate = LocalDate.now();
            } else {
                contractStartDate = LocalDate.parse(contractDate, formatter);
            }



            salary = 3500.00;

            System.out.println("Indicate the experience of the Captain");
            experience = input.nextInt();
            input.nextLine();
            if(experience < 0){
                throw new NegativeNumberException("Experience cannot be negative");
            }
        }
        //recogemos las excepciones lanzadas
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

        /*creamos al nuevo captián, le añadimos el año de ultimo actualización para la subida salarial
        lo añadimos a la lista de empleado y mostramos su id y nombre por pantalla*/
        Captain newCaptain = new Captain(id, name, surnames, contractStartDate, salary, experience);
        newCaptain.lastUpdatedYear = contractStartDate.getYear();
        employees.add(newCaptain);
        System.out.println("New Captain with ID: " + newCaptain.getId() + " named: " + newCaptain.getName() + " " + newCaptain.getSurnames() + " created Successfully");
    }

    /**
     * Metodo qeu calcula el bono mensual a partir de la distancia mensual recorrida por el Capitán
     */
    public void calculateSalaryBonus(){

        this.bonus = monthDistance / 50;
    }

    /**
     * Metodo que permite aumentar el salario de un Capitán en relación con distintos baremos
     */
    @Override
    public void updateBaseSalary() {

        LocalDate today = LocalDate.now();

        //por cada 100 viajes se aumenta el sueldo y se aumenta el siguiente marca a superar antes de la nueva subida salarial
        if(this.trips >= nextTripsForRise){
            this.salary += 150;
            this.nextTripsForRise += 100;
        }

        //aumentamos el salario si ha cumplido un nuevo año en la compalia
        if(today.getMonth() == this.contractStartDate.getMonth()){
            if(today.getYear() > this.lastUpdatedYear){
                this.salary += 75;
                this.lastUpdatedYear = today.getYear();
            }
        }
    }


    //Getters y Setters

    public Boat getAssignedBoat(){
        return assignedBoat;
    }
    public void setAssignedBoat(Boat assignedBoat){
        this.assignedBoat = assignedBoat;
    }
    public double getBonus(){
        return bonus;
    }
    public double getMonthDistance(){
        return monthDistance;
    }
    public void setMonthDistance(double monthDistance){
        this.monthDistance = monthDistance;
    }
    public int getTrips(){
        return this.trips;
    }
    public void setTrips(int trips){
        this.trips = trips;
    }
}
