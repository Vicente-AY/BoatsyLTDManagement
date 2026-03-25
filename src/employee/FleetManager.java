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
 * Clase que identifica a un asistente de flota
 */
public class FleetManager extends Employee {

    private static final long serialVersionUID = 1L;
    ArrayList<Boat> managedBoats = new ArrayList<Boat>();
    int lastUpdatedYear = 0;

    /**
     * Constructor de la clase asistente de flota
     * @param id identificador unico por cada empleado
     * @param name nombre del empleado
     * @param surnames apellidos del empleado
     * @param contractStartDate fecha de inicio de contrato con la compalia
     * @param salary salario del asistente de flota
     * @param experience años de experiencia del asistente de flota
     */
    public FleetManager(int id, String name, String surnames, LocalDate contractStartDate, double salary, int experience){
        super(id, name, surnames, contractStartDate, salary, experience);

    }

    /**
     * Metodo que permite la creación de nuevos asistentes de flota
     * @param employees lista con todos los empleados registrados del sistema
     */
    public static void createFleetManager(ArrayList<Employee> employees){

        Scanner input = new Scanner(System.in);

        String name = "";
        String surnames = "";
        LocalDate contractStartDate = null;
        double salary = 0.0;
        int experience = 0;

        //recorremos la lista en busca de el id mas alto para no repetir id
        int id = 0;
        if(!employees.isEmpty()) {
            for (Employee e : employees) {
                if(e.id > id) {
                    id = e.id;
                }
            }
        }
        id++;

        //solicitamos al usuario la información del asistente de flota
        try {
            System.out.println("Indicate the name of the Fleet Manager");
            name = input.nextLine();
            if (name == null || name.trim().isEmpty()) {
                throw new EmptyFieldException("Fleet Manager name cannot be empty");
            }

            System.out.println("Indicate the surname of the Manager");
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

            salary = 12000.00;

            System.out.println("Indicate the experience of the Manager");
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

        /*creamos al nuevo asistente de flota, le añadimos la fecha de actualización para las subidas salariales
        lo añadimos a la lista de empleados y mostramos su id y nombre por pantalla*/
        FleetManager newFM = new FleetManager(id, name, surnames, contractStartDate, salary, experience);
        newFM.lastUpdatedYear = contractStartDate.getYear();
        employees.add(newFM);
        System.out.println("New Fleet Manager with ID: " + newFM.getId() + " named: " + newFM.getName() + " " + newFM.getSurnames() + " created Successfully");
    }

    /**
     * Metodo que actualiza el salario en base a su tiempo en la compañia
     */
    @Override
    public void updateBaseSalary() {

        LocalDate today = LocalDate.now();

        //cuando el empleado lleve un año le ampliamos su salario
        if(today.getMonth() == this.contractStartDate.getMonth()){
            if(today.getYear() > this.lastUpdatedYear){
                this.salary += 250;
                this.lastUpdatedYear = today.getYear();
            }
        }
    }

    //Getters and Setters

    public ArrayList<Boat> getManagedBoats() {
        return managedBoats;
    }

}
