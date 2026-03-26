package menu;

import boat.Boat;
import employee.Captain;
import employee.Employee;
import employee.FleetManager;
import employee.Sailor;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que permite el manejo de personal a través de varios metodos
 */
public class EmployeeManagement {

    Scanner input = new Scanner(System.in);

    /**
     * Metodo que sirve de menu para la clase, permitiendo al usuario navegar entre sus diferentes metodos
     * @param employees lista con todos los usuarios registrados del sistema
     * @param boats lista con todos los barcos del sistema
     */
    public void personalManagementMenu(ArrayList<Employee> employees, ArrayList<Boat> boats) {
        int option = 0;
        while(true) {
            System.out.println("- - - Employee Management Menu - - -");
            System.out.println("1. Add Employee | 2. Remove Employee | 3. Assign Employee to a boat");
            System.out.println("4. Unassign Employee from a Ship | 5. Employee List | 6. Employee Info");
            System.out.println("7. Back");
            try {
                option = input.nextInt();
                input.nextLine();
            }
            catch(InputMismatchException e) {
                System.err.println("Please enter a valid Number");
                input.nextLine();
                option = 0;
                continue;
            }
            switch(option) {
                case 1:
                    addEmployee(employees);
                    break;
                case 2:
                    removeEmployee(employees);
                    break;
                case 3:
                    assignBoat(employees, boats);
                    break;
                case 4:
                    unassignBoat(employees);
                    break;
                case 5:
                    employeeList(employees);
                    break;
                case 6:
                    employeeInfo(employees);
                    break;
                case 7:
                    System.out.println("Back to Main Menu");
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    /**
     * Metodo que permite añadir nuevos empleados al sistema
     * @param employee lista con todos los empleados del sistema
     */
    public void addEmployee(ArrayList<Employee> employee){

        int option = 0;
        while(true) {
            System.out.println("Select the type of employee you want to add");
            System.out.println("1. Add Sailor | 2. Add Captain | 3. Add Fleet Manager");
            System.out.println("4. Back");
            try {
                option = input.nextInt();
                input.nextLine();
            }
            catch(InputMismatchException e) {
                System.err.println("Please enter a valid Number");
                input.nextLine();
                option = 0;
                continue;
            }
            switch(option) {
                case 1:
                    Sailor.createSailor(employee);
                    break;
                case 2:
                    Captain.createCaptain(employee);
                    break;
                case 3:
                    FleetManager.createFleetManager(employee);
                    break;
                case 4:
                    System.out.println("Back to the Manage Employee Menu");
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    /**
     * Metodo que permite la eliminación de un empleado del sistema
     * @param employees lista con todos los empleados registrados
     */
    public void removeEmployee(ArrayList<Employee> employees){

        Employee employeeToRemove = null;

        //preguntamos el id del usuario a borrar
        int id = 0;
        System.out.println("Type the ID of the Employee you want to remove");
        try {
            id = input.nextInt();
            input.nextLine();
        }
        catch(InputMismatchException e) {
            System.err.println("Please enter a valid ID Number");
            return;
        }

        //buscamos el id del empleado
        for(Employee e: employees){
            if(e.getId() == id){
                employeeToRemove = e;
            }
        }
        //de encontrarlo preguntamos al usuario si realmente desea borrar al usuario mostrando por pantalla id y nombre
        if(employeeToRemove != null) {
            if (employeeToRemove instanceof Sailor) {
                System.out.println("Are you sure you want to remove Sailor " + employeeToRemove.getName() + " " + employeeToRemove.getSurnames() + "?");
            }
            if (employeeToRemove instanceof Captain) {
                System.out.println("Are you sure you want to remove Captain " + employeeToRemove.getName() + " " + employeeToRemove.getSurnames() + "?");
            }
            if (employeeToRemove instanceof FleetManager) {
                System.out.println("Are you sure you want to remove Fleet Manager " + employeeToRemove.getName() + " " + employeeToRemove.getSurnames() + "?");
            }
            System.out.println("Y / N");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                employees.remove(employeeToRemove);
                System.out.println("Employee " +  employeeToRemove.getName() + " " + employeeToRemove.getSurnames() + " removed successfully");
            } else {
                System.out.println("Cancelling operation");
            }
        }
        //si no encontramos al empleado mostramos por pantalla que no se ha encontrado
        else{
            System.out.println("ID not found");
        }
    }

    /**
     * Metodo que permite asignar un empleado a un barco
     * @param employee lista con todos los empleados del sistema
     * @param boats lista con todos los barcos del sistema
     */
    public void assignBoat(ArrayList<Employee> employee, ArrayList<Boat> boats){

        Employee employeeToAssign = null;
        Boat boatToAssign = null;

        //preguntamos el id del usuarioa a asignar
        int employeeId = 0;
        System.out.println("Type the ID of the Employee you want to Assign to a boat");
        try {
            employeeId = input.nextInt();
            input.nextLine();
        }
        catch(InputMismatchException e) {
            System.err.println("Please enter a valid Number");
            return;
        }

        //lo buscamos
        for(Employee e: employee){
            if(e.getId() == employeeId){
                employeeToAssign = e;
            }
        }
        //de no encontrarlo cancelamos la operacion
        if(employeeToAssign == null) {
            System.out.println("Employee not found");
            return;
        }

        //preguntamos el id del barco al que queremos asignar al empleado
        int boatId = 0;
        System.out.println("Type the ID of the boat you want to Assign to");
        try {
            boatId = input.nextInt();
            input.nextLine();
        }
        catch(InputMismatchException e) {
            System.err.println("Please enter a valid Number");
            return;
        }

        //lo buscamos
        for(Boat boat: boats){
            if(boat.getId() == boatId){
                boatToAssign = boat;
            }
        }
        //de no encontrarlo cancelamos la operación
        if(boatToAssign == null){
            System.out.println("Boat not found");
            return;
        }
        //si el barco encontrado está de viaje no podrá cambiar la tripulación
        if(boatToAssign.getCurrentDistanceLeft() > 0){
            System.out.println("The ship is already on trip. Wait until they arrive to a port to change its crew");
            return;
        }

        if(employeeToAssign instanceof Sailor) {
            Sailor sailor = (Sailor) employeeToAssign;
            //si el marinero ya está asignado al mismo barco lo mastramos por pantalla
            if(sailor.getAssignedBoat() == boatToAssign){
                System.out.println("The Sailor is already assigned to that boat");
                return;
            }
            //si el barco esta completamente tripulado concelamos la operación, mostrando mensaje por pantalla
            else if(boatToAssign.getCrew().size() >= boatToAssign.getMaxCrew()){
                System.out.println("The boat is fully crewed");
                return;
            }
            //si el marinero ya está asignado a un barco lo mostramos por pantalla
            else if(sailor.getAssignedBoat() != null){
                System.out.println("The Sailor is already assigned to the Boat with ID: " + sailor.getAssignedBoat().getId() + " Named: " + sailor.getAssignedBoat().getName());
                /*si el barco al que pertenece el marinero actualmente no está en un viaje permitiremos al usuario
                 decidir si desea cambiar al marinero de barco*/
                if(sailor.getAssignedBoat().getCurrentDistanceLeft() <= 0){
                    boolean change = changeBoat();
                    if(change){
                        System.out.println("Changing Ship from " + sailor.getAssignedBoat().getName() + " to " + boatToAssign.getName());
                        sailor.getAssignedBoat().getCrew().remove(sailor);
                        sailor.setAssignedBoat(boatToAssign);
                        boatToAssign.getCrew().add(sailor);
                        System.out.println("Change Completed");
                    }
                }
                else{
                    System.out.println("Wait until the Ship arrive to a port to change its crew");
                }
            }
            //situación estandar donde el marinero es asignado al barco
            else{
                sailor.setAssignedBoat(boatToAssign);
                boatToAssign.getCrew().add(sailor);
                System.out.println("Sailor " + sailor.getName() + " " + sailor.getSurnames() + " Assigned to boat ID: " + boatToAssign.getId() + " named: " + boatToAssign.getName() + " Successfully");
                return;
            }
        }
        else if(employeeToAssign instanceof Captain) {
            Captain captain = (Captain) employeeToAssign;
            //si el capitán ya está asignado al barco seleccionado lo mostramos por pantalla
            if(captain.getAssignedBoat() == boatToAssign){
                System.out.println("The Captain is already assigned to that boat");
                return;
            }
            //si el capitan tiene barco asignado o el barco seleccionado ya tiene captián entraremos en este flujo
            if(captain.getAssignedBoat() != null || boatToAssign.getCaptain() != null) {

                //Si el capitán tiene barco y este esta de viaje indicaremos al usuario que debe esperar a su llegada
                if(captain.getAssignedBoat() != null && captain.getAssignedBoat().getCurrentDistanceLeft() > 0){
                    System.out.println("Selected Captain is assigned to the Boat with ID: " + captain.getAssignedBoat().getId() + " Named: " + captain.getAssignedBoat().getName() + " which is currently on a trip");
                    System.out.println("Wait until arrival to change Captain");
                    return;
                }

                /*Si el capitán tiene barco y el barco destino tiene capitán permitiremos al usuario decidir si desea
                cambiar a los capitanes, solo asignar al capitán seleccionado al barco seleccionado o cancelar*/
                if(captain.getAssignedBoat() != null && boatToAssign.getCaptain() != null) {
                    int option = 0;
                    while(true) {

                        System.out.println("Caution! Both, selected Captain and Ship have already been assigned.");
                        System.out.println("Selected Captain is assigned to the Boat with ID: " + captain.getAssignedBoat().getId() + " Named: " + captain.getAssignedBoat().getName());
                        System.out.println("And selected Ship has Captain with ID: " + boatToAssign.getCaptain().getId() + " Named: " + boatToAssign.getCaptain().getName());
                        System.out.println("Choose your action");
                        System.out.println("1. Exchange Captains and Ships | 2. Just assign selected Captain to the selected Boat | 3. Cancel");
                        try {
                            option = input.nextInt();
                            input.nextLine();
                        }
                        catch (InputMismatchException e) {
                            System.err.println("Please enter a valid Number");
                            continue;
                        }

                        Boat boatToExchange = captain.getAssignedBoat();
                        Captain captainToExchange = boatToAssign.getCaptain();

                        switch (option) {
                            //opción de cambio total, ambos capitantes cambian de barco
                            case 1:
                                captain.setAssignedBoat(boatToAssign);
                                boatToAssign.setCaptain(captain);

                                captainToExchange.setAssignedBoat(boatToExchange);
                                boatToExchange.setCaptain(captainToExchange);
                                System.out.println("Exchange Completed");
                                return;
                            /*opción de relevo donde el barco anterior del captián se queda vacio y el captián del
                            barco destino queda sin asignar a ningún barco*/
                            case 2:
                                boatToAssign.setCaptain(captain);
                                captain.setAssignedBoat(boatToAssign);

                                boatToExchange.setCaptain(null);
                                captainToExchange.setAssignedBoat(null);
                                System.out.println("Change Completed");
                                return;
                            //Opción de cancelación de la asignación
                            case 3:
                                System.out.println("Cancelling operation");
                                return;
                            default:
                                System.out.println("Invalid option");
                                break;
                        }
                    }
                }

                //si solo el capitán tiene barco asignado mostraremos el mensaje por pantalla
                if (captain.getAssignedBoat() != null) {
                    System.out.println("The Captain is already assigned to the Boat with ID: " + captain.getAssignedBoat().getId() + " Named: " + captain.getAssignedBoat().getName());
                    //si el barco del capitán no está de viaje el usuario podrá decidir si este cambia de barco
                    if (captain.getAssignedBoat().getCurrentDistanceLeft() <= 0) {
                        boolean change = changeBoat();
                        if (change) {
                            System.out.println("Changing assigned Ship from " + captain.getAssignedBoat().getName() + " to " + boatToAssign.getName());
                            captain.getAssignedBoat().setCaptain(null);
                            captain.setAssignedBoat(boatToAssign);
                            boatToAssign.setCaptain(captain);
                            System.out.println("Change Completed");
                        }
                    }
                }
                //si es solo el barco quien tiene captán asignado le mostramos al usuario la situación por pantalla
                if (boatToAssign.getCaptain() != null) {
                    System.out.println("The Boat has already the captain " + boatToAssign.getCaptain().getName() + " " + boatToAssign.getCaptain().getSurnames() + " Assigned");
                    //el usuario podrá cambiar la situación dado que ya hemos comprobado antes que el barco esta en puerto
                    boolean change = changeBoat();
                    if(change){
                        System.out.println("Changing assigned Captain from " + boatToAssign.getCaptain().getName() + " " + boatToAssign.getCaptain().getSurnames() + " to " + captain.getName() + " " + captain.getSurnames());
                        boatToAssign.getCaptain().setAssignedBoat(null);
                        boatToAssign.setCaptain(captain);
                        captain.setAssignedBoat(boatToAssign);
                        System.out.println("Change completed");
                    }
                }
            }
            //caso base donde el capitán se asigna al barco
            else{
                captain.setAssignedBoat(boatToAssign);
                boatToAssign.setCaptain(captain);
                System.out.println("Captain " +  captain.getName() + " " + captain.getSurnames() + " Assigned to " + boatToAssign.getName() + " Successfully");
            }
        }
        else if(employeeToAssign instanceof FleetManager) {
            FleetManager fleetManager = (FleetManager) employeeToAssign;
            //si el asistente de flota tiene 5 o mas de cinco barcos no se podrá añadir otro barco
            if(fleetManager.getManagedBoats().size() >= 5){
                System.out.println("The Fleet Manager has the maximum managed boats assigned");
            }
            //mostramos por pantalla si el barco ya tiene al asistente de flota seleccionado
            else if(boatToAssign.getAssignedFM() == fleetManager){
                System.out.println("The Fleet Manager is already managing that boat");
            }
            //si el barco ya tiene asistente de flota permitiremos al usuario cambiar esta situación
            else if(boatToAssign.getAssignedFM() != null){
                System.out.println("The boat has already been assigned to the Fleet Manager " + boatToAssign.getAssignedFM().getId() + " " + boatToAssign.getAssignedFM().getName() + " " + boatToAssign.getAssignedFM().getSurnames());
                boolean change = changeBoat();
                if(change){
                    boatToAssign.getAssignedFM().getManagedBoats().remove(boatToAssign);
                    boatToAssign.setAssignedFM(fleetManager);
                    fleetManager.getManagedBoats().add(boatToAssign);
                    System.out.println("Change completed");
                }
            }
            //caso base donde el barco se añade a la lista de barcos gestionados por el assitente de flota
            else{
                fleetManager.getManagedBoats().add(boatToAssign);
                boatToAssign.setAssignedFM(fleetManager);
                System.out.println("Captain " +  fleetManager.getId() + " " + fleetManager.getName() + " " + fleetManager.getSurnames() + " Assigned to " + boatToAssign.getId() + " " + boatToAssign.getName() + " Successfully");
            }
        }
    }

    /**
     * Metodo que sirve para desasignaro a un empleado de un barco
     * @param employees lista con todos los usuarios registrados en el sistema
     */
    public void unassignBoat(ArrayList<Employee> employees){

        Employee employeeToUnassign = null;
        Boat boatToUnassign = null;

        //solicitamos al usuario que introduzca el id del empleado adesasignar
        int employeeId = 0;
        System.out.println("Type the ID of the Employee you want to Assign to a boat");
        try {
            employeeId = input.nextInt();
            input.nextLine();
        }
        catch (InputMismatchException e) {
            System.err.println("Enter a valid Number");
            return;
        }
        for(Employee e: employees){
            if(e.getId() == employeeId){
                employeeToUnassign = e;
            }
        }
        if(employeeToUnassign != null){
            if(employeeToUnassign instanceof Sailor){
                Sailor sailor =  (Sailor) employeeToUnassign;

                if(sailor.getAssignedBoat() != null){
                    //si el marinero seleccionado no está en puerto lo comunicamos por pantalla y cancelamos
                    if(sailor.getAssignedBoat().getCurrentDistanceLeft() > 0){
                        System.out.println("The ship is on trip. Wait until arrival to change its staff");
                    }
                    //de estar en puerto completamos la operacion
                    else{
                        sailor.getAssignedBoat().getCrew().remove(sailor);
                        sailor.setAssignedBoat(null);
                    }
                }
                //si el marinero no está asignado a ningún barco lo mostramos por pantalla
                else{
                    System.out.println("The Sailor is not assigned to any ship yet");
                }
            }


            if(employeeToUnassign instanceof Captain){
                Captain captain = (Captain) employeeToUnassign;

                if(captain.getAssignedBoat() != null){
                    //Si el capitán no está en puerto mostramos la situación por pantalla
                    if(captain.getAssignedBoat().getCurrentDistanceLeft() > 0){
                        System.out.println("The ship is on trip. Wait until arrival to change its staff");
                    }
                    //de estar en puerto completamos la operación
                    else{
                        captain.getAssignedBoat().setCaptain(null);
                        captain.setAssignedBoat(null);
                    }
                }
                //si el Capitán no está asignado a un barco lo comunicamos al usuario
                System.out.println("The Captain is not assigned to any ship yet");
            }

            if(employeeToUnassign instanceof FleetManager){
                FleetManager fleetManager = (FleetManager) employeeToUnassign;

                //si el asistente de flota no está gestionando ningún barco lo comunicamos por pantalla
                if(fleetManager.getManagedBoats().isEmpty()){
                    System.out.println("The Fleet Manager is not Managing any ship yet");
                }
                //de lo contrario solicitamos al usuario que barco quiere que se le retire al asistente de flota
                else{
                    System.out.println("Select the boat you want to unassign");
                    for(int i = 0; i < fleetManager.getManagedBoats().size(); i++){
                        System.out.println("Option " + (i + 1) + ": ID" + fleetManager.getManagedBoats().get(i).getId() + " Name: " + fleetManager.getManagedBoats().get(i).getName() );
                    }
                    boolean cont = true;
                    while(cont) {
                        try {
                            int option = input.nextInt();
                            input.nextLine();

                            boatToUnassign = fleetManager.getManagedBoats().get(option - 1);
                            cont = false;
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Invalid option");
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a number");
                            input.nextLine();
                        }
                    }
                    /*si el barco selleccionado no está en puerto cancelamos la operación mostrando la situación
                    por pantalla*/
                    if(boatToUnassign.getCurrentDistanceLeft() > 0){
                        System.out.println("The selected ship is on a trip. Wait until arrival to change its staff");
                    }
                    //de lo contrario completamos la operación
                    else{
                        boatToUnassign.setAssignedFM(null);
                        fleetManager.getManagedBoats().remove(boatToUnassign);
                    }
                }
            }
        }
    }

    /**
     * Metodo que muestra por pantalla todos los empleados del sistema imprimiendo su id, nombres, apellidos y barco
     * asignado
     * @param employees lista con todos los empleados del registrados
     */
    public void employeeList(ArrayList<Employee> employees) {

        for (Employee employee : employees) {
            if (employee instanceof Captain) {
                Captain captain = (Captain) employee;
                if (captain.getAssignedBoat() != null) {
                    System.out.println("Employee ID: " + captain.getId() + " Captain: " + captain.getName() + " " + captain.getSurnames() + " Is assigned to Ship with ID: " + captain.getAssignedBoat().getId() + " " + captain.getAssignedBoat().getName());
                } else {
                    System.out.println("Employee ID: " + captain.getId() + " Captain: " + captain.getName() + " " + captain.getSurnames() + " Is not assigned to any Ship");
                }
            }
            if (employee instanceof Sailor) {
                Sailor sailor = (Sailor) employee;
                if (sailor.getAssignedBoat() != null) {
                    System.out.println("Employee ID: " + sailor.getId() + " Sailor: " + sailor.getName() + " " + sailor.getSurnames() + " Is assigned to Ship with ID: " + sailor.getAssignedBoat().getId() + " " + sailor.getAssignedBoat().getName());
                }
                else {
                    System.out.println("Employee ID: " + employee.getId() + " Sailor: " + employee.getName() + " " + employee.getSurnames() + " Is not assigned to any Ship");
                }
            }
            if (employee instanceof FleetManager) {
                FleetManager fleetManager = (FleetManager) employee;
                System.out.println("Employee ID: " + employee.getId() + " Fleet Manager " + employee.getName() + " " + employee.getSurnames());
                if (!fleetManager.getManagedBoats().isEmpty()) {
                    System.out.println("Is managin the current Fleet: ");
                    for (Boat boat : fleetManager.getManagedBoats()) {
                        System.out.println("Boat ID: " + boat.getId() + " Named: " + boat.getName());
                    }
                } else {
                    System.out.println("The Fleet Manager has no fleet Assigned to Manage");
                }
            }
        }
    }

    /**
     * Metodo que sirve para poder ver la información completa de un empleado
     * @param employees lista con todos los empleados registrados
     */
    public void employeeInfo(ArrayList<Employee> employees) {

        Employee infoEmployee = null;

        //solicitamos la id del empleado del cual se desea la información
        int id = 0;
        System.out.println("Type the id of the employee");
        try {
            id = input.nextInt();
            input.nextLine();
        }
        catch (InputMismatchException e) {
            System.out.println("Enter a valid Number");
            return;
        }

        for(Employee e : employees){
            if(e.getId() == id){
                infoEmployee = e;
            }
        }

        //dependiendo del tipo de empleado imprimiermos su tipo y sus variables unicas
        if(infoEmployee != null){
            if(infoEmployee instanceof Sailor) {
                Sailor sailorInfo = (Sailor) infoEmployee;
                System.out.println("Sailor Info");
                System.out.println("ID: " +  sailorInfo.getId());
                System.out.println("Full name: " + sailorInfo.getName() + " " +  sailorInfo.getSurnames());
                System.out.println("Contracted: " +  sailorInfo.getContractStartDate());
                System.out.println("Salary: " +  sailorInfo.getSalary());
                System.out.println("Current month accumulated Bonus: " + sailorInfo.getBonus());
                double totalSalary = sailorInfo.getSalary() +  sailorInfo.getBonus();
                System.out.println("Total Salary: " + totalSalary);
                System.out.println("Years of experience: " + sailorInfo.getExperience());
                if(sailorInfo.getAssignedBoat() != null) {
                    System.out.println("Is assigned to boat ID: " + sailorInfo.getAssignedBoat().getId() + " Named: " + sailorInfo.getAssignedBoat().getName());
                }
                else{
                    System.out.println("The current Sailor is no assigned to a ship");
                }
                System.out.println("This month has traveled: " + sailorInfo.getMonthDistance() + "Km and made " + sailorInfo.getTrips() + " trips");
            }
            if(infoEmployee instanceof Captain) {
                Captain captainInfo = (Captain) infoEmployee;
                System.out.println("Captain Info");
                System.out.println("ID: " +  captainInfo.getId());
                System.out.println("Full name: " + captainInfo.getName() + " " +  captainInfo.getSurnames());
                System.out.println("Contracted: " +  captainInfo.getContractStartDate());
                System.out.println("Base Salary: " +  captainInfo.getSalary());
                System.out.println("Current month accumulated Bonus: " + captainInfo.getBonus());
                double totalSalary = captainInfo.getSalary() + captainInfo.getBonus();
                System.out.println("Total Salary: " + totalSalary);
                System.out.println("Years of Experience: " + captainInfo.getExperience());
                if(captainInfo.getAssignedBoat() != null) {
                    System.out.println("Is assigned to Ship ID: " + captainInfo.getAssignedBoat().getId() + " Named: " + captainInfo.getAssignedBoat().getName());
                }
                else{
                    System.out.println("The current Captain is not assigned to a ship");
                }
                System.out.println("This month has traveled: " + captainInfo.getMonthDistance() + "Km and made " + captainInfo.getTrips() + " Trips");
            }
            if(infoEmployee instanceof FleetManager) {
                FleetManager fleetManagerInfo = (FleetManager) infoEmployee;
                System.out.println("Fleet Manager Info");
                System.out.println("ID: " +  fleetManagerInfo.getId());
                System.out.println("Full name: " + fleetManagerInfo.getName() + " " +  fleetManagerInfo.getSurnames());
                System.out.println("Contracted: " +  fleetManagerInfo.getContractStartDate());
                System.out.println("Salary: " +  fleetManagerInfo.getSalary());
                System.out.println("Years of experience: " + fleetManagerInfo.getExperience());
                if(!fleetManagerInfo.getManagedBoats().isEmpty()) {
                    System.out.println("Is managing the current Fleet");
                    for (Boat b : fleetManagerInfo.getManagedBoats()) {
                        System.out.println(b.getId() + " " + b.getName());
                    }
                }
                else{
                    System.out.println("The selected Fleet Manager is not managing any ship");
                }
            }
        }
    }

    /**
     * Metodo auxiliar que sirve para que el usuario decida sobre si cambiar al personal o no
     * @return booleano true si decide cambiar la situación de la tripulación o false si la rechaza
     */
    public boolean changeBoat(){

        System.out.println("Do you want to change? (Y/N)");
        String choice = input.nextLine();
        if(choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")){
            return true;
        }
        else{
            System.out.println("Cancelling operation");
            return false;
        }
    }
}
