package menu;

import boat.Boat;
import boat.CargoShip;
import boat.MotorBoat;
import boat.SailingBoat;
import employee.*;
import utils.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que sirve de menú principal de la aplicación
 */
public class Menu {

    Data dataAccess = new Data();

    /**
     * metodo que carga los datos, comprueba el dia para el cobro del personal y muestra el menú principal
     */
    public void displayMenu() {

        //cargamos los datos
        ArrayList<Boat> boats = dataAccess.chargeBoats();
        ArrayList<Employee> employees = dataAccess.chargeEmployees();

        //actualizamos el estado de los barcos en viaje
        FleetManagement.boatTripStatusUpdate(boats);

        //cargamos la ultima vez que se ejecutó el mes de pago y si es dia 1 ejecutamos el metodo calculateSalary
        String lastChecked = dataAccess.chargeLastExecution();
        LocalDate today = LocalDate.now();
        String todayString = today.toString();
        int day = today.getDayOfMonth();
        if(day == 1 && !todayString.equals(lastChecked)){
            calculateSalary(todayString, employees);
        }



        Scanner input = new Scanner(System.in);

        //mostramos el menú
        int option = 0;
        boolean cont = true;
        while(cont){
            System.out.println("Welcome to Boatsy LTD Management");
            System.out.println("Type your option");
            System.out.println("1. Manage Personal | 2. Manage Fleet | 3. Close application");
            try {
                option = input.nextInt();
                input.nextLine();
            }
            catch(InputMismatchException e){
                System.err.println("Please enter a valid Number");
                continue;
            }
            switch(option){
                case 1:
                    EmployeeManagement employeeManagement = new EmployeeManagement();
                    employeeManagement.personalManagementMenu(employees,  boats);
                    break;
                case 2:
                    FleetManagement fleetManagement = new FleetManagement();
                    fleetManagement.fleetManagementMenu(boats, employees);
                    break;
                case 3:
                    //al salir de la aplicación guardamos los cambios
                    dataAccess.saveBoats(boats);
                    dataAccess.saveEmployees(employees);
                    System.out.println("Have a nice day!");
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    /**
     * Clase que recorre toda la lista de empleados ejecutando su metodo calculateSalary
     * @param todayString string con la fecha de hoy que si se ejecuta es dia 1
     * @param employees lista con todos los empleados registrados del sitema
     */
    public void calculateSalary(String todayString, ArrayList<Employee> employees){

        for(Employee employee : employees){
            employee.updateBaseSalary();
        }
        dataAccess.saveLastExecution(todayString);
    }

}
